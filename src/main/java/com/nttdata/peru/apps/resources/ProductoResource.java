package com.nttdata.peru.apps.resources;

import com.nttdata.peru.apps.dto.ProductoDTO;
import com.nttdata.peru.apps.services.ProductoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoResource {

    @Inject
    ProductoService productoService;

    @GET
    public Response listarTodos() {
        List<ProductoDTO> productos = productoService.listarTodos();
        return Response.ok(productos).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            ProductoDTO producto = productoService.buscarPorId(id);
            return Response.ok(producto).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/buscar")
    public Response buscarPorNombre(@QueryParam("nombre") String nombre) {
        List<ProductoDTO> productos = productoService.buscarPorNombre(nombre);
        return Response.ok(productos).build();
    }

    @GET
    @Path("/con-stock")
    public Response listarConStock() {
        List<ProductoDTO> productos = productoService.listarConStock();
        return Response.ok(productos).build();
    }

    @POST
    public Response crear(@Valid ProductoDTO dto) {
        try {
            ProductoDTO producto = productoService.crear(dto);
            return Response.status(Response.Status.CREATED)
                    .entity(producto).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, @Valid ProductoDTO dto) {
        try {
            ProductoDTO producto = productoService.actualizar(id, dto);
            return Response.ok(producto).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        try {
            productoService.eliminar(id);
            return Response.ok("Producto eliminado correctamente").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
}