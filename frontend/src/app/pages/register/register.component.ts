import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EquipoService, Equipo } from '../../services/equipo.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  correo = '';
  username = '';
  password = '';
  verifyPassword = '';
  nombre = '';
  apellidos = '';
  errorMsg = '';
  successMsg = '';
  loading = false;
  equipos: Equipo[] = [];
  equipoFavoritoId: string | null = null;
  errorList: string[] = [];

  constructor(private auth: AuthService, private router: Router, private equipoService: EquipoService) {}

  ngOnInit() {
    this.equipoService.getEquipos().subscribe(equipos => {
      this.equipos = equipos;
    });
  }

  async onSubmit() {
    this.errorList = [];
    this.errorMsg = '';
    this.successMsg = '';
    let errors: string[] = [];
    if (this.password !== this.verifyPassword) {
      errors.push('Las contraseñas no coinciden');
    }
    if (!this.nombre || this.nombre.trim() === '') {
      errors.push('El nombre es obligatorio');
    }
    this.loading = true;
    let usernameExists = false;
    let emailExists = false;
    try {
      const [usernameExistsRaw, emailExistsRaw] = await Promise.all([
        this.auth.checkUsername(this.username).toPromise(),
        this.auth.checkEmail(this.correo).toPromise()
      ]);
      usernameExists = !!usernameExistsRaw;
      emailExists = !!emailExistsRaw;
    } catch (err) {
      this.loading = false;
      this.errorList = ['Error al comprobar usuario o correo'];
      return;
    }
    if (usernameExists) {
      errors.push('El nombre de usuario ya está en uso');
    }
    if (emailExists) {
      errors.push('El correo ya está en uso.');
    }
    if (errors.length > 0) {
      this.loading = false;
      this.errorList = errors;
      return;
    }
    this.errorList = [];
    const data = {
      correo: this.correo,
      username: this.username,
      password: this.password,
      verifyPassword: this.verifyPassword,
      nombre: this.nombre,
      apellidos: this.apellidos,
      equipoFavoritoId: this.equipoFavoritoId
    };
    this.auth.register(data).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/activate-account']);
      },
      error: (err) => {
        this.loading = false;
        this.errorList = [err.error?.message || 'Error en el registro'];
      }
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
