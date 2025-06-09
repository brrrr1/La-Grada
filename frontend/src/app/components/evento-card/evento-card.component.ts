import { Component, Input } from '@angular/core';
import { Evento } from '../../services/evento.service';
import { Router } from '@angular/router';

export interface EventoCard extends Evento {
  escudo1Url?: string;
  escudo2Url?: string;
  fondo1Url?: string;
  fondo2Url?: string;
}

@Component({
  selector: 'app-evento-card',
  templateUrl: './evento-card.component.html',
  styleUrls: ['./evento-card.component.css']
})
export class EventoCardComponent {
  @Input() evento!: EventoCard;

  constructor(private router: Router) {}

  irADetalle() {
    this.router.navigate(['/eventos', this.evento.id]);
  }
}
