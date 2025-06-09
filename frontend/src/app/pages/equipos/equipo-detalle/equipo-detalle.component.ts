import { Component, OnInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EquipoService, Equipo } from '../../../services/equipo.service';
import { AuthService } from '../../../services/auth.service';
import { EventoService, Evento } from '../../../services/evento.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ModalComponent } from '../../../components/modal/modal/modal.component';

interface EventoCard extends Evento {
  escudo1Url?: string;
  escudo2Url?: string;
  fondo1Url?: string;
  fondo2Url?: string;
}

@Component({
  selector: 'app-equipo-detalle',
  templateUrl: './equipo-detalle.component.html',
  styleUrls: ['./equipo-detalle.component.css']
})
export class EquipoDetalleComponent implements OnInit {
  equipo: Equipo | null = null;
  escudoUrl?: string;
  fondoUrl?: string;
  loading = true;
  errorMsg = '';
  eventos: EventoCard[] = [];
  eventosLoading = false;
  eventosError = '';
  esFavorito: boolean = false;
  @ViewChild('modalConfirm', { static: false }) modalConfirm: ModalComponent | undefined;
  modalTitulo: string = '';
  modalTexto: string = '';
  modalConfirmText: string = '';
  modalCancelText: string = '';
  accionFavorito: 'quitar' | 'cambiar' | 'elegir' | null = null;
  equipoFavoritoNombre: string | null = null;
  mostrarModal: boolean = false;
  esAdmin: boolean = false;
  modoEdicion: boolean = false;
  editandoNombre: boolean = false;
  editandoEscudo: boolean = false;
  editandoFondo: boolean = false;
  @ViewChild('inputEscudo') inputEscudo!: ElementRef<HTMLInputElement>;
  @ViewChild('inputFondo') inputFondo!: ElementRef<HTMLInputElement>;

  constructor(
    private route: ActivatedRoute,
    private equipoService: EquipoService,
    private auth: AuthService,
    private eventoService: EventoService,
    private http: HttpClient
  ) {}

  async ngOnInit() {
    await this.checkIsAdmin();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.equipoService.getEquipoById(id).subscribe({
        next: (data) => {
          this.equipo = data;
          this.loadImages();
          this.loading = false;
          this.loadEventos();
          this.comprobarFavorito();
        },
        error: () => {
          this.errorMsg = 'Error al cargar el equipo';
          this.loading = false;
        }
      });
    } else {
      this.errorMsg = 'ID de equipo no válido';
      this.loading = false;
    }
  }

  async checkIsAdmin() {
    const token = this.auth.getToken();
    if (!token) {
      this.esAdmin = false;
      return;
    }
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    try {
      await this.http.get('http://localhost:8080/me/admin', { headers }).toPromise();
      this.esAdmin = true;
    } catch {
      this.esAdmin = false;
    }
  }

  loadImages() {
    if (!this.equipo) return;
    const token = this.auth.getToken() || undefined;
    this.equipoService.downloadImage(this.equipo.fotoEscudo, token).subscribe({
      next: (blob) => {
        this.escudoUrl = URL.createObjectURL(blob);
      },
      error: () => {
        this.escudoUrl = undefined;
      }
    });
    this.equipoService.downloadImage(this.equipo.fotoFondo, token).subscribe({
      next: (blob) => {
        this.fondoUrl = URL.createObjectURL(blob);
      },
      error: () => {
        this.fondoUrl = undefined;
      }
    });
  }

  loadEventos() {
    if (!this.equipo) return;
    this.eventosLoading = true;
    this.eventosError = '';
    this.eventoService.getEventosPorEquipo(this.equipo.nombre).subscribe({
      next: (data) => {
        this.eventos = data;
        this.loadEventoEscudos();
        this.eventosLoading = false;
      },
      error: () => {
        this.eventosError = 'No se pudieron cargar los próximos eventos.';
        this.eventosLoading = false;
      }
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

  comprobarFavorito() {
    if (!this.equipo) return;
    const token = this.auth.getToken();
    if (!token) return;
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    this.http.get<any>('http://localhost:8080/me', { headers }).subscribe({
      next: (data) => {
        this.equipoFavoritoNombre = data.equipoFavorito || null;
        if (data.equipoFavorito && this.equipo) {
          this.esFavorito = data.equipoFavorito === this.equipo.nombre;
        } else {
          this.esFavorito = false;
        }
      },
      error: () => {
        this.esFavorito = false;
        this.equipoFavoritoNombre = null;
      }
    });
  }

  onHeartClick() {
    if (!this.equipo) return;
    if (!this.equipoFavoritoNombre) {
      this.accionFavorito = 'elegir';
      this.marcarComoFavorito();
      return;
    }
    if (this.esFavorito) {
      this.accionFavorito = 'quitar';
      this.modalTitulo = 'Quitar equipo favorito';
      this.modalTexto = `¿Seguro que quieres quitar a ${this.equipo.nombre} como tu equipo favorito?`;
      this.modalConfirmText = 'Quitar';
      this.modalCancelText = 'Cancelar';
      this.mostrarModal = true;
      return;
    }
    if (this.equipoFavoritoNombre && !this.esFavorito) {
      this.accionFavorito = 'cambiar';
      this.modalTitulo = 'Cambiar equipo favorito';
      this.modalTexto = `¿Seguro que quieres cambiar tu equipo favorito de ${this.equipoFavoritoNombre} a ${this.equipo.nombre}?`;
      this.modalConfirmText = 'Cambiar';
      this.modalCancelText = 'Cancelar';
      this.mostrarModal = true;
      return;
    }
  }

  onModalConfirm() {
    if (this.accionFavorito === 'quitar') {
      this.quitarFavorito();
    } else if (this.accionFavorito === 'cambiar') {
      this.cambiarFavorito();
    }
    this.accionFavorito = null;
    this.mostrarModal = false;
  }

  onModalCancel() {
    this.accionFavorito = null;
    this.mostrarModal = false;
  }

  marcarComoFavorito() {
    if (!this.equipo) return;
    this.auth.chooseFavoriteTeam(this.equipo.id).subscribe({
      next: () => {
        this.comprobarFavorito();
      }
    });
  }

  quitarFavorito() {
    this.auth.removeFavoriteTeam().subscribe({
      next: () => {
        this.comprobarFavorito();
      }
    });
  }

  cambiarFavorito() {
    if (!this.equipo) return;
    this.auth.changeFavoriteTeam(this.equipo.id).subscribe({
      next: () => {
        this.comprobarFavorito();
      }
    });
  }

  activarModoEdicion() {
    this.modoEdicion = true;
  }

  cancelarModoEdicion() {
    this.modoEdicion = false;
    this.editandoNombre = false;
    this.editandoEscudo = false;
    this.editandoFondo = false;
  }

  activarEdicionNombre() {
    this.editandoNombre = true;
  }

  activarEdicionEscudo() {
    if (this.inputEscudo) {
      this.inputEscudo.nativeElement.value = '';
      this.inputEscudo.nativeElement.click();
    }
  }

  activarEdicionFondo() {
    if (this.inputFondo) {
      this.inputFondo.nativeElement.value = '';
      this.inputFondo.nativeElement.click();
    }
  }

  guardarNombre(nuevoNombre: string) {
    if (!this.equipo) return;
    if (!nuevoNombre || nuevoNombre.trim() === '') {
      alert('El nombre del equipo no puede estar vacío.');
      this.editandoNombre = false;
      return;
    }
    this.equipoService.updateEquipo(this.equipo.id, { nombre: nuevoNombre }, undefined, undefined).subscribe({
      next: (updated) => {
        this.equipo = { ...this.equipo!, nombre: updated.nombre };
        this.editandoNombre = false;
      }
    });
  }

  guardarEscudo(file: File) {
    if (!this.equipo) return;
    this.equipoService.updateEquipo(this.equipo.id, { nombre: this.equipo.nombre }, file, undefined).subscribe({
      next: (updated) => {
        this.equipo = { ...this.equipo!, fotoEscudo: updated.fotoEscudo };
        this.loadImages();
        this.editandoEscudo = false;
      }
    });
  }

  guardarFondo(file: File) {
    if (!this.equipo) return;
    this.equipoService.updateEquipo(this.equipo.id, { nombre: this.equipo.nombre }, undefined, file).subscribe({
      next: (updated) => {
        this.equipo = { ...this.equipo!, fotoFondo: updated.fotoFondo };
        this.loadImages();
        this.editandoFondo = false;
      }
    });
  }

  @HostListener('document:keydown.escape', ['$event'])
  onKeydownEscape(event: KeyboardEvent) {
    if (this.modoEdicion) {
      this.cancelarModoEdicion();
    }
  }
}
