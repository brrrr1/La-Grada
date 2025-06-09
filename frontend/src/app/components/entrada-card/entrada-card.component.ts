import { Component, Input } from '@angular/core';
import { GetEntradaDto } from '../../services/evento.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-entrada-card',
  templateUrl: './entrada-card.component.html',
  styleUrls: ['./entrada-card.component.css']
})
export class EntradaCardComponent {
  @Input() entrada!: GetEntradaDto;
  isModalOpen = false;

  constructor(private sanitizer: DomSanitizer) {}

  getSafeQrUrl(base64: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + base64);
  }

  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  downloadQr() {
    const link = document.createElement('a');
    link.href = 'data:image/png;base64,' + this.entrada.qrBase64;
    link.download = `qr-entrada-${this.entrada.id || ''}.png`;
    link.click();
  }
} 