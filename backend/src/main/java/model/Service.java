package model;

import com.google.gson.annotations.Expose;

import java.util.LinkedList;

/**
 * Created by migueloliveira on 02/06/17.
 */
public class Service
{
    @Expose
    private String name;
    //Pair GroupId, TicketId
    private LinkedList<Pair<Long, Long>> queue;
    @Expose
    private Ticket actualTicket;

    public Service(String name)
    {
        this.name = name;
        this.queue = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Pair<Long, Long>> getQueue() {
        return queue;
    }

    public void setQueue(LinkedList<Pair<Long, Long>> queue) {
        this.queue = queue;
    }

    public Ticket getActualTicket() {
        return actualTicket;
    }

    public void setActualTicket(Ticket actualTicket) {
        this.actualTicket = actualTicket;
    }


}

