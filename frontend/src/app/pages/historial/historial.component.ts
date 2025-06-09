import { Component, OnInit } from '@angular/core';
import { EventoService, GetEntradaDto } from '../../services/evento.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { EquipoService } from '../../services/equipo.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-historial',
  templateUrl: './historial.component.html',
  styleUrls: ['./historial.component.css']
})
export class HistorialComponent implements OnInit {
  entradas: GetEntradaDto[] = [];
  loading = true;
  errorMsg = '';

  constructor(
    private eventoService: EventoService,
    private sanitizer: DomSanitizer,
    private equipoService: EquipoService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.eventoService.getEntradasPasadasUsuario().subscribe({
      next: (data) => {
        this.entradas = data;
        this.loadEscudos();
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'Error al cargar tu historial de entradas';
        this.loading = false;
      }
    });
  }

  loadEscudos() {
    const token = this.auth.getToken() || undefined;
    this.entradas.forEach((entrada, idx) => {
      this.equipoService.downloadImage(entrada.evento.equipo1.fotoEscudo, token).subscribe({
        next: (blob) => {
          this.entradas[idx].evento.equipo1.escudoUrl = URL.createObjectURL(blob);
        },
        error: () => {
          this.entradas[idx].evento.equipo1.escudoUrl = undefined;
        }
      });
      this.equipoService.downloadImage(entrada.evento.equipo2.fotoEscudo, token).subscribe({
        next: (blob) => {
          this.entradas[idx].evento.equipo2.escudoUrl = URL.createObjectURL(blob);
        },
        error: () => {
          this.entradas[idx].evento.equipo2.escudoUrl = undefined;
        }
      });
      this.equipoService.downloadImage(entrada.evento.equipo1.fotoFondo, token).subscribe({
        next: (blob) => {
          this.entradas[idx].evento.equipo1.fondoUrl = URL.createObjectURL(blob);
        },
        error: () => {
          this.entradas[idx].evento.equipo1.fondoUrl = undefined;
        }
      });
      this.equipoService.downloadImage(entrada.evento.equipo2.fotoFondo, token).subscribe({
        next: (blob) => {
          this.entradas[idx].evento.equipo2.fondoUrl = URL.createObjectURL(blob);
        },
        error: () => {
          this.entradas[idx].evento.equipo2.fondoUrl = undefined;
        }
      });
    });
  }

  getSafeQrUrl(base64: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + base64);
  }
} 