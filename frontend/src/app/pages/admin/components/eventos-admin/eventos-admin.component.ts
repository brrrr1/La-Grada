import { Component, OnInit } from '@angular/core';
import { EventoService, Evento, CreateEventoDto } from '../../../../services/evento.service';
import { EquipoService } from '../../../../services/equipo.service';
import { Equipo } from '../../../../models/equipo.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-eventos-admin',
  templateUrl: './eventos-admin.component.html',
  styleUrls: ['./eventos-admin.component.css']
})
export class EventosAdminComponent implements OnInit {
  eventos: Evento[] = [];
  equipos: Equipo[] = [];
  loading: boolean = true;
  error: string | null = null;

  deleteMode: boolean = false;
  editMode: boolean = false;
  eventoSeleccionado: Evento | null = null;
  showDeleteModal: boolean = false;
  showEditModal: boolean = false;
  showCreateModal: boolean = false;
  showNotification: boolean = false;
  notificationMessage: string = '';
  notificationType: 'error' | 'success' | 'info' = 'error';

  eventoForm: FormGroup;

  constructor(
    private eventoService: EventoService,
    private equipoService: EquipoService,
    private fb: FormBuilder
  ) {
    this.eventoForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      fechaYHora: ['', Validators.required],
      equipo1Id: ['', Validators.required],
      equipo2Id: ['', Validators.required],
      entradasTotales: [0, [Validators.required, Validators.min(1)]],
      precio: [0, [Validators.required, Validators.min(0.01)]],
      tipo: ['', Validators.required]
    }, { validators: this.sameTeamValidator });
  }

  sameTeamValidator(group: FormGroup) {
    const equipo1Id = group.get('equipo1Id')?.value;
    const equipo2Id = group.get('equipo2Id')?.value;
    
    if (equipo1Id && equipo2Id && equipo1Id === equipo2Id) {
      return { sameTeam: true };
    }
    return null;
  }

  getErrorMessage(controlName: string): string {
    const control = this.eventoForm.get(controlName);
    if (!control) return '';

    if (control.hasError('required')) {
      return 'Este campo es obligatorio';
    }
    if (control.hasError('min')) {
      if (controlName === 'entradasTotales') {
        return 'Debe haber al menos 1 entrada';
      }
      if (controlName === 'precio') {
        return 'El precio debe ser mayor que 0';
      }
    }
    return '';
  }

  ngOnInit(): void {
    this.loadEventos();
    this.loadEquipos();
  }

  loadEventos(): void {
    this.loading = true;
    this.eventoService.getAllEventos().subscribe({
      next: (data) => {
        this.eventos = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Error al cargar los eventos';
        this.loading = false;
        console.error('Error:', error);
      }
    });
  }

  loadEquipos(): void {
    this.equipoService.getEquipos().subscribe({
      next: (data) => {
        this.equipos = data;
      },
      error: (error) => {
        console.error('Error al cargar equipos:', error);
      }
    });
  }

  onEliminarClick() {
    if (this.deleteMode) {
      this.deleteMode = false;
      this.eventoSeleccionado = null;
    } else {
      this.deleteMode = true;
      this.editMode = false;
      this.showCreateModal = false;
      this.eventoSeleccionado = null;
    }
  }

  onEditarClick() {
    if (this.editMode) {
      this.editMode = false;
      this.eventoSeleccionado = null;
    } else {
      this.editMode = true;
      this.deleteMode = false;
      this.showCreateModal = false;
      this.eventoSeleccionado = null;
    }
  }

  onAddClick() {
    this.showCreateModal = true;
    this.deleteMode = false;
    this.editMode = false;
    this.eventoSeleccionado = null;
    this.eventoForm.reset();
  }

  seleccionarEvento(evento: Evento) {
    if (this.deleteMode) {
      this.eventoSeleccionado = evento;
      this.showDeleteModal = true;
    } else if (this.editMode) {
      this.eventoSeleccionado = evento;
      this.eventoForm.patchValue({
        nombre: evento.nombre,
        descripcion: evento.descripcion,
        fechaYHora: evento.fechaYHora,
        equipo1Id: evento.equipo1?.id,
        equipo2Id: evento.equipo2?.id,
        entradasTotales: evento.entradasTotales,
        precio: evento.precio,
        tipo: evento.tipoEvento
      });
      this.showEditModal = true;
    }
  }

  isEventoSeleccionado(evento: Evento): boolean {
    return this.eventoSeleccionado?.id === evento.id;
  }

  onDeleteModalConfirm() {
    if (!this.eventoSeleccionado) return;
    this.eventoService.deleteEvento(this.eventoSeleccionado.id).subscribe({
      next: () => {
        this.eventos = this.eventos.filter(e => e.id !== this.eventoSeleccionado?.id);
        this.showDeleteModal = false;
        this.deleteMode = false;
        this.eventoSeleccionado = null;
        this.showNotification = true;
        this.notificationMessage = 'Evento eliminado correctamente';
        this.notificationType = 'success';
        setTimeout(() => {
          this.showNotification = false;
        }, 3000);
      },
      error: () => {
        this.error = 'No se pudo eliminar el evento.';
        this.showDeleteModal = false;
        this.deleteMode = false;
        this.eventoSeleccionado = null;
        this.showNotification = true;
        this.notificationMessage = 'Error al eliminar el evento';
        this.notificationType = 'error';
        setTimeout(() => {
          this.showNotification = false;
        }, 3000);
      }
    });
  }

  onDeleteModalCancel() {
    this.showDeleteModal = false;
    this.eventoSeleccionado = null;
  }

  onEditModalConfirm() {
    if (this.eventoForm.valid && this.eventoSeleccionado) {
      const eventoData = this.eventoForm.value;
      this.eventoService.updateEvento(this.eventoSeleccionado.id, eventoData).subscribe({
        next: (updatedEvento) => {
          const index = this.eventos.findIndex(e => e.id === updatedEvento.id);
          if (index !== -1) {
            this.eventos[index] = updatedEvento;
          }
          this.showEditModal = false;
          this.editMode = false;
          this.eventoSeleccionado = null;
          this.showNotification = true;
          this.notificationMessage = 'Evento actualizado correctamente';
          this.notificationType = 'success';
          setTimeout(() => {
            this.showNotification = false;
          }, 3000);
        },
        error: (error) => {
          this.error = 'No se pudo actualizar el evento.';
          console.error('Error:', error);
          this.showNotification = true;
          this.notificationMessage = 'Error al actualizar el evento';
          this.notificationType = 'error';
          setTimeout(() => {
            this.showNotification = false;
          }, 3000);
        }
      });
    }
  }

  onEditModalCancel() {
    this.showEditModal = false;
    this.eventoSeleccionado = null;
  }

  onCreateModalConfirm() {
    if (this.eventoForm.hasError('sameTeam')) {
      this.showNotification = true;
      this.notificationMessage = 'No se puede crear un evento con el mismo equipo en ambos lados';
      this.notificationType = 'error';
      setTimeout(() => {
        this.showNotification = false;
      }, 3000);
      return;
    }

    const entradasControl = this.eventoForm.get('entradasTotales');
    const precioControl = this.eventoForm.get('precio');

    if (entradasControl?.hasError('min')) {
      this.showNotification = true;
      this.notificationMessage = 'El número de entradas debe ser mayor que 0';
      this.notificationType = 'error';
      setTimeout(() => {
        this.showNotification = false;
      }, 3000);
      return;
    }

    if (precioControl?.hasError('min')) {
      this.showNotification = true;
      this.notificationMessage = 'El precio debe ser mayor que 0';
      this.notificationType = 'error';
      setTimeout(() => {
        this.showNotification = false;
      }, 3000);
      return;
    }

    if (this.eventoForm.valid) {
      const eventoData: CreateEventoDto = this.eventoForm.value;
      this.eventoService.createEvento(eventoData).subscribe({
        next: (newEvento) => {
          this.eventos.push(newEvento);
          this.showCreateModal = false;
          this.eventoForm.reset();
          this.showNotification = true;
          this.notificationMessage = 'Evento creado correctamente';
          this.notificationType = 'success';
          setTimeout(() => {
            this.showNotification = false;
          }, 3000);
        },
        error: (error) => {
          this.error = 'No se pudo crear el evento.';
          console.error('Error:', error);
          this.showNotification = true;
          this.notificationMessage = 'Error al crear el evento';
          this.notificationType = 'error';
          setTimeout(() => {
            this.showNotification = false;
          }, 3000);
        }
      });
    } else {
      this.showNotification = true;
      this.notificationMessage = 'Por favor, completa todos los campos obligatorios';
      this.notificationType = 'error';
      setTimeout(() => {
        this.showNotification = false;
      }, 3000);
    }
  }

  onCreateModalCancel() {
    this.showCreateModal = false;
    this.eventoForm.reset();
  }
} 