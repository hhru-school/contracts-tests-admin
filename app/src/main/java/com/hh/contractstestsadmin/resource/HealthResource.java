package com.hh.contractstestsadmin.resource;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("api")
public class HealthResource {

    @GET
    @Path("health")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getHealth() {
        return Response.ok().entity(Map.of("value", "hello")).build();
    }
}
