package com.nttdata.peru.apps.repositories;

import com.nttdata.peru.apps.entities.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Optional<Cliente> findByDni(String dni) {
        return find("dni", dni).firstResultOptional();
    }

    public Optional<Cliente> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}