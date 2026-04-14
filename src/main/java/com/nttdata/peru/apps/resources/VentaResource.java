package com.nttdata.peru.apps.resources;

import com.nttdata.peru.apps.dto.VentaDTO;
import com.nttdata.peru.apps.services.VentaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/ventas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VentaResource {

    @Inject
    VentaService ventaService;

    @GET
    public Response listarTodos() {
        List<VentaDTO> ventas = ventaService.listarTodos();
        return Response.ok(ventas).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            VentaDTO venta = ventaService.buscarPorId(id);
            return Response.ok(venta).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/cliente/{clienteId}")
    public Response buscarPorClienteId(@PathParam("clienteId") Long clienteId) {
        List<VentaDTO> ventas = ventaService.buscarPorClienteId(clienteId);
        return Response.ok(ventas).build();
    }

    @POST
    public Response crear(@Valid VentaDTO dto) {
        try {
            VentaDTO venta = ventaService.crear(dto);
            return Response.status(Response.Status.CREATED)
                    .entity(venta).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/estado")
    public Response actualizarEstado(@PathParam("id") Long id, @QueryParam("estado") String estado) {
        try {
            VentaDTO venta = ventaService.actualizarEstado(id, estado);
            return Response.ok(venta).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
}