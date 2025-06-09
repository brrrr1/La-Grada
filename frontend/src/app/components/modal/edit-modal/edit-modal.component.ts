import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-edit-modal',
  templateUrl: './edit-modal.component.html',
  styleUrls: ['./edit-modal.component.css']
})
export class EditModalComponent {
  @Input() titulo: string = '';
  @Input() confirmText: string = 'Guardar';
  @Input() cancelText: string = 'Cancelar';
  @Input() show: boolean = false;
  @Output() confirm = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();
} 