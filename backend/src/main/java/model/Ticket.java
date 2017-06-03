package model;

import com.google.gson.annotations.Expose;

/**
 * Created by migueloliveira on 02/06/17.
 */
public class Ticket
{
    @Expose
    private Service service;
    @Expose
    private Long ticketId;
    private boolean busy;
    private boolean finished;
    //The groupTicket the ticket belongs
    private Long groupTicketId;

    public Ticket(Service service, Long ticketId)
    {
        this.service = service;
        this.ticketId = ticketId;
        this.busy = false;
        this.finished = false;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getGroupTicketId() {
        return groupTicketId;
    }

    public void setGroupTicketId(Long groupTicketId) {
        this.groupTicketId = groupTicketId;
    }
}
