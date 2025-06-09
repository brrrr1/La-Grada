import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent {
  @Input() message: string = '';
  @Input() type: 'success' | 'error' | 'info' = 'info';
  @Input() duration: number = 10000; 
  @Input() show: boolean = false;
  @Input() actionButton?: { text: string; route: string };
  @Output() closed = new EventEmitter<void>();

  constructor(private router: Router) {}

  ngOnChanges() {
    if (this.show && this.duration > 0) {
      setTimeout(() => {
        this.close();
      }, this.duration);
    }
  }

  close() {
    this.closed.emit();
  }

  handleAction() {
    if (this.actionButton?.route) {
      this.router.navigate([this.actionButton.route]);
      this.close();
    }
  }
} 