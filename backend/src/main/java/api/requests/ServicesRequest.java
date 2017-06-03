package api.requests;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by migueloliveira on 03/06/17.
 */
public class ServicesRequest
{
    @Expose
    List<String> services;

    public ServicesRequest(List<String> services) {
        this.services = services;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }
}
