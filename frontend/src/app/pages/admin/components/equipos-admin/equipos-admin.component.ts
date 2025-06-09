import { Component, OnInit } from '@angular/core';
import { EquipoService, Equipo, CreateEquipoDto } from '../../../../services/equipo.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-equipos-admin',
  templateUrl: './equipos-admin.component.html',
  styleUrls: ['./equipos-admin.component.css']
})
export class EquiposAdminComponent implements OnInit {
  equipos: Equipo[] = [];
  loading: boolean = true;
  error: string | null = null;

  deleteMode: boolean = false;
  editMode: boolean = false;
  equipoSeleccionado: Equipo | null = null;
  showNotification: boolean = false;
  notificationMessage: string = '';
  notificationType: 'error' | 'success' | 'info' = 'error';
  showEditModal: boolean = false;
  showCreateModal: boolean = false;

  equipoForm: FormGroup;
  escudoFile: File | null = null;
  fondoFile: File | null = null;

  constructor(
    private equipoService: EquipoService,
    private fb: FormBuilder
  ) {
    this.equipoForm = this.fb.group({
      nombre: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadEquipos();
  }

  loadEquipos(): void {
    this.loading = true;
    this.equipoService.getEquipos().subscribe({
      next: (data) => {
        this.equipos = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Error al cargar los equipos';
        this.loading = false;
        console.error('Error:', error);
      }
    });
  }

  onEliminarClick() {
    this.showDeleteError();
  }

  onEditarClick() {
    this.editMode = !this.editMode;
    if (!this.editMode) {
      this.equipoSeleccionado = null;
    }
  }

  onAddClick() {
    this.showCreateModal = true;
    this.equipoForm.reset();
    this.escudoFile = null;
    this.fondoFile = null;
  }

  seleccionarEquipo(equipo: Equipo) {
    if (this.deleteMode) {
      this.equipoSeleccionado = equipo;
      this.showDeleteError();
    } else if (this.editMode) {
      this.equipoSeleccionado = equipo;
      this.equipoForm.patchValue({
        nombre: equipo.nombre
      });
      this.showEditModal = true;
    }
  }

  isEquipoSeleccionado(equipo: Equipo): boolean {
    return this.equipoSeleccionado?.id === equipo.id;
  }

  showDeleteError() {
    this.notificationMessage = 'No se pueden eliminar los equipos';
    this.notificationType = 'error';
    this.showNotification = true;
    setTimeout(() => {
      this.showNotification = false;
    }, 3000);
  }

  onEscudoFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.escudoFile = input.files[0];
    }
  }

  onFondoFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.fondoFile = input.files[0];
    }
  }

  onEditModalConfirm() {
    if (this.equipoForm.valid && this.equipoSeleccionado) {
      const equipoData: CreateEquipoDto = this.equipoForm.value;
      
      this.equipoService.updateEquipo(
        this.equipoSeleccionado.id,
        equipoData,
        this.escudoFile || undefined,
        this.fondoFile || undefined
      ).subscribe({
        next: (updatedEquipo) => {
          const index = this.equipos.findIndex(e => e.id === updatedEquipo.id);
          if (index !== -1) {
            this.equipos[index] = updatedEquipo;
          }
          this.showEditModal = false;
          this.editMode = false;
          this.equipoSeleccionado = null;
          this.escudoFile = null;
          this.fondoFile = null;
          this.showNotification = true;
          this.notificationMessage = 'Equipo actualizado correctamente';
          this.notificationType = 'success';
          setTimeout(() => {
            this.showNotification = false;
          }, 3000);
        },
        error: (error) => {
          this.error = 'Error al actualizar el equipo';
          console.error('Error:', error);
          this.showNotification = true;
          this.notificationMessage = 'Error al actualizar el equipo';
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
    this.equipoSeleccionado = null;
    this.escudoFile = null;
    this.fondoFile = null;
    this.equipoForm.reset();
  }

  onCreateModalConfirm() {
    if (this.equipoForm.valid && this.escudoFile && this.fondoFile) {
      const equipoData: CreateEquipoDto = this.equipoForm.value;
      
      this.equipoService.createEquipo(
        equipoData,
        this.escudoFile,
        this.fondoFile
      ).subscribe({
        next: (newEquipo) => {
          this.equipos.push(newEquipo);
          this.showCreateModal = false;
          this.escudoFile = null;
          this.fondoFile = null;
          this.equipoForm.reset();
          this.showNotification = true;
          this.notificationMessage = 'Equipo creado correctamente';
          this.notificationType = 'success';
          setTimeout(() => {
            this.showNotification = false;
          }, 3000);
        },
        error: (error) => {
          this.error = 'Error al crear el equipo';
          console.error('Error:', error);
          this.showNotification = true;
          this.notificationMessage = 'Error al crear el equipo';
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
    this.escudoFile = null;
    this.fondoFile = null;
    this.equipoForm.reset();
  }
} 