package com.fcouceiro.pepesticket.communication.models;

import java.util.List;

/**
 * Created by franciscocouceiro on 03/06/17.
 */

public class ServiceListWrapper {
    public List<String> services;

    public static ServiceListWrapper fromList(List<String> list){
        ServiceListWrapper listWrapper = new ServiceListWrapper();
        listWrapper.services = list;

        return listWrapper;
    }
}
