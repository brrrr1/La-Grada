import { Component, OnInit } from '@angular/core';
import { EventoService, Evento } from '../../services/evento.service';
import { EquipoService } from '../../services/equipo.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

export interface EventoCard extends Evento {
  escudo1Url?: string;
  escudo2Url?: string;
  fondo1Url?: string;
  fondo2Url?: string;
}

@Component({
  selector: 'app-eventos-list',
  templateUrl: './eventos-list.component.html',
  styleUrls: ['./eventos-list.component.css']
})
export class EventosListComponent implements OnInit {
  eventos: EventoCard[] = [];
  loading = true;
  errorMsg = '';
  tabs = [
    { label: 'Todos', value: 'all' },
    { label: 'Cotidianos', value: 'cotidianos' },
    { label: 'Importantes', value: 'importantes' },
    { label: 'Finales', value: 'finales' }
  ];
  activeTab = 'all';

  nombreEquipo = '';
  nombreEvento = '';
  tieneEntradasDisponibles = false;
  private filterSubject = new Subject<void>();

  constructor(
    private eventoService: EventoService,
    private equipoService: EquipoService,
    private auth: AuthService,
    private router: Router
  ) {
    this.filterSubject.pipe(
      debounceTime(300)
    ).subscribe(() => {
      this.aplicarFiltros();
    });
  }

  ngOnInit() {
    this.aplicarFiltros();
  }

  onTabChange(tab: string) {
    this.activeTab = tab;
    this.aplicarFiltros();
  }

  onFilterChange() {
    this.filterSubject.next();
  }

  aplicarFiltros() {
    this.loading = true;
    let obs;
    switch (this.activeTab) {
      case 'cotidianos':
        obs = this.eventoService.getProximosEventosCotidianos(
          this.nombreEquipo,
          this.nombreEvento,
          this.tieneEntradasDisponibles
        );
        break;
      case 'importantes':
        obs = this.eventoService.getProximosEventosImportantes(
          this.nombreEquipo,
          this.nombreEvento,
          this.tieneEntradasDisponibles
        );
        break;
      case 'finales':
        obs = this.eventoService.getProximosEventosFinales(
          this.nombreEquipo,
          this.nombreEvento,
          this.tieneEntradasDisponibles
        );
        break;
      default:
        obs = this.eventoService.getProximosEventosAll(
          this.nombreEquipo,
          this.nombreEvento,
          this.tieneEntradasDisponibles
        );
    }
    obs.subscribe({
      next: (data) => {
        this.eventos = data;
        this.loadImages();
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'Error al cargar los eventos';
        this.loading = false;
      }
    });
  }

  loadImages() {
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
          this.eventos[idx].fondo1Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventos[idx].fondo1Url = undefined;
        }
      });
      this.equipoService.downloadImage(evento.equipo2.fotoFondo, token).subscribe({
        next: (blob) => {
          this.eventos[idx].fondo2Url = URL.createObjectURL(blob);
        },
        error: () => {
          this.eventos[idx].fondo2Url = undefined;
        }
      });
    });
  }

  onEventoClick(evento: EventoCard) {
    this.router.navigate(['/evento', evento.id]);
  }
}
