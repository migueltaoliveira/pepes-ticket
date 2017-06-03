package model;

import java.util.List;

/**
 * Created by migueloliveira on 02/06/17.
 */
public class GroupTicket
{
    private List<Ticket> ticket;
    private long id;
    private String userId;

    public GroupTicket(List<Ticket> ticket, long id, String userId) {
        this.ticket = ticket;
        this.id = id;
        this.userId = userId;
    }

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
