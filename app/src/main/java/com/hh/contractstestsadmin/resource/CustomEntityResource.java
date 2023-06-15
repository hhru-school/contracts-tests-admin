package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.api.ErrorMessageDto;
import com.hh.contractstestsadmin.dto.api.ErrorTypeDto;
import com.hh.contractstestsadmin.exception.ErrorTypeCannotBeDeletedException;
import com.hh.contractstestsadmin.service.CustomEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

@Api
@Path("api")
public class CustomEntityResource {
  private final CustomEntityService customEntityService;
  private static final Logger LOG = LoggerFactory.getLogger(CustomEntityResource.class);

  @Inject
  public CustomEntityResource(CustomEntityService customEntityService) {

    this.customEntityService = customEntityService;
  }

  @ApiOperation(
      value = "create error dictionary",
      response = String.class,
      code = 201)
  @Path("error-types")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(@RequestBody List<ErrorTypeDto> errorTypeDtos) {
    if (CollectionUtils.isEmpty(errorTypeDtos)) {
      return Response.status(Response.Status.BAD_REQUEST).entity("entity can not be empty").build();
    }
    if (errorTypeDtos.size() > 100) {
      return Response.status(Response.Status.BAD_REQUEST).entity("max size value for create entities is 100").build();
    }
    try {
      customEntityService.createErrorTypes(errorTypeDtos);
      return Response.status(Response.Status.CREATED).build();
    } catch (IllegalArgumentException e) {
      LOG.error("illegal argument", e);
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessageDto(e.getMessage())).build();
    } catch (ConcurrentModificationException e) {
      LOG.error("concurrent modification ", e);
      return Response.status(Response.Status.CONFLICT).entity(new ErrorMessageDto(e.getMessage())).build();
    } catch (Exception exception) {
      LOG.error("internal error", exception);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }

  @ApiOperation(
      value = "delete error type",
      response = String.class,
      code = 202
  )
  @Path("error-types/{errorKey}")
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("errorKey") String errorKey) {
    try {
      customEntityService.deleteErrorType(errorKey);
      return Response.status(Response.Status.OK).build();
    } catch (ErrorTypeCannotBeDeletedException e) {
      LOG.error("error type can not be delete with key {}", errorKey, e);
      return Response.status(Response.Status.CONFLICT).entity(new ErrorMessageDto(e.getMessage())).build();
    } catch (Exception exception) {
      LOG.error("internal error", exception);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }

  @ApiOperation(
      value = "get error type",
      response = ErrorTypeDto.class
  )
  @Path("error-types/{errorKey}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCustomEntity(@PathParam("errorKey") String errorKey) {
    try {
      Optional<ErrorTypeDto> errorTypeDto = customEntityService.getErrorTypeByKey(errorKey);
      if (errorTypeDto.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND).entity("not found entity with error key" + errorKey).build();
      }

      return Response.status(Response.Status.OK).entity(customEntityService.getErrorTypeByKey(errorKey).get()).build();
    } catch (IllegalArgumentException e) {
      LOG.error("illegal argument", e);
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessageDto(e.getMessage())).build();
    } catch (Exception exception) {
      LOG.error("internal error", exception);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }

  @ApiOperation(
          value = "update error types",
          response = String.class
  )
  @PUT
  @Path("error-types")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateCustomEntity(@RequestBody List<ErrorTypeDto> errorTypeDtos) {
    if (CollectionUtils.isEmpty(errorTypeDtos)) {
      return Response.status(Response.Status.BAD_REQUEST).entity("entity can not be empty").build();
    }

    if (errorTypeDtos.size() > 100) {
      return Response.status(Response.Status.BAD_REQUEST).entity("max size value for update entities is 100").build();
    }

    try {
      customEntityService.updateErrorType(errorTypeDtos);
      return Response.status(Response.Status.OK).build();
    } catch (IllegalArgumentException e) {
      LOG.error("illegal argument", e);
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessageDto(e.getMessage())).build();
    } catch (ConcurrentModificationException e) {
      LOG.error("errorType entity was created another session", e);
      return Response.status(Response.Status.CONFLICT).entity(new ErrorMessageDto(e.getMessage())).build();
    } catch (Exception exception) {
      LOG.error("internal error", exception);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }


  @ApiOperation(
      value = "get all error types dictionary",
      response = ErrorTypeDto.class,
      responseContainer = "List"
  )
  @Path("error-types")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllCustomEntity() {
    try {
      List<ErrorTypeDto> errorTypeDtos = customEntityService.getAllErrorType();
      return Response.status(Response.Status.OK).entity(errorTypeDtos).build();
    } catch (Exception exception) {
      LOG.error("internal error", exception);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }
}
