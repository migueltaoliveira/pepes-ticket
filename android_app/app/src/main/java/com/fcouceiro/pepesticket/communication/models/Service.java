package com.fcouceiro.pepesticket.communication.models;

import java.util.List;

/**
 * Created by franciscocouceiro on 02/06/17.
 */

public class Service {

    private String name;

    private boolean isChecked = false;

    private Ticket actualTicket;

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Ticket getActualTicket() {
        return actualTicket;
    }
}
