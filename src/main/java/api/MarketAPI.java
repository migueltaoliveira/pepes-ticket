package main.java.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by migueloliveira on 02/06/17.
 */
@Path("/endpoints")
@Produces({MediaType.APPLICATION_JSON})
public class MarketAPI
{
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String test()
    {
        return "{\"test\":\"first\"}";
    }
}
