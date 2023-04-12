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

    @Path("stands/{standId}/services/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServices(@PathParam("standId") Long standId,
                                @QueryParam("serviceType") ServiceType serviceType){
        return Response.ok(new ArrayList<ServiceDto>()).build();
    }

    @Path("stands/{standId}/services/contracts/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContractsInfo(@PathParam("standId") Long standId,
                                     @QueryParam("warningsLimit") Integer warningsLimit,
                                     @QueryParam("serviceType") ServiceType serviceType){
        return Response.ok(new ArrayList<ContractsInfoDto>()).build();
    }

    @Path("stands/{standId}/services/{serviceId}/contracts/expectations")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExpectations(@PathParam("standId") Long standId,
                                    @PathParam("serviceId") Long serviceId){
        return Response.ok("").build();
    }

    @Path("stands/{standId}/services/{serviceId}/contracts/expectations/{producerServiceId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExpectation(@PathParam("standId") Long standId,
                                   @PathParam("serviceId") Long serviceId,
                                   @PathParam("producerServiceId") Long producerServiceId){
        return Response.ok("").build();
    }

    @Path("stands/{standId}/services/{serviceId}/contracts/scheme")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScheme(@PathParam("standId") Long standId,
                              @PathParam("serviceId") Long serviceId){
        return Response.ok("").build();
    }

    @Path("stands/{standId}/services/{serviceId}/contracts/scheme/{operationId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSchemeOperation(@PathParam("standId") Long standId,
                                       @PathParam("serviceId") Long serviceId,
                                       @PathParam("operationId") String operationId){
        return Response.ok("").build();
    }
    @Path("stands/{standId}/services/{serviceId}/contracts/expectations/warningsList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWarningsOfExpectations(@PathParam("standId") Long standId,
                                              @PathParam("serviceId") Long serviceId){
        return Response.ok(new ArrayList<WarningDto>()).build();
    }

    @Path("stands/{standId}/services/{serviceId}/contracts/scheme/warningsList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWarningsOfScheme(@PathParam("standId") Long standId,
                                        @PathParam("serviceId") Long serviceId){
        return Response.ok(new ArrayList<WarningDto>()).build();
    }

}
