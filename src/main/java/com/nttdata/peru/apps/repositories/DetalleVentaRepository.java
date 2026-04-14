package com.nttdata.peru.apps.repositories;

import com.nttdata.peru.apps.entities.DetalleVenta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DetalleVentaRepository implements PanacheRepository<DetalleVenta> {

    public List<DetalleVenta> findByVentaId(Long ventaId) {
        return find("venta.id", ventaId).list();
    }

    public List<DetalleVenta> findByProductoId(Long productoId) {
        return find("producto.id", productoId).list();
    }
}