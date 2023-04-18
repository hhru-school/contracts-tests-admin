package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.*;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.service.StatusService;

import javax.inject.Inject;
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

    private final StatusService statusService;

    @Inject
    public StatusResource(StatusService statusService){
        this.statusService = statusService;
    }

    @Path("stands")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public StandsContainerDto getStands(){
        return statusService.getStandsInfo();
    }

    @Path("services")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServices(@QueryParam("standName") String standName){
        try {
            return Response.ok(statusService.getServicesInfo(standName)).build();
        } catch (StandNotFoundException exception){
            return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
        }
    }

}
