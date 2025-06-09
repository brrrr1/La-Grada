import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Evento, EventoService } from '../../../services/evento.service';
import { EquipoService } from '../../../services/equipo.service';
import { AuthService } from '../../../services/auth.service';
import { NotificationComponent } from '../../../components/notification/notification.component';

@Component({
  selector: 'app-evento-detalle',
  templateUrl: './evento-detalle.component.html',
  styleUrl: './evento-detalle.component.css'
})
export class EventoDetalleComponent implements OnInit {
  evento?: Evento;
  cargando = true;
  error?: string;
  escudo1Url?: string;
  escudo2Url?: string;
  fondo1Url?: string;
  fondo2Url?: string;
  usuarioId: string | null = null;
  yaTieneEntrada = false;
  comprando = false;
  showBuyModal = false;
  modalTexto = '';
  notification = {
    show: false,
    message: '',
    type: 'success' as 'success' | 'error' | 'info',
    actionButton: undefined as { text: string; route: string } | undefined
  };

  constructor(
    private route: ActivatedRoute,
    private eventoService: EventoService,
    private equipoService: EquipoService,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.usuarioId = this.auth.getUserId();
    if (id) {
      this.eventoService.getEventoPorId(id).subscribe({
        next: (evento: Evento) => {
          this.evento = evento;
          this.cargarImagenes();
          this.cargando = false;
          this.comprobarEntrada();
        },
        error: (err: any) => {
          this.error = 'No se pudo cargar el evento';
          this.cargando = false;
        }
      });
    } else {
      this.error = 'ID de evento no encontrado';
      this.cargando = false;
    }
  }

  cargarImagenes() {
    if (!this.evento) return;
    const token = this.auth.getToken() || undefined;
    this.equipoService.downloadImage(this.evento.equipo1.fotoEscudo, token).subscribe({
      next: (blob) => {
        this.escudo1Url = URL.createObjectURL(blob);
      },
      error: () => {
        this.escudo1Url = undefined;
      }
    });
    this.equipoService.downloadImage(this.evento.equipo2.fotoEscudo, token).subscribe({
      next: (blob) => {
        this.escudo2Url = URL.createObjectURL(blob);
      },
      error: () => {
        this.escudo2Url = undefined;
      }
    });
    this.equipoService.downloadImage(this.evento.equipo1.fotoFondo, token).subscribe({
      next: (blob) => {
        this.fondo1Url = URL.createObjectURL(blob);
      },
      error: () => {
        this.fondo1Url = undefined;
      }
    });
    this.equipoService.downloadImage(this.evento.equipo2.fotoFondo, token).subscribe({
      next: (blob) => {
        this.fondo2Url = URL.createObjectURL(blob);
      },
      error: () => {
        this.fondo2Url = undefined;
      }
    });
  }

  comprobarEntrada() {
    if (!this.evento || !this.usuarioId) {
      this.yaTieneEntrada = false;
      return;
    }
    this.yaTieneEntrada = this.evento.entradas.some(e => e.usuarioId === this.usuarioId);
  }

  comprarEntrada() {
    if (!this.evento || this.yaTieneEntrada || this.evento.entradasRestantes <= 0) return;
    this.comprando = true;
    this.hideNotification();
    this.eventoService.buyTicket(this.evento.id).subscribe({
      next: () => {
        this.evento!.entradasRestantes--;
        if (this.usuarioId) {
          this.evento!.entradas.push({
            id: 'fake',
            usuarioId: this.usuarioId,
            usuarioNombre: '',
            usuarioApellidos: '',
            usuarioCorreo: ''
          });
        }
        this.comprobarEntrada();
        this.comprando = false;
        this.showNotification('¡Entrada comprada con éxito!', 'success');
      },
      error: (err) => {
        this.comprando = false;
        this.showNotification(err?.error?.message || 'Error al comprar la entrada', 'error');
      }
    });
  }

  intentarComprarEntrada() {
    if (!this.evento || this.yaTieneEntrada || this.evento.entradasRestantes <= 0) return;
    this.modalTexto = `¿Seguro que quieres comprar una entrada para este evento por ${this.evento.precio.toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })}?`;
    this.showBuyModal = true;
  }

  confirmarCompraEntrada() {
    this.showBuyModal = false;
    this.comprarEntrada();
  }

  cancelarCompraEntrada() {
    this.showBuyModal = false;
  }

  get isLoggedIn(): boolean {
    return this.auth.isLoggedIn();
  }

  irADetalleEquipo1() {
    if (this.evento) {
      this.router.navigate(['/equipos', this.evento.equipo1.id]);
    }
  }

  irADetalleEquipo2() {
    if (this.evento) {
      this.router.navigate(['/equipos', this.evento.equipo2.id]);
    }
  }

  showNotification(message: string, type: 'success' | 'error' | 'info' = 'info') {
    this.notification = { 
      show: true, 
      message, 
      type,
      actionButton: type === 'success' && message.includes('comprada') ? { text: 'Ver entradas', route: '/entradas' } : undefined
    };
  }

  hideNotification() {
    this.notification.show = false;
  }

  buscarEnYoutube() {
    if (this.evento) {
      const searchQuery = encodeURIComponent(`${this.evento.nombre} resumen`);
      window.open(`https://www.youtube.com/results?search_query=${searchQuery}`, '_blank');
    }
  }
}
