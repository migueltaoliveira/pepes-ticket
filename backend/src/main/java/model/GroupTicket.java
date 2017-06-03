package model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by migueloliveira on 02/06/17.
 */
public class GroupTicket
{
    @Expose
    private List<Ticket> ticket;
    @Expose
    private Long id;
    @Expose
    private String userId;

    public GroupTicket(List<Ticket> ticket, Long id, String userId) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
