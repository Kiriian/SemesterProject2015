package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Role;
import entity.User;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("saveUser")

public class HandleUser 
{
  
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveUser(String user) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        Role r = new Role("User");
        JsonObject json = new JsonParser().parse(user).getAsJsonObject();
        User u = new User();
        u.setUserName(json.get("username").getAsString());
        u.setPassword(json.get("password").getAsString());
        u.AddRole(r);
        UserFacade f = new UserFacade();
        f.saveUser(u);
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("username", u.getUserName());
        return Response.ok(new Gson().toJson(responseJson)).build();
    }
  }
 
