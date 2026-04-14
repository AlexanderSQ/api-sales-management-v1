package com.nttdata.peru.apps.repositories;

import com.nttdata.peru.apps.entities.Venta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class VentaRepository implements PanacheRepository<Venta> {

    public List<Venta> findByClienteId(Long clienteId) {
        return find("cliente.id", clienteId).list();
    }

    public List<Venta> findByEstado(String estado) {
        return find("estado", estado).list();
    }
}