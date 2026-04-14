package com.nttdata.peru.apps.resources;

import com.nttdata.peru.apps.dto.ClienteDTO;
import com.nttdata.peru.apps.services.ClienteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    public Response listarTodos() {
        List<ClienteDTO> clientes = clienteService.listarTodos();
        return Response.ok(clientes).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            ClienteDTO cliente = clienteService.buscarPorId(id);
            return Response.ok(cliente).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/dni/{dni}")
    public Response buscarPorDni(@PathParam("dni") String dni) {
        try {
            ClienteDTO cliente = clienteService.buscarPorDni(dni);
            return Response.ok(cliente).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    public Response crear(@Valid ClienteDTO dto) {
        try {
            ClienteDTO cliente = clienteService.crear(dto);
            return Response.status(Response.Status.CREATED)
                    .entity(cliente).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, @Valid ClienteDTO dto) {
        try {
            ClienteDTO cliente = clienteService.actualizar(id, dto);
            return Response.ok(cliente).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        try {
            clienteService.eliminar(id);
            return Response.ok("Cliente eliminado correctamente").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
}