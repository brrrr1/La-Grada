import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { EquipoService, Equipo } from '../../services/equipo.service';
import { EventoService, Evento } from '../../services/evento.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

interface EquipoCard extends Equipo {
  escudoUrl?: string;
  fondoUrl?: string;
}

interface EventoCard extends Evento {
  escudo1Url?: string;
  escudo2Url?: string;
  fondo1Url?: string;
  fondo2Url?: string;
}

const BASE_URL = 'http://localhost:8080';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  equipos: EquipoCard[] = [];
  eventos: EventoCard[] = [];
  eventosFuturos: EventoCard[] = [];
  eventosEquipoFavorito: EventoCard[] = [];
  loading = true;
  loadingEventos = true;
  loadingEventosFuturos = false;
  loadingEventosEquipoFavorito = false;
  errorMsg = '';
  errorEventos = '';
  errorEventosFuturos = '';
  errorEventosEquipoFavorito = '';
  userName: string | null = null;

  constructor(
    public auth: AuthService,
    private equipoService: EquipoService,
    private eventoService: EventoService,
    private http: HttpClient
  ) {}

  ngOnInit() {
    this.equipoService.getEquipos().subscribe({
      next: (data) => {
        this.equipos = data;
        this.loadImages();
        this.loading = false;
      },
      error: (err) => {
        this.errorMsg = 'Error al cargar los equipos';
        this.loading = false;
      }
    });
    this.eventoService.getProximosEventos().subscribe({
      next: (data) => {
        this.eventos = data;
        this.loadEventoEscudos();
        this.loadingEventos = false;
      },
      error: (err) => {
        this.errorEventos = 'Error al cargar los eventos';
        this.loadingEventos = false;
      }
    });
    this.loadUserName();
    this.loadEventosFuturos();
    this.loadEventosEquipoFavorito();
  }

  loadImages() {
    const token = this.auth.getToken() || undefined;
    this.equipos.forEach((equipo, idx) => {
      this.equipoService.downloadImage(equipo.fotoEscudo, token).subscribe({
        next: (blob) => {
          this.equipos[idx].escudoUrl = URL.createObjectURL(blob);
        },
        error: () => {
          this.equipos[idx].escudoUrl = undefined;
        }
      });
      this.equipoService.downloadImage(equipo.fotoFondo, token).subscribe({
        next: (blob) => {
          this.equipos[idx].fondoUrl = URL.createObjectURL(blob);
        },
        error: () => {
          this.equipos[idx].fondoUrl = undefined;
        }
      });
    });
  }

  loadEventoEscudos() {
    const token = this.auth.getToken() || undefined;
    this.eventos.forEach((evento, idx) => {
      this.equipoService.downloadImage(evento.equipo1.fotoEscudo, token).subscribe({
        next: (blob) => {
          this.eventos[idx].escudo1Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventos[idx].escudo1Url = undefined;
        }
      });
      this.equipoService.downloadImage(evento.equipo2.fotoEscudo, token).subscribe({
        next: (blob) => {
          this.eventos[idx].escudo2Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventos[idx].escudo2Url = undefined;
        }
      });
      this.equipoService.downloadImage(evento.equipo1.fotoFondo, token).subscribe({
        next: (blob) => {
          (this.eventos[idx] as any).fondo1Url = URL.createObjectURL(blob);
        },
        error: () => {
          (this.eventos[idx] as any).fondo1Url = undefined;
        }
      });
      this.equipoService.downloadImage(evento.equipo2.fotoFondo, token).subscribe({
        next: (blob) => {
          (this.eventos[idx] as any).fondo2Url = URL.createObjectURL(blob);
        },
        error: () => {
          (this.eventos[idx] as any).fondo2Url = undefined;
        }
      });
    });
  }

  loadUserName() {
    const token = this.auth.getToken();
    if (!token) return;
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    this.http.get<{ nombre: string }>(`${BASE_URL}/me`, { headers }).subscribe({
      next: (data) => {
        this.userName = data.nombre;
      },
      error: () => {
        this.userName = null;
      }
    });
  }

  loadEventosFuturos() {
    if (!this.auth.isLoggedIn()) return;
    this.loadingEventosFuturos = true;
    this.errorEventosFuturos = '';
    const token = this.auth.getToken();
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    this.http.get<EventoCard[]>(`${BASE_URL}/eventos-futuros`, { headers }).subscribe({
      next: (data) => {
        this.eventosFuturos = data;
        this.loadEscudosEventosFuturos();
        this.loadingEventosFuturos = false;
      },
      error: () => {
        this.errorEventosFuturos = 'Error al cargar tus próximos eventos';
        this.loadingEventosFuturos = false;
      }
    });
  }

  loadEscudosEventosFuturos() {
    const token = this.auth.getToken() || undefined;
    this.eventosFuturos.forEach((evento, idx) => {
      this.equipoService.downloadImage(evento.equipo1.fotoEscudo, token).subscribe({
        next: (blob) => {
          this.eventosFuturos[idx].escudo1Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventosFuturos[idx].escudo1Url = undefined;
        }
      });
      this.equipoService.downloadImage(evento.equipo2.fotoEscudo, token).subscribe({
        next: (blob) => {
          this.eventosFuturos[idx].escudo2Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventosFuturos[idx].escudo2Url = undefined;
        }
      });
      this.equipoService.downloadImage(evento.equipo1.fotoFondo, token).subscribe({
        next: (blob) => {
          this.eventosFuturos[idx].fondo1Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventosFuturos[idx].fondo1Url = undefined;
        }
      });
      this.equipoService.downloadImage(evento.equipo2.fotoFondo, token).subscribe({
        next: (blob) => {
          this.eventosFuturos[idx].fondo2Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventosFuturos[idx].fondo2Url = undefined;
        }
      });
    });
  }

  loadEventosEquipoFavorito() {
    if (!this.auth.isLoggedIn()) return;
    this.loadingEventosEquipoFavorito = true;
    this.errorEventosEquipoFavorito = '';
    this.eventoService.getProximosEventosEquipoFavorito().subscribe({
      next: (data) => {
        this.eventosEquipoFavorito = data;
        this.loadEscudosEventosEquipoFavorito();
        this.loadingEventosEquipoFavorito = false;
      },
      error: () => {
        this.errorEventosEquipoFavorito = 'Error al cargar los eventos de tu equipo favorito';
        this.loadingEventosEquipoFavorito = false;
      }
    });
  }

  loadEscudosEventosEquipoFavorito() {
    const token = this.auth.getToken() || undefined;
    this.eventosEquipoFavorito.forEach((evento, idx) => {
      this.equipoService.downloadImage(evento.equipo1.fotoEscudo, token).subscribe({
        next: (blob) => {
          this.eventosEquipoFavorito[idx].escudo1Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventosEquipoFavorito[idx].escudo1Url = undefined;
        }
      });
      this.equipoService.downloadImage(evento.equipo2.fotoEscudo, token).subscribe({
        next: (blob) => {
          this.eventosEquipoFavorito[idx].escudo2Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventosEquipoFavorito[idx].escudo2Url = undefined;
        }
      });
      this.equipoService.downloadImage(evento.equipo1.fotoFondo, token).subscribe({
        next: (blob) => {
          this.eventosEquipoFavorito[idx].fondo1Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventosEquipoFavorito[idx].fondo1Url = undefined;
        }
      });
      this.equipoService.downloadImage(evento.equipo2.fotoFondo, token).subscribe({
        next: (blob) => {
          this.eventosEquipoFavorito[idx].fondo2Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventosEquipoFavorito[idx].fondo2Url = undefined;
        }
      });
    });
  }
}
