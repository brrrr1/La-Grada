import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { EquipoService, Equipo } from '../../services/equipo.service';
import { Router } from '@angular/router';

interface EquipoCard extends Equipo {
  escudoUrl?: string;
  fondoUrl?: string;
}

@Component({
  selector: 'app-equipos',
  templateUrl: './equipos.component.html',
  styleUrls: ['./equipos.component.css']
})
export class EquiposComponent implements OnInit {
  equipos: EquipoCard[] = [];
  loading = true;
  errorMsg = '';

  constructor(private equipoService: EquipoService, private auth: AuthService, private router: Router) {}

  ngOnInit() {
    this.equipoService.getEquipos().subscribe({
      next: (data) => {
        this.equipos = data;
        this.loadImages();
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'Error al cargar los equipos';
        this.loading = false;
      }
    });
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

  onEquipoClick(equipo: EquipoCard) {
    this.router.navigate(['/equipos', equipo.id]);
  }
}
