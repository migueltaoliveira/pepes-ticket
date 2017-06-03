package api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.requests.ServicesRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.MarketManager;
import model.GroupTicket;
import model.Service;
import org.apache.log4j.Logger;
import utils.Constants;

import java.util.List;

/**
 * Created by migueloliveira on 02/06/17.
 */
@Path("/endpoints")
@Produces({MediaType.APPLICATION_JSON})
public class MarketAPI
{
    private static Logger logger = Logger.getLogger(MarketAPI.class);

    private MarketManager marketManager;
    private Gson gson;

    public MarketAPI()
    {
        this.marketManager = MarketManager.getInstance();
        this.gson = new GsonBuilder().create();
    }

    @POST
    @Path("/tickets/generate")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response generateTickets(@QueryParam("userId") String userId, String servicesRequest)
    {
        ServicesRequest services = gson.fromJson(servicesRequest, ServicesRequest.class);
        GroupTicket groupTicket = null;

        if (userId == null)
        {
            userId = Constants.FAKE_USER;
        }

        if (services != null && services.getServices() != null && !services.getServices().isEmpty())
        {
            groupTicket = this.marketManager.generateGroupTicket(userId, services.getServices());
        }

        if (groupTicket != null)
        {
            return Response.status(Response.Status.OK).entity(gson.toJson(groupTicket)).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/tickets/id")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTickets(@QueryParam("userId") String userId, @QueryParam("id") Long id)
    {
        GroupTicket groupTicket = this.marketManager.getTicket(userId, id);

        if (groupTicket != null)
        {
            return Response.status(Response.Status.OK).entity(gson.toJson(groupTicket)).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/tickets/id/nextService")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNextService(@QueryParam("userId") String userId, @QueryParam("id") Long id)
    {
        Service services = this.marketManager.getNextService(userId, id);
        return Response.status(Response.Status.OK).entity(gson.toJson(services)).build();
    }

    @GET
    @Path("/services")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getServices()
    {
        List<Service> services = this.marketManager.getServices();
        return Response.status(Response.Status.OK).entity(gson.toJson(services)).build();
    }

    @DELETE
    @Path("/tickets/id")
    public Response deleteTicket(@QueryParam("userId") String userId, @QueryParam("id") Long id)
    {
        boolean deleted = this.marketManager.deleteTicket(userId, id);
        if (deleted)
        {
            return Response.status(Response.Status.OK).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/tickets")
    public Response getTickets(@QueryParam("userId") String userId)
    {
        List<GroupTicket> tickets = this.marketManager.getTickets(userId);

        return Response.status(Response.Status.OK).entity(gson.toJson(tickets)).build();
    }

    @POST
    @Path("/authenticate")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response authentication()
    {
        String userId = this.marketManager.authentication();
        return Response.status(Response.Status.OK).entity(gson.toJson(userId)).build();
    }

}
