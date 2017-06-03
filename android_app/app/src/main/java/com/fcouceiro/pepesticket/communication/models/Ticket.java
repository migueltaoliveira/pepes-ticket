package com.fcouceiro.pepesticket.communication.models;

/**
 * Created by franciscocouceiro on 02/06/17.
 */

public class Ticket {

    private long ticketId;

    private Service service;

    public long getTicketNumber() {
        return ticketId;
    }

    public Service getService() {
        return service;
    }

}
