package com.nttdata.peru.apps.services;

import com.nttdata.peru.apps.MyMessagingApplication;
import com.nttdata.peru.apps.dto.DetalleVentaDTO;
import com.nttdata.peru.apps.dto.VentaDTO;
import com.nttdata.peru.apps.entities.Cliente;
import com.nttdata.peru.apps.entities.DetalleVenta;
import com.nttdata.peru.apps.entities.Producto;
import com.nttdata.peru.apps.entities.Venta;
import com.nttdata.peru.apps.repositories.ClienteRepository;
import com.nttdata.peru.apps.repositories.DetalleVentaRepository;
import com.nttdata.peru.apps.repositories.ProductoRepository;
import com.nttdata.peru.apps.repositories.VentaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class VentaService {

    @Inject
    VentaRepository ventaRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ProductoRepository productoRepository;

    @Inject
    DetalleVentaRepository detalleVentaRepository;

    @Inject
    MyMessagingApplication messagingApplication;

    public List<VentaDTO> listarTodos() {
        return ventaRepository.listAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VentaDTO buscarPorId(Long id) {
        Venta venta = ventaRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));
        return toDTO(venta);
    }

    public List<VentaDTO> buscarPorClienteId(Long clienteId) {
        return ventaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public VentaDTO crear(VentaDTO dto) {
        Cliente cliente = clienteRepository.findByIdOptional(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + dto.getClienteId()));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());
        venta.setEstado("PENDIENTE");

        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVentaDTO detalleDTO : dto.getDetalles()) {
            Producto producto = productoRepository.findByIdOptional(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + detalleDTO.getProductoId()));

            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(detalleDTO.getCantidad())));

            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            total = total.add(detalle.getSubtotal());
            detalles.add(detalle);
        }

        venta.setTotal(total);
        venta.setDetalles(detalles);
        ventaRepository.persist(venta);

        VentaDTO ventaDTO = toDTO(venta);
        messagingApplication.publicarVenta(ventaDTO);

        return ventaDTO;
    }

    @Transactional
    public VentaDTO actualizarEstado(Long id, String estado) {
        Venta venta = ventaRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));
        venta.setEstado(estado);
        return toDTO(venta);
    }

    private VentaDTO toDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setClienteId(venta.getCliente().getId());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado());
        if (venta.getDetalles() != null) {
            dto.setDetalles(venta.getDetalles()
                    .stream()
                    .map(this::toDetalleDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private DetalleVentaDTO toDetalleDTO(DetalleVenta detalle) {
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setId(detalle.getId());
        dto.setProductoId(detalle.getProducto().getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}