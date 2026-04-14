package com.nttdata.peru.apps.services;

import com.nttdata.peru.apps.dto.ProductoDTO;
import com.nttdata.peru.apps.entities.Producto;
import com.nttdata.peru.apps.repositories.ProductoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductoService {

    @Inject
    ProductoRepository productoRepository;

    public List<ProductoDTO> listarTodos() {
        return productoRepository.listAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO buscarPorId(Long id) {
        Producto producto = productoRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return toDTO(producto);
    }

    public List<ProductoDTO> buscarPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> listarConStock() {
        return productoRepository.findWithStock()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductoDTO crear(ProductoDTO dto) {
        Producto producto = toEntity(dto);
        productoRepository.persist(producto);
        return toDTO(producto);
    }

    @Transactional
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return toDTO(producto);
    }

    @Transactional
    public void eliminar(Long id) {
        productoRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        productoRepository.deleteById(id);
    }

    private ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        return dto;
    }

    private Producto toEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return producto;
    }
}