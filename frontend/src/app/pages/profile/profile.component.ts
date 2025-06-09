import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { EquipoService } from '../../services/equipo.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { User, EditUserInfoDto, EditUserPasswordDto } from '../../models/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User | null = null;
  equipos: any[] = [];
  editMode = false;
  passwordMode = false;
  profileForm: FormGroup;
  passwordForm: FormGroup;
  passwordChangeSuccess: boolean | null = null;
  passwordChangeError: string | null = null;

  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private equipoService: EquipoService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.profileForm = this.fb.group({
      nombre: [''],
      apellidos: [''],
      correo: [''],
      equipoFavoritoId: [null]
    });
    this.passwordForm = this.fb.group({
      oldPassword: [''],
      newPassword: ['']
    });
  }

  ngOnInit() {
    this.loadUser();
    this.loadEquipos();
  }

  loadUser() {
    const token = this.auth.getToken();
    if (!token) return;
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    this.http.get<User>('http://localhost:8080/me', { headers }).subscribe(user => {
      this.user = user;
      let equipoFavoritoId = user.equipoFavoritoId || null;
      if (!equipoFavoritoId && user.equipoFavorito && this.equipos.length > 0) {
        const found = this.equipos.find(e => e.nombre === user.equipoFavorito);
        if (found) equipoFavoritoId = found.id;
      }
      this.profileForm.patchValue({
        nombre: user.nombre,
        apellidos: user.apellidos,
        correo: user.correo,
        equipoFavoritoId: equipoFavoritoId
      });
    });
  }

  loadEquipos() {
    this.equipoService.getEquipos().subscribe(equipos => {
      this.equipos = equipos;
      if (this.user) {
        let equipoFavoritoId = (this.user && this.user.equipoFavoritoId) || null;
        if (!equipoFavoritoId && this.user && this.user.equipoFavorito) {
          const found = equipos.find(e => e.nombre === this.user!.equipoFavorito);
          if (found) equipoFavoritoId = found.id;
        }
        this.profileForm.patchValue({ equipoFavoritoId });
      }
    });
  }

  enableEdit() {
    this.editMode = true;
    this.passwordMode = false;
  }

  enablePasswordMode() {
    this.passwordMode = true;
    this.editMode = false;
    this.passwordChangeSuccess = null;
    this.passwordChangeError = null;
    this.passwordForm.reset();
  }

  cancelEdit() {
    this.editMode = false;
    this.passwordMode = false;
    this.loadUser();
  }

  cancelPasswordMode() {
    this.passwordMode = false;
    this.passwordChangeSuccess = null;
    this.passwordChangeError = null;
    this.passwordForm.reset();
  }

  save() {
    const token = this.auth.getToken();
    if (!token) return;
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    const data: EditUserInfoDto = this.profileForm.value;
    this.http.put<User>('http://localhost:8080/user/edit-info', data, { headers }).subscribe({
      next: (user) => {
        this.user = user;
        this.editMode = false;
        this.loadUser();
        this.auth.notifyUserChanged();
      }
    });
  }

  getInitials(): string {
    if (!this.user) return '';
    const nombre = this.user.nombre || '';
    const apellidos = this.user.apellidos || '';
    const n = nombre.trim().split(' ')[0];
    const a = apellidos.trim().split(' ')[0];
    return (n.charAt(0) + (a.charAt(0) || '')).toUpperCase();
  }

  changePassword() {
    const data: EditUserPasswordDto = {
      oldPassword: this.passwordForm.value.oldPassword,
      newPassword: this.passwordForm.value.newPassword
    };
    this.auth.changePassword(data).subscribe({
      next: () => {
        this.passwordChangeSuccess = true;
        this.passwordChangeError = null;
        this.auth.logout().subscribe(() => {
          this.router.navigate(['/login']);
        });
      },
      error: (err) => {
        this.passwordChangeSuccess = false;
        this.passwordChangeError = err.error?.message || 'Error al cambiar la contraseña.';
      }
    });
  }
} 