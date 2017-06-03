package com.fcouceiro.pepesticket.communication.models;

import java.util.List;

/**
 * Created by franciscocouceiro on 02/06/17.
 */

public class GroupTicket {

    private List<Ticket> ticket;

    private long id;

    private long userId;

    public List<Ticket> getTicket() {
        return ticket;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }


}
