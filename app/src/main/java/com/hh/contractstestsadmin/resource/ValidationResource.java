package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.api.ErrorMessageDto;
import com.hh.contractstestsadmin.dto.api.ExpectationDto;
import com.hh.contractstestsadmin.dto.api.ValidationDto;
import com.hh.contractstestsadmin.dto.api.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.service.StandValidationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
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

  private final StandValidationService standValidationService;

  @Inject
  public ValidationResource(StandValidationService standValidationService) {
    this.standValidationService = standValidationService;
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
      @QueryParam("sizeLimit") Integer sizeLimit
  ) {
    try {
      return Response.ok(standValidationService.getValidationHistory(standName, sizeLimit)).build();
    } catch (ValidationHistoryNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessageDto(exception.getMessage())).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }

  @ApiOperation(
      value = "Get detailed information about validation",
      response = ValidationDto.class)
  @Path("stands/{standName}/validations/{validationId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getServicesRelations(
      @PathParam("standName") String standName,
      @PathParam("validationId") Long validationId
  ) {
    try {
      return Response.ok().build();
    } catch (ValidationHistoryNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessageDto(exception.getMessage())).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }

  @ApiOperation(
      value = "Get wrong expectations",
      response = ExpectationDto.class,
      responseContainer = "List")
  @Path("stands/{standName}/validations/{validationId}/expectations")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getExpectations(
      @PathParam("standName") String standName,
      @PathParam("validationId") Long validationId,
      @NotNull @QueryParam("producerId") Long producerId,
      @NotNull @QueryParam("consumerId") Long consumerId
  ) {
    try {
      return Response.ok().build();
    } catch (ValidationHistoryNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessageDto(exception.getMessage())).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }

  @ApiOperation(
      value = "Get validation source code")
  @Path("stands/{standName}/validations/{validationId}/validatorError")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getValidatorError(
      @PathParam("standName") String standName,
      @PathParam("validationId") Long validationId
  ) {
    try {
      return Response.ok().build();
    } catch (ValidationHistoryNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessageDto(exception.getMessage())).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
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
      standValidationService.runValidation(standName);
      return Response.status(Response.Status.ACCEPTED).build();
    } catch (StandNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessageDto(exception.getMessage())).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }

}
