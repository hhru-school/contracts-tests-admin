package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("api")
public class StatusResource {

    @Path("stands")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public StandsContainerDto getStands(){
        return new StandsContainerDto();
    }

    @Path("services")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServices(@QueryParam("standId") String standId){
        return Response.ok(new ArrayList<ServiceDto>()).build();
    }

}
