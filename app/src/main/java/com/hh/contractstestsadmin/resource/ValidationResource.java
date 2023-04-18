package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.ValidationPreviewDto;

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

    @Path("stand/{standName}/validations/preview")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistoryPreview(@PathParam("standName") String standName,
                                      @QueryParam("sizeLimit") Long sizeLimit){
        return Response.ok(new ArrayList<ValidationPreviewDto>()).build();
    }

    @Path("stand/{standName}/validations")
    @POST
    public Response runValidation(@PathParam("standName") String standName){
        return Response.ok().build();
    }

}
