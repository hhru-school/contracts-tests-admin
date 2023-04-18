package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.service.StatusService;
import com.hh.contractstestsadmin.service.ValidationService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("api")
public class ValidationResource {

    private final ValidationService validationService;

    @Inject
    public ValidationResource(ValidationService validationService){
        this.validationService = validationService;
    }

    @Path("stand/{standName}/validations/preview")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistoryPreview(@PathParam("standName") String standName,
                                      @QueryParam("sizeLimit") Long sizeLimit){
        try {
            return Response.ok(validationService.getHistoryPreview(standName, sizeLimit)).build();
        } catch (StandNotFoundException exception){
            return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
        }
    }

    @Path("stand/{standName}/validations")
    @POST
    public Response runValidation(@PathParam("standName") String standName){
        try {
            validationService.runValidation(standName);
            return Response.ok().build();
        } catch (StandNotFoundException exception){
            return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
        }
    }

}