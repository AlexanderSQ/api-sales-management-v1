package com.nttdata.peru.apps.services;

import com.nttdata.peru.apps.dto.ClienteDTO;
import com.nttdata.peru.apps.entities.Cliente;
import com.nttdata.peru.apps.repositories.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.listAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        return toDTO(cliente);
    }

    public ClienteDTO buscarPorDni(String dni) {
        Cliente cliente = clienteRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con dni: " + dni));
        return toDTO(cliente);
    }

    @Transactional
    public ClienteDTO crear(ClienteDTO dto) {
        Cliente cliente = toEntity(dto);
        clienteRepository.persist(cliente);
        return toDTO(cliente);
    }

    @Transactional
    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setDni(dto.getDni());
        return toDTO(cliente);
    }

    @Transactional
    public void eliminar(Long id) {
        clienteRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        clienteRepository.deleteById(id);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setDni(cliente.getDni());
        return dto;
    }

    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setDni(dto.getDni());
        return cliente;
    }
}