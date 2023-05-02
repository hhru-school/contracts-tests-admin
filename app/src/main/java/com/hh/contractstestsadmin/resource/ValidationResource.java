package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.service.ValidationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api
@Path("api")
public class ValidationResource {

  private final ValidationService validationService;

  @Inject
  public ValidationResource(ValidationService validationService) {
    this.validationService = validationService;
  }

  @ApiOperation(
      value = "Get list with stands",
      response = ValidationPreviewDto.class,
      responseContainer = "List")
  @Path("stands/{standName}/validations")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getValidationsHistory(
      @PathParam("standName") String standName,
      @QueryParam("historySizeLimit") Long historySizeLimit
  ) {
    try {
      return Response.ok(validationService.getValidationsHistory(standName, historySizeLimit)).build();
    } catch (ValidationHistoryNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
    }
  }

  @ApiOperation(
      value = "Run validation",
      response = String.class,
      code = 202)
  @Path("stands/{standName}/validations")
  @POST
  public Response runValidation(@PathParam("standName") String standName) {
    try {
      validationService.runValidation(standName);
      return Response.status(Response.Status.ACCEPTED).build();
    } catch (StandNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
    }
  }

}
