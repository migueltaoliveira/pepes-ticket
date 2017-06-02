package model;

/**
 * Created by migueloliveira on 02/06/17.
 */
public class Ticket
{
    private Service service;
    private long ticketNumber;

    public Ticket(Service service, long ticketNumber) {
        this.service = service;
        this.ticketNumber = ticketNumber;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public long getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(long ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
