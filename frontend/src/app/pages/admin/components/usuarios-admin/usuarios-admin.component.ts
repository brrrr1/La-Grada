import { Component, OnInit } from '@angular/core';
import { UserService, UserListResponse } from '../../../../services/user.service';

@Component({
  selector: 'app-usuarios-admin',
  templateUrl: './usuarios-admin.component.html',
  styleUrl: './usuarios-admin.component.css'
})
export class UsuariosAdminComponent implements OnInit {
  usuarios: UserListResponse[] = [];
  loading = true;
  error: string | null = null;

  disableMode: boolean = false;
  usuarioSeleccionado: UserListResponse | null = null;
  showDisableModal: boolean = false;
  notificationMessage: string = '';
  showNotification: boolean = false;
  notificationType: 'success' | 'error' = 'success';

  enableMode: boolean = false;
  showEnableModal: boolean = false;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsuarios();
  }

  loadUsuarios() {
    this.loading = true;
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar los usuarios';
        this.loading = false;
      }
    });
  }

  onDisableClick() {
    if (this.disableMode) {
      this.disableMode = false;
      this.usuarioSeleccionado = null;
    } else {
      this.disableMode = true;
      this.enableMode = false;
      this.usuarioSeleccionado = null;
    }
  }

  onEnableClick() {
    if (this.enableMode) {
      this.enableMode = false;
      this.usuarioSeleccionado = null;
    } else {
      this.enableMode = true;
      this.disableMode = false;
      this.usuarioSeleccionado = null;
    }
  }

  seleccionarUsuario(usuario: UserListResponse) {
    if (this.disableMode && usuario.enabled) {
      this.usuarioSeleccionado = usuario;
      this.showDisableModal = true;
    } else if (this.enableMode && !usuario.enabled) {
      this.usuarioSeleccionado = usuario;
      this.showEnableModal = true;
    }
  }

  isUsuarioSeleccionado(usuario: UserListResponse): boolean {
    return this.usuarioSeleccionado?.id === usuario.id;
  }

  onDisableModalConfirm() {
    if (!this.usuarioSeleccionado) return;
    this.userService.disableUser(this.usuarioSeleccionado.id).subscribe({
      next: (updatedUser) => {
        const idx = this.usuarios.findIndex(u => u.id === updatedUser.id);
        if (idx !== -1) {
          this.usuarios[idx] = updatedUser;
        }
        this.showDisableModal = false;
        this.disableMode = false;
        this.usuarioSeleccionado = null;
        this.showNotification = true;
        this.notificationMessage = 'Usuario deshabilitado correctamente';
        this.notificationType = 'success';
        setTimeout(() => this.showNotification = false, 3000);
      },
      error: () => {
        this.showDisableModal = false;
        this.disableMode = false;
        this.usuarioSeleccionado = null;
        this.showNotification = true;
        this.notificationMessage = 'Error al deshabilitar el usuario';
        this.notificationType = 'error';
        setTimeout(() => this.showNotification = false, 3000);
      }
    });
  }

  onDisableModalCancel() {
    this.showDisableModal = false;
    this.usuarioSeleccionado = null;
  }

  onEnableModalConfirm() {
    if (!this.usuarioSeleccionado) return;
    this.userService.enableUser(this.usuarioSeleccionado.id).subscribe({
      next: (updatedUser) => {
        const idx = this.usuarios.findIndex(u => u.id === updatedUser.id);
        if (idx !== -1) {
          this.usuarios[idx] = updatedUser;
        }
        this.showEnableModal = false;
        this.enableMode = false;
        this.usuarioSeleccionado = null;
        this.showNotification = true;
        this.notificationMessage = 'Usuario habilitado correctamente';
        this.notificationType = 'success';
        setTimeout(() => this.showNotification = false, 3000);
      },
      error: () => {
        this.showEnableModal = false;
        this.enableMode = false;
        this.usuarioSeleccionado = null;
        this.showNotification = true;
        this.notificationMessage = 'Error al habilitar el usuario';
        this.notificationType = 'error';
        setTimeout(() => this.showNotification = false, 3000);
      }
    });
  }

  onEnableModalCancel() {
    this.showEnableModal = false;
    this.usuarioSeleccionado = null;
  }
}
