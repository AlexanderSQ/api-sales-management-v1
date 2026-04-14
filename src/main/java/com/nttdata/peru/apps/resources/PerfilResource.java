package com.nttdata.peru.apps.resources;

import com.nttdata.peru.apps.client.PlatziClient;
import com.nttdata.peru.apps.dto.LoginDTO;
import com.nttdata.peru.apps.dto.PerfilDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/perfil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilResource {

    @Inject
    @RestClient
    PlatziClient platziClient;

    @POST
    @Path("/login")
    public Response login(LoginDTO loginDTO) {
        try {
            PerfilDTO perfil = platziClient.login(loginDTO);
            return Response.ok(perfil).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/me")
    public Response obtenerPerfil(@HeaderParam("Authorization") String token) {
        try {
            PerfilDTO perfil = platziClient.obtenerPerfil(token);
            return Response.ok(perfil).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage()).build();
        }
    }
}