import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.css']
})
export class ActivateAccountComponent {
  token = '';
  errorMsg = '';
  successMsg = '';
  loading = false;

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit() {
    this.errorMsg = '';
    this.successMsg = '';
    this.loading = true;
    this.auth.activateAccount(this.token).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = err.error?.message || 'Error al activar la cuenta';
      }
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
