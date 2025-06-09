import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = 'http://localhost:8080';

export interface Entrada {
  id: string;
  usuarioId: string;
  usuarioNombre: string;
  usuarioApellidos: string;
  usuarioCorreo: string;
}

export interface Evento {
  id: string;
  nombre: string;
  descripcion: string;
  fechaYHora: string;
  equipo1: {
    id: string;
    nombre: string;
    fotoEscudo: string;
    fotoFondo: string;
  };
  equipo2: {
    id: string;
    nombre: string;
    fotoEscudo: string;
    fotoFondo: string;
  };
  entradasRestantes: number;
  entradasTotales: number;
  precio: number;
  tipoEvento: string;
  entradas: Entrada[];
}

export interface GetEntradaDto {
  id: string;
  usuarioId: string;
  usuarioNombre: string;
  usuarioApellidos: string;
  usuarioCorreo: string;
  evento: any;
  qrBase64: string;
}

export interface CreateEventoDto {
  nombre: string;
  descripcion: string;
  fechaYHora: string;
  equipo1Id: string;
  equipo2Id: string;
  entradasTotales: number;
  precio: number;
  tipo: string;
}

@Injectable({
  providedIn: 'root'
})
export class EventoService {
  constructor(private http: HttpClient) {}

  getProximosEventos(filtros?: any): Observable<Evento[]> {
    let params: any = {};
    if (filtros) {
      if (filtros.nombre) params.nombre = filtros.nombre;
      if (filtros.equipo) params.equipo = filtros.equipo;
      if (filtros.aunQuedanEntradas) params.aunQuedanEntradas = true;
    }
    return this.http.get<Evento[]>(`${BASE_URL}/evento/proximos`, { params });
  }

  getEventosPorEquipo(nombreEquipo: string): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${BASE_URL}/evento/equipo/${encodeURIComponent(nombreEquipo)}`);
  }

  getEventoPorId(id: string): Observable<Evento> {
    return this.http.get<Evento>(`${BASE_URL}/evento/${id}`);
  }

  buyTicket(eventId: string) {
    return this.http.post(`${BASE_URL}/user/buy-ticket/${eventId}`, {});
  }

  getProximosEventosAll(nombreEquipo?: string, nombreEvento?: string, tieneEntradasDisponibles?: boolean): Observable<Evento[]> {
    const params: any = {};
    if (nombreEquipo) params.nombreEquipo = nombreEquipo;
    if (nombreEvento) params.nombreEvento = nombreEvento;
    if (tieneEntradasDisponibles !== undefined) params.tieneEntradasDisponibles = tieneEntradasDisponibles;
    return this.http.get<Evento[]>(`${BASE_URL}/evento/proximos/all`, { params });
  }

  getProximosEventosCotidianos(nombreEquipo?: string, nombreEvento?: string, tieneEntradasDisponibles?: boolean): Observable<Evento[]> {
    const params: any = {};
    if (nombreEquipo) params.nombreEquipo = nombreEquipo;
    if (nombreEvento) params.nombreEvento = nombreEvento;
    if (tieneEntradasDisponibles !== undefined) params.tieneEntradasDisponibles = tieneEntradasDisponibles;
    return this.http.get<Evento[]>(`${BASE_URL}/evento/proximos/cotidianos`, { params });
  }

  getProximosEventosImportantes(nombreEquipo?: string, nombreEvento?: string, tieneEntradasDisponibles?: boolean): Observable<Evento[]> {
    const params: any = {};
    if (nombreEquipo) params.nombreEquipo = nombreEquipo;
    if (nombreEvento) params.nombreEvento = nombreEvento;
    if (tieneEntradasDisponibles !== undefined) params.tieneEntradasDisponibles = tieneEntradasDisponibles;
    return this.http.get<Evento[]>(`${BASE_URL}/evento/proximos/importantes`, { params });
  }

  getProximosEventosFinales(nombreEquipo?: string, nombreEvento?: string, tieneEntradasDisponibles?: boolean): Observable<Evento[]> {
    const params: any = {};
    if (nombreEquipo) params.nombreEquipo = nombreEquipo;
    if (nombreEvento) params.nombreEvento = nombreEvento;
    if (tieneEntradasDisponibles !== undefined) params.tieneEntradasDisponibles = tieneEntradasDisponibles;
    return this.http.get<Evento[]>(`${BASE_URL}/evento/proximos/finales`, { params });
  }

  getEntradasFuturasUsuario(): Observable<GetEntradaDto[]> {
    return this.http.get<GetEntradaDto[]>(`${BASE_URL}/user/entradas/futuras`);
  }

  getEntradasPasadasUsuario(): Observable<GetEntradaDto[]> {
    return this.http.get<GetEntradaDto[]>(`${BASE_URL}/user/entradas/pasadas`);
  }

  getProximosEventosEquipoFavorito(): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${BASE_URL}/user/favorite-team-events`);
  }

  getAllEventos(): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${BASE_URL}/evento/todos`);
  }

  deleteEvento(id: string) {
    return this.http.delete(`${BASE_URL}/evento/${id}`);
  }

  updateEvento(id: string, eventoData: any): Observable<Evento> {
    return this.http.put<Evento>(`${BASE_URL}/evento/${id}`, eventoData);
  }

  createEvento(eventoData: CreateEventoDto): Observable<Evento> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<Evento>(`${BASE_URL}/evento`, eventoData, { headers });
  }
}
