package com.triana.salesianos.dam.lagrada;

import com.triana.salesianos.dam.lagrada.dto.CreateEventoDto;
import com.triana.salesianos.dam.lagrada.dto.GetEventoDto;
import com.triana.salesianos.dam.lagrada.dto.UpdateEventoDto;
import com.triana.salesianos.dam.lagrada.model.Equipo;
import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.model.TipoEvento;
import com.triana.salesianos.dam.lagrada.repo.EquipoRepository;
import com.triana.salesianos.dam.lagrada.repo.EventoRepository;
import com.triana.salesianos.dam.lagrada.repo.UserRepository;
import com.triana.salesianos.dam.lagrada.service.EventoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private EquipoRepository equipoRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventoService eventoService;

    private Equipo equipo1;
    private Equipo equipo2;
    private Evento evento;
    private UUID eventoId;
    private UUID equipo1Id;
    private UUID equipo2Id;

    @BeforeEach
    void setUp() {
        equipo1Id = UUID.randomUUID();
        equipo2Id = UUID.randomUUID();
        eventoId = UUID.randomUUID();

        equipo1 = new Equipo();
        equipo1.setId(equipo1Id);
        equipo2 = new Equipo();
        equipo2.setId(equipo2Id);

        evento = Evento.builder()
                .id(eventoId)
                .nombre("Partido Ejemplo")
                .descripcion("Un partido emocionante")
                .fechaYHora(LocalDateTime.now().plusDays(1))
                .equipo1(equipo1)
                .equipo2(equipo2)
                .entradasTotales(100)
                .entradasRestantes(100)
                .precio(10.0)
                .tipoEvento(TipoEvento.COTIDIANO)
                .build();
    }

    @Test
    void createEvento() {
        CreateEventoDto dto = new CreateEventoDto("Partido Ejemplo", "Un partido emocionante", LocalDateTime.now().plusDays(1), equipo1Id, equipo2Id, 100, 10.0, TipoEvento.COTIDIANO);

        when(equipoRepository.findById(equipo1Id)).thenReturn(Optional.of(equipo1));
        when(equipoRepository.findById(equipo2Id)).thenReturn(Optional.of(equipo2));
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        Evento created = eventoService.createEvento(dto);

        assertNotNull(created);
        assertEquals("Partido Ejemplo", created.getNombre());
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    void updateEvento() {
        UpdateEventoDto dto = new UpdateEventoDto("Nuevo Nombre", "Nueva Descripción", LocalDateTime.now().plusDays(2), equipo1Id, equipo2Id, 150, 12.0, TipoEvento.IMPORTANTE);

        when(eventoRepository.findById(eventoId)).thenReturn(Optional.of(evento));
        when(equipoRepository.findById(equipo1Id)).thenReturn(Optional.of(equipo1));
        when(equipoRepository.findById(equipo2Id)).thenReturn(Optional.of(equipo2));
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        Evento updated = eventoService.updateEvento(eventoId, dto);

        assertNotNull(updated);
        assertEquals("Nuevo Nombre", updated.getNombre());
        assertEquals(150, updated.getEntradasTotales());
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    void deleteEvento() {
        when(eventoRepository.findById(eventoId)).thenReturn(Optional.of(evento));
        doNothing().when(eventoRepository).delete(evento);

        eventoService.deleteEvento(eventoId);

        verify(eventoRepository, times(1)).delete(evento);
    }

    @Test
    void getAllNextEvents() {
        Pageable pageable = PageRequest.of(0, 10);
        when(eventoRepository.findNextEvents(pageable)).thenReturn(List.of(evento));

        List<Evento> eventos = eventoService.getAllNextEvents(pageable);

        assertFalse(eventos.isEmpty());
        assertEquals(1, eventos.size());
        verify(eventoRepository, times(1)).findNextEvents(pageable);
    }

    @Test
    void obtenerEventosFuturosPorEquipo() {
        when(eventoRepository.findByEquipoAndFechaFutura(anyString(), any(LocalDateTime.class)))
                .thenReturn(List.of(evento));

        List<GetEventoDto> eventos = eventoService.obtenerEventosFuturosPorEquipo("Equipo A");

        assertFalse(eventos.isEmpty());
        assertEquals(1, eventos.size());
        verify(eventoRepository, times(1)).findByEquipoAndFechaFutura(anyString(), any(LocalDateTime.class));
    }
}
