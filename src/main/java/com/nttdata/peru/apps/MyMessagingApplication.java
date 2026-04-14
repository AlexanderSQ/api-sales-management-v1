package com.nttdata.peru.apps;

import com.nttdata.peru.apps.dto.VentaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import io.smallrye.reactive.messaging.annotations.Blocking;

@ApplicationScoped
public class MyMessagingApplication {

    @Inject
    @Channel("sales-out")
    Emitter<String> ventaEmitter;

    @Inject
    ObjectMapper objectMapper;

    public void publicarVenta(VentaDTO ventaDTO) {
        try {
            String mensaje = objectMapper.writeValueAsString(ventaDTO);
            ventaEmitter.send(mensaje);
            System.out.println("Venta publicada en Kafka: " + mensaje);
        } catch (Exception e) {
            System.err.println("Error al publicar venta en Kafka: " + e.getMessage());
        }
    }

    @Incoming("sales-in")
    @Blocking
    public void consumirVenta(String mensaje) {
        try {
            VentaDTO ventaDTO = objectMapper.readValue(mensaje, VentaDTO.class);
            System.out.println("Venta recibida desde Kafka - ID: " + ventaDTO.getId()
                    + " Total: " + ventaDTO.getTotal()
                    + " Estado: " + ventaDTO.getEstado());
        } catch (Exception e) {
            System.err.println("Error al consumir venta desde Kafka: " + e.getMessage());
        }
    }
}