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

    @Path("stand/{standId}/validations/preview")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistoryPreview(@PathParam("standId") Long standId,
                                      @QueryParam("sizeLimit") Long sizeLimit){
        return Response.ok(new ArrayList<ValidationPreviewDto>()).build();
    }

    @Path("stand/{standId}/validations")
    @POST
    public Response runValidation(@PathParam("standId") Long standId){
        return Response.ok().build();
    }

}
