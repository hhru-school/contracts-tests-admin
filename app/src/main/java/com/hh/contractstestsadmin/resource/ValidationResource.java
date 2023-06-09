package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.api.ErrorMessageDto;
import com.hh.contractstestsadmin.dto.api.ExpectationDto;
import com.hh.contractstestsadmin.dto.api.ValidationWithRelationsDto;
import com.hh.contractstestsadmin.dto.api.ValidationMetaInfoDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.service.StandValidationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api
@Path("api")
public class ValidationResource {

  private final StandValidationService standValidationService;
  private static final Logger LOG = LoggerFactory.getLogger(ValidationResource.class);

  @Inject
  public ValidationResource(StandValidationService standValidationService) {
    this.standValidationService = standValidationService;
  }

  @ApiOperation(
      value = "Get list with stands",
      response = ValidationMetaInfoDto.class,
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
      response = ValidationWithRelationsDto.class)
  @Path("stands/{standName}/validations/{validationId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getValidationWithRelations(
      @PathParam("standName") String standName,
      @PathParam("validationId") Long validationId
  ) {
    try {
      return Response.ok(standValidationService.getValidationWithRelations(standName, validationId)).build();
    } catch (ValidationHistoryNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessageDto(exception.getMessage())).build();
    } catch (Exception exception) {
      LOG.error("Unknown exception caught during getting expectation ", exception);
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
      return Response.ok(standValidationService.getExpectations(standName, validationId, producerId, consumerId)).build();
    } catch (NotFoundException e) {
      LOG.error("Not found expectation with standName {} and validationId {} " +
          "and producerId {} and consumerId {}", standName, validationId, producerId, consumerId, e);
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    } catch (Exception exception) {
      LOG.error("Unknown exception caught during getting expectation ", exception);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }

  @ApiOperation(
      value = "Get validation source code")
  @Path("stands/{standName}/validations/{validationId}/file")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getValidatorReport(
      @PathParam("standName") String standName,
      @PathParam("validationId") Long validationId
  ) {
    try {
      return Response.ok(standValidationService.getValidatorReport(standName, validationId))
              .header("Content-Disposition", "attachment")
              .build();
    } catch (ValidationHistoryNotFoundException | StandNotFoundException e) {
      LOG.error("Not found validation report with standName: {} and validationId: {} ", standName, validationId, e);

      return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessageDto(e.getMessage())).build();
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
