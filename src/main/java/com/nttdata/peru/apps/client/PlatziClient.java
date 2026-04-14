package com.nttdata.peru.apps.client;

import com.nttdata.peru.apps.dto.LoginDTO;
import com.nttdata.peru.apps.dto.PerfilDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "platzi-api")
@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PlatziClient {

    @POST
    @Path("/auth/login")
    PerfilDTO login(LoginDTO loginDTO);

    @GET
    @Path("/auth/profile")
    PerfilDTO obtenerPerfil(@HeaderParam("Authorization") String token);
}