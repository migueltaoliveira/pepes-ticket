package com.fcouceiro.pepesticket.communication;

import com.fcouceiro.pepesticket.communication.models.GroupTicket;
import com.fcouceiro.pepesticket.communication.models.Service;
import com.fcouceiro.pepesticket.communication.models.ServiceListWrapper;
import com.fcouceiro.pepesticket.communication.models.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by franciscocouceiro on 02/06/17.
 */

public interface ApiService {
    @GET("tickets")
    Call<List<GroupTicket>> getGroupTickets();

    @GET("tickets/{id}")
    Call<GroupTicket> getGroupTicket(@Path("id") long id);

    @GET("tickets/{id}/nextService")
    Call<Service> getGroupTicketNextService(@Path("id") int id);

    @POST("tickets/generate")
    Call<GroupTicket> generateGroupTicket(@Body ServiceListWrapper services);

    @DELETE("tickets/{id}")
    Call<GroupTicket> deleteTicket(@Path("id") long id);

    @GET("services")
    Call<List<Service>> getServices();

    @GET("services/{name}/nextTicket")
    Call<Ticket> getServiceNextTicket(@Path("name") String name);

    @POST("authenticate")
    Call<String> authenticate();
}
