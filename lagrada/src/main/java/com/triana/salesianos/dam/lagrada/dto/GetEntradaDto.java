package com.triana.salesianos.dam.lagrada.dto;

import java.util.UUID;
import com.triana.salesianos.dam.lagrada.model.Entrada;
import com.triana.salesianos.dam.lagrada.model.User;
import com.triana.salesianos.dam.lagrada.dto.GetEventoDto;
import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.util.MailService;

public record GetEntradaDto(
    UUID id,
    UUID usuarioId,
    String usuarioNombre,
    String usuarioApellidos,
    String usuarioCorreo,
    GetEventoDto evento,
    String qrBase64
) {
    public static GetEntradaDto from(Entrada entrada, MailService mailService) {
        User u = entrada.getUsuario();
        Evento e = entrada.getEvento();
        String qrContent = e.getId() + ":" + u.getId();
        String qrBase64 = mailService.generateQRCodeBase64(qrContent, 250, 250);
        return new GetEntradaDto(
            entrada.getId(),
            u.getId(),
            u.getNombre(),
            u.getApellidos(),
            u.getCorreo(),
            GetEventoDto.from(e),
            qrBase64
        );
    }
}
