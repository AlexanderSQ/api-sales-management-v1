package com.nttdata.peru.apps.repositories;

import com.nttdata.peru.apps.entities.Producto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductoRepository implements PanacheRepository<Producto> {

    public List<Producto> findByNombre(String nombre) {
        return find("nombre like ?1", "%" + nombre + "%").list();
    }

    public List<Producto> findWithStock() {
        return find("stock > 0").list();
    }
}