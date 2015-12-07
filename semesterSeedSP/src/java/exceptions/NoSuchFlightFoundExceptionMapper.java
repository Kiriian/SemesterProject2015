/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Pernille
 */
@Provider
public class NoSuchFlightFoundExceptionMapper implements ExceptionMapper<NoSuchFlightFoundException>
{

    private static Gson gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    @Context
    ServletContext context;

    @Override
    public Response toResponse(NoSuchFlightFoundException e)
    {
        JsonObject errorObj = new JsonObject();
        if (Boolean.valueOf(context.getInitParameter("debug")))
        {
            errorObj.addProperty("httpError", 404);
            errorObj.addProperty("errorCode", 1);
            errorObj.addProperty("message", "No flights were found within the given parameters.");
            ErrorObject errObj = gson.fromJson(errorObj, ErrorObject.class);
            String err = gson.toJson(errObj);
        }
        return Response.status(Response.Status.NOT_FOUND).entity(errorObj.toString()).type(MediaType.APPLICATION_JSON).build();
    }

}
