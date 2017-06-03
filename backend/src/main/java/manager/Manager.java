package manager;


import model.GroupTicket;
import model.Service;

import java.util.List;

/**
 * Created by migueloliveira on 02/06/17.
 */
public interface Manager
{
    /*
    * Tickets
    * */
    GroupTicket generateGroupTicket(String userId, List<String> services);

    GroupTicket getTicket(String userId, Long id);

    Service getNextService(String userId, Long id);

    List<Service> getServices();

    boolean deleteTicket(String userId, Long id);

    List<GroupTicket> getTickets(String userId);

    String authentication();

}
