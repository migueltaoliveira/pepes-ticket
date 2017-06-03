package manager;


import database.MarketDatabase;
import model.GroupTicket;
import model.Service;
import model.Ticket;
import utils.Constants;

import java.security.acl.Group;
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

        for (String service : Constants.SERVICES)
        {
            this.nextTicket.put(service, 0L);
        }
    }

    @Override
    public GroupTicket generateGroupTicket(String userId, List<String> services)
    {
        //TODO: proteger quando o service esta vazio
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (String service : services)
        {
            if (Constants.SERVICES.contains(service))
            {
                tickets.add(new Ticket(new Service(service), incrementNextService(new Service(service))));
            }
        }

        GroupTicket groupTicket = null;

        if (tickets.size() != 0)
        {
            groupTicket = new GroupTicket(tickets, incrementNextGroupId(), userId);

            synchronized (this.groupTickets) {
                this.groupTickets.put(groupTicket.getId(), groupTicket);
            }
        }

        return groupTicket;
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

    private Long incrementNextService(Service service)
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
