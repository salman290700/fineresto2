package org.babagroup.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import javax.xml.crypto.Data;

@Provider
public class GlobalException implements ExceptionMapper<DataError> {
    @Override
    public Response toResponse(DataError dataError) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorObject(dataError.getMessage())).build();
    }
}
