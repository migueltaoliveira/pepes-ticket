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
import model.Ticket;
import utils.Constants;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by migueloliveira on 02/06/17.
 */
@Path("/endpoints")
@Produces({MediaType.APPLICATION_JSON})
public class MarketAPI
{
    private static Logger logger = Logger.getLogger(MarketAPI.class.getName());

    private MarketManager marketManager;
    private Gson gson;

    public MarketAPI()
    {
        this.marketManager = MarketManager.getInstance();
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
    * Returns the generated group ticket
    * */
    @POST
    @Path("/tickets/generate")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response generateTickets(@QueryParam("userId") String userId, String servicesRequest)
    {
        logger.info("POST /tickets/generate?userId=" + userId + " body = " + servicesRequest);
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

    /**
     * Returns the group ticket by its id
     * */
    @GET
    @Path("/tickets/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTickets(@QueryParam("userId") String userId, @PathParam("id") Long id)
    {
        logger.info("GET /tickets/" + id + "?userId=" + userId);

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

    /**
     * Get the nextService for a certain group ticket
     * */
    @GET
    @Path("/tickets/{id}/nextService")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNextService(@QueryParam("userId") String userId, @PathParam("id") Long id)
    {
        logger.info("GET /tickets/" + id + "/nextService?userId=" + userId);

        Service service = this.marketManager.getNextService(userId, id);
        if (service != null)
        {
            return Response.status(Response.Status.OK).entity(gson.toJson(service)).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Get all the services
     */
    @GET
    @Path("/services")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getServices()
    {
        logger.info("GET /services");

        List<Service> services = this.marketManager.getServices();
        return Response.status(Response.Status.OK).entity(gson.toJson(services)).build();
    }

    /**
    * Deletes a group ticket
    * */
    @DELETE
    @Path("/tickets/{id}")
    public Response deleteTicket(@QueryParam("userId") String userId, @PathParam("id") Long id)
    {
        logger.info("DELETE /tickets/" + id + "?userId=" + userId);

        GroupTicket deleted = this.marketManager.deleteTicket(userId, id);
        if (deleted != null)
        {
            return Response.status(Response.Status.OK).entity(gson.toJson(deleted)).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /*
    * Get all the group tickets for a certain userId
    * */
    @GET
    @Path("/tickets")
    public Response getTickets(@QueryParam("userId") String userId)
    {
        logger.info("GET /tickets?userId=" + userId);

        List<GroupTicket> tickets = this.marketManager.getTickets(userId);

        return Response.status(Response.Status.OK).entity(gson.toJson(tickets)).build();
    }

    /*
    * Choose the next ticket for that service
    * */
    @GET
    @Path("/services/{service}/nextTicket")
    public Response getNextTicket(@PathParam("service") String serviceName)
    {
        logger.info("GET /services/" + serviceName);

        Ticket nextTicket = this.marketManager.getNextTicket(serviceName);

        if (nextTicket != null)
        {
            return Response.status(Response.Status.OK).entity(gson.toJson(nextTicket)).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }



    @POST
    @Path("/authenticate")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response authentication()
    {
        logger.info("POST /authentication");

        String userId = this.marketManager.authentication();
        return Response.status(Response.Status.OK).entity(gson.toJson(userId)).build();
    }

}
