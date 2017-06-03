package manager;


import database.MarketDatabase;
import model.GroupTicket;
import model.Pair;
import model.Service;
import model.Ticket;
import utils.Constants;

import java.util.*;

/**
 * Created by migueloliveira on 02/06/17.
 */
public class MarketManager implements Manager
{
    private static MarketManager INSTANCE;
    private MarketDatabase marketDatabase;

    /*
    * Variables that represents the next ticket number
    * */
    private Long nextGroupId = 0L;

    private Map<String, Long> nextTicket;
    private Map<Long, GroupTicket> groupTickets;
    private HashMap<String, Service> services;

    /*
    * Here we create a singleton MarketManager
    * */
    public static synchronized MarketManager getInstance()
    {
        if (INSTANCE == null) {
            synchronized (MarketManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MarketManager();
                }
            }
        }
        return INSTANCE;
    }

    // Synchronized block
    private MarketManager()
    {
        this.marketDatabase = new MarketDatabase();
        this.nextTicket = new HashMap<>();
        this.groupTickets = new HashMap<>();
        this.services = new HashMap<>();

        for (String service : Constants.SERVICES)
        {
            this.nextTicket.put(service, 0L);
            this.services.put(service, new Service(service));
        }
    }

    @Override
    public GroupTicket generateGroupTicket(String userId, List<String> services)
    {
        ArrayList<Ticket> tickets = new ArrayList<>();

        GroupTicket groupTicket = new GroupTicket(tickets, incrementNextGroupId(), userId);

        for (Ticket ticket : tickets)
        {
            ticket.setGroupTicketId(groupTicket.getId());
        }

        for (String service : services)
        {
            if (Constants.SERVICES.contains(service))
            {
                Long ticketNumber = incrementTicketNumberByService(new Service(service));

                //Add this ticket to the queue list
                synchronized (this.services)
                {
                    this.services.get(service).getQueue().add(new Pair<>(groupTicket.getId(), ticketNumber));
                    tickets.add(new Ticket(this.services.get(service), ticketNumber));
                }
            }
        }

        if (tickets.size() != 0)
        {
            synchronized (this.groupTickets)
            {
                this.groupTickets.put(groupTicket.getId(), groupTicket);
            }

            return groupTicket;
        }
        else
        {
            return null;
        }
    }

    @Override
    public GroupTicket getTicket(String userId, Long id)
    {
        GroupTicket groupTicket;
        synchronized (this.groupTickets)
        {
            groupTicket = this.groupTickets.get(id);
        }

        if (groupTicket != null && groupTicket.getUserId().equals(userId))
        {
            return groupTicket;
        }

        return null;
    }

    private int getQueuePosition(Service service, Long groupId, Long ticketId)
    {
        for (int i = 0; i <service.getQueue().size(); i++)
        {
            Pair<Long, Long> ticketsPair = service.getQueue().get(i);

            if (ticketsPair.getLeft().equals(groupId) && ticketsPair.getRight().equals(ticketId))
            {
                return i;
            }
        }

        //When the groupId, ticketId is not found
        return Integer.MAX_VALUE;
    }

    @Override
    public Service getNextService(String userId, Long groupId)
    {
        // We want to decide which service the user is served
        GroupTicket groupTicket = this.groupTickets.get(groupId);

        int minValue = Integer.MAX_VALUE;
        Service bestService = null;

        //We need to lock the services to assure that the services is not being changed
        synchronized (this.services)
        {
            for (Ticket ticket : groupTicket.getTicket()) {
                if (!ticket.isFinished()) {
                    int queuePosition = getQueuePosition(ticket.getService(), ticket.getGroupTicketId(), ticket.getTicketId());
                    if (queuePosition < minValue)
                    {
                        bestService = ticket.getService();
                    }
                }
            }
        }

        return bestService;
    }



    @Override
    public GroupTicket deleteTicket(String userId, Long groupId)
    {
        synchronized (this.groupTickets)
        {
            GroupTicket groupTicket = this.groupTickets.get(groupId);

            if (groupTicket != null) {
                if (groupTicket.getUserId().equals(userId)) {
                    GroupTicket remove = this.groupTickets.remove(groupId);
                    return remove;
                }
            }
        }

        return null;
    }

    @Override
    public List<GroupTicket> getTickets(String userId)
    {
        List<GroupTicket> groupTickets = new ArrayList<>();

        for (Map.Entry<Long, GroupTicket> entry : this.groupTickets.entrySet())
        {
            if (entry.getValue().getUserId().equals(userId))
            {
                groupTickets.add(entry.getValue());
            }
        }

        return groupTickets;
    }

    @Override
    public Ticket getNextTicket(String serviceName)
    {
        if (Constants.SERVICES.contains(serviceName))
        {
            Service service = this.services.get(serviceName);
            //We lock the service to assure that no one is modifing the actual service state
            synchronized (service)
            {
                return chooseTicketToServiceName(service);
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Service> getServices()
    {
        ArrayList<Service> services = new ArrayList<>();

        for (Map.Entry<String, Service> service : this.services.entrySet())
        {
            services.add(service.getValue());
        }

        return services;
    }

    @Override
    public String authentication() {
        return UUID.randomUUID().toString();
    }

    /**
    * Methods to organize the application logical
    * */
    private Long incrementTicketNumberByService(Service service)
    {
        synchronized (this.nextTicket)
        {
            Long aLong = this.nextTicket.get(service.getName());

            if (aLong != null)
            {
                Long nextValue = aLong + 1;
                this.nextTicket.put(service.getName(), nextValue);
                return nextValue;
            }
        }
        return null;
    }

    private Long incrementNextGroupId()
    {
        synchronized (this.nextGroupId)
        {
            this.nextGroupId++;
            return this.nextGroupId;
        }
    }

    private Ticket getTicketByServiceAndGroup(String serviceName, Long ticketId)
    {
        for (Map.Entry<Long, GroupTicket> longGroupTicketEntry : this.groupTickets.entrySet())
        {

            for (Ticket ticket : longGroupTicketEntry.getValue().getTicket())
            {
                if (ticket.getService().getName().equals(serviceName) && ticketId.equals(ticket.getTicketId()))
                {
                    return ticket;
                }
            }

        }
        return null;
    }

    private Ticket chooseTicketToServiceName(Service service)
    {
        Long cleanGroupTicket = null;

        //Free the service
        if (service.getActualTicketId() != null)
        {
            Long ticketId = service.getActualTicketId();
            Ticket ticket = getTicketByServiceAndGroup(service.getName(), ticketId);

            if (ticket != null)
            {
                synchronized (ticket)
                {
                    ticket.setFinished(true);
                    ticket.setBusy(false);
                    service.setActualTicketId(null);

                    cleanGroupTicket = ticket.getGroupTicketId();
                    GroupTicket groupTicket = this.groupTickets.get(cleanGroupTicket);

                    if (groupTicket != null)
                    {
                        //check if the group ticket was served
                        for (Ticket ticket1 : groupTicket.getTicket()) {
                            if (!ticket1.isFinished()) {
                                //if the group ticket has one ticket that is not served, we don't remove it yet
                                cleanGroupTicket = null;
                            }
                        }
                    }
                }
            }
        }

        //The decision process needs to assure that no service is interfering
        synchronized (this.groupTickets)
        {
            //We remove a group ticket that was already served
            if (cleanGroupTicket != null)
            {
                this.groupTickets.remove(cleanGroupTicket);
            }

            // We iterate to check which ticket is chosen
            Iterator<Pair<Long, Long>> it = service.getQueue().iterator();
            while (it.hasNext())
            {
                Pair<Long, Long> ticketsPair = it.next();

                GroupTicket groupTicket = this.groupTickets.get(ticketsPair.getLeft());
                if (groupTicket != null)
                {
                    for (Ticket ticket : this.groupTickets.get(ticketsPair.getLeft()).getTicket()) {
                        if (ticket.getTicketId().equals(ticketsPair.getRight())) {
                            if (!ticket.isFinished() && !ticket.isBusy()) {
                                ticket.setBusy(true);
                                service.setActualTicketId(ticket.getTicketId());

                                //Remove the ticket from the queue
                                it.remove();

                                return ticket;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

}
