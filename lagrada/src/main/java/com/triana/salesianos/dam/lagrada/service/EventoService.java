package com.triana.salesianos.dam.lagrada.service;

import com.triana.salesianos.dam.lagrada.dto.CreateEventoDto;
import com.triana.salesianos.dam.lagrada.dto.GetEventoDto;
import com.triana.salesianos.dam.lagrada.dto.UpdateEventoDto;
import com.triana.salesianos.dam.lagrada.model.Entrada;
import com.triana.salesianos.dam.lagrada.model.Equipo;
import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.model.User;
import com.triana.salesianos.dam.lagrada.repo.EquipoRepository;
import com.triana.salesianos.dam.lagrada.repo.EventoRepository;
import com.triana.salesianos.dam.lagrada.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EquipoRepository equipoRepository;
    private final UserRepository userRepository;

    public Evento createEvento(CreateEventoDto dto) {
        Optional<Equipo> equipo1 = equipoRepository.findById(dto.equipo1Id());
        Optional<Equipo> equipo2 = equipoRepository.findById(dto.equipo2Id());

        if (equipo1.isPresent() && equipo2.isPresent()) {
            Evento evento = Evento.builder()
                    .nombre(dto.nombre())
                    .descripcion(dto.descripcion())
                    .fechaYHora(dto.fechaYHora())
                    .equipo1(equipo1.get())
                    .equipo2(equipo2.get())
                    .entradasTotales(dto.entradasTotales())
                    .entradasRestantes(dto.entradasTotales()) // Inicialmente todas las entradas están disponibles
                    .precio(dto.precio())
                    .tipoEvento(dto.tipo()) // Asignar el tipo de evento
                    .build();

            return eventoRepository.save(evento);
        } else {
            throw new RuntimeException("Uno o ambos equipos no existen");
        }
    }

    /*@Transactional(readOnly = true)
    public List<Evento> getProximosEventosEquipoFavorito(User user) {
        // Forzamos la carga de las entradas asociadas para evitar problemas de LazyInitializationException
        user.getEntradas().size(); // Si es necesario acceder a las entradas

        // Obtener los próximos 4 eventos en los que el equipo favorito participe
        return eventoRepository.findProximosEventosPorEquipoFavorito(user.getEquipoFavorito(), PageRequest.of(0, 4));
    }*/

    /*@Transactional
    public List<Evento> verEventosDelEquipoFavorito(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return user.getEntradas().stream()
                .map(Entrada::getEvento)
                .collect(Collectors.toList());
    }*/

    public List<Evento> getAllNextEvents(Pageable pageable) {
        return eventoRepository.findNextEvents(pageable);
    }

    public List<Evento> getAllNextEventsNoPage() {
        return eventoRepository.findAllNextEvents();
    }

    public List<Evento> getAllNextEventsCotidianos() {
        return eventoRepository.findAllNextEventsCotidianos();
    }

    public List<Evento> getAllNextEventsImportantes() {
        return eventoRepository.findAllNextEventsImportantes();
    }

    public List<Evento> getAllNextEventsFinales() {
        return eventoRepository.findAllNextEventsFinales();
    }

    @Transactional
    public Evento updateEvento(UUID eventoId, UpdateEventoDto dto) {
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);

        if (eventoOpt.isPresent()) {
            Evento evento = eventoOpt.get();

            // Actualizar los campos del evento
            Optional<Equipo> equipo1 = equipoRepository.findById(dto.equipo1Id());
            Optional<Equipo> equipo2 = equipoRepository.findById(dto.equipo2Id());

            if (equipo1.isPresent() && equipo2.isPresent()) {
                evento.setNombre(dto.nombre());
                evento.setDescripcion(dto.descripcion());
                evento.setFechaYHora(dto.fechaYHora());
                evento.setEquipo1(equipo1.get());
                evento.setEquipo2(equipo2.get());
                evento.setEntradasTotales(dto.entradasTotales());
                evento.setEntradasRestantes(dto.entradasTotales());  // Asumimos que las entradas restantes son iguales a las totales al editar
                evento.setPrecio(dto.precio());
                evento.setTipoEvento(dto.tipo());

                return eventoRepository.save(evento);
            } else {
                throw new RuntimeException("Uno o ambos equipos no existen");
            }
        } else {
            throw new RuntimeException("Evento no encontrado");
        }
    }


    @Transactional
    public void deleteEvento(UUID eventoId) {
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);

        if (eventoOpt.isPresent()) {
            eventoRepository.delete(eventoOpt.get());
        } else {
            throw new RuntimeException("Evento no encontrado");
        }
    }

    public List<GetEventoDto> obtenerEventosFuturosPorEquipo(String equipo) {
        LocalDateTime fechaActual = LocalDateTime.now();
        return eventoRepository.findByEquipoAndFechaFutura(equipo, fechaActual)
                .stream()
                .map(GetEventoDto::from)
                .toList();
    }

    public Optional<Evento> findById(UUID id) {
        return eventoRepository.findById(id);
    }

}




