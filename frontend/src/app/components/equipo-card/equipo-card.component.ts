import { Component, Input } from '@angular/core';
import { Equipo } from '../../services/equipo.service';
import { Router } from '@angular/router';

export interface EquipoCard extends Equipo {
  escudoUrl?: string;
  fondoUrl?: string;
}

@Component({
  selector: 'app-equipo-card',
  templateUrl: './equipo-card.component.html',
  styleUrls: ['./equipo-card.component.css']
})
export class EquipoCardComponent {
  @Input() equipo!: EquipoCard;

  constructor(private router: Router) {}

  onCardClick() {
    this.router.navigate(['/equipos', this.equipo.id]);
  }
}
