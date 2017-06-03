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

    //Number of Ticket by service -> only used to increment
    private Map<String, Long> nextTicket;
    private Map<Long, GroupTicket> groupTickets;
    private HashMap<String, Service> services;

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

                //Add this ticket to the list
                synchronized (this.services)
                {
                    this.services.get(service).getQueue().add(new Pair<>(groupTicket.getId(), ticketNumber));
                }

                tickets.add(new Ticket(new Service(service), ticketNumber));
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

    @Override
    public Service getNextService(String userId, Long groupId)
    {
        return null;
    }

    @Override
    public boolean deleteTicket(String userId, Long groupId)
    {
        synchronized (this.groupTickets)
        {
            GroupTicket groupTicket = this.groupTickets.get(groupId);

            if (groupTicket != null) {
                if (groupTicket.getUserId().equals(userId)) {
                    GroupTicket remove = this.groupTickets.remove(groupId);
                    return remove != null;
                }
            }
        }

        return false;
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

    private Ticket chooseTicketToServiceName(Service service)
    {
        int cleanByIndex = 0;
        boolean clean = false;

        synchronized (this.groupTickets)
        {

            //First we need to mark the previous ticket as finished
            for (int i = 0; i < service.getQueue().size(); i++) {
                cleanByIndex = i;

                Pair<Long, Long> ticketsPair = service.getQueue().get(i);

                for (Ticket ticket : this.groupTickets.get(ticketsPair.getLeft()).getTicket()) {
                    if (ticket.getTicketNumber().equals(ticketsPair.getRight()) && ticket.getService().getName().equals(service.getName())) {
                        if (ticket.isBusy()) {
                            ticket.setBusy(false);
                            ticket.setFinished(true);
                            clean = true;
                            break;
                        }
                    }
                }

                if (clean) {
                    break;
                }
            }
        }

        // If the ticket needs to be removed
        if (clean)
        {
            if (service.getQueue().get(cleanByIndex) != null)
            {
                service.getQueue().remove(cleanByIndex);
            }
        }

        synchronized (this.groupTickets)
        {
            // We iterate to check which ticket is choosed
            for (int i = 0; i < service.getQueue().size(); i++) {
                Pair<Long, Long> ticketsPair = service.getQueue().get(i);

                for (Ticket ticket : this.groupTickets.get(ticketsPair.getLeft()).getTicket()) {
                    if (ticket.getTicketNumber().equals(ticketsPair.getRight())) {
                        if (!ticket.isFinished() && !ticket.isBusy()) {
                            return ticket;
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Ticket getNextTicket(String serviceName)
    {
        if (Constants.SERVICES.contains(serviceName))
        {
            synchronized (this.services)
            {
                return chooseTicketToServiceName(this.services.get(serviceName));
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Service> getServices() {
        return Arrays.asList(new Service("TALHO"), new Service("PADARIA"),
                new Service("PEIXARIA"), new Service("CHARCUTARIA"),
                new Service("PASTELARIA"));
    }

    @Override
    public String authentication() {
        return UUID.randomUUID().toString();
    }

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


}
