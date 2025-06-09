import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalComponent } from './modal.component';
import { EditModalComponent } from '../edit-modal/edit-modal.component';

@NgModule({
  declarations: [ModalComponent, EditModalComponent],
  imports: [CommonModule],
  exports: [ModalComponent, EditModalComponent]
})
export class SharedModule {} 