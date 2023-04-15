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
    public List<StandDto> getStands(){
        return new ArrayList<>();
    }

    @Path("stands/{standId}/services")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServices(@PathParam("standId") Long standId){
        return Response.ok(new ArrayList<ServiceDto>()).build();
    }

    @Path("stands/release/services")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReleaseServices(){
        return Response.ok(new ArrayList<ServiceDto>()).build();
    }

}
