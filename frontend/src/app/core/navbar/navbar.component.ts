import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { trigger, transition, style, animate } from '@angular/animations';

const BASE_URL = 'http://localhost:8080';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  animations: [
    trigger('userMenuAnimation', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateX(20px)' }),
        animate('200ms cubic-bezier(0.4,0,0.2,1)', style({ opacity: 1, transform: 'translateX(0)' }))
      ]),
      transition(':leave', [
        animate('150ms cubic-bezier(0.4,0,0.2,1)', style({ opacity: 0, transform: 'translateX(20px)' }))
      ])
    ])
  ]
})
export class NavbarComponent implements OnInit {
  menuOpen = false;
  userName: string | null = null;
  userMenuOpen: boolean = false;
  isAdmin: boolean = false;

  showLogoutModal = false;

  constructor(public auth: AuthService, private router: Router, private http: HttpClient) {}

  ngOnInit() {
    this.loadUserName();
    this.checkIsAdmin();
    this.auth.userChanged$.subscribe(() => {
      this.loadUserName();
      this.checkIsAdmin();
    });
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  isLoggedIn(): boolean {
    return this.auth.isLoggedIn();
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

  logout() {
    this.showLogoutModal = true;
  }

  confirmLogout() {
    this.auth.logout().subscribe(() => {
      this.userName = null;
      this.router.navigate(['/main']);
      this.showLogoutModal = false;
    });
  }

  cancelLogout() {
    this.showLogoutModal = false;
  }

  loadUserName() {
    const token = this.auth.getToken();
    this.userMenuOpen = false;
    if (!token) {
      return;
    }
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    this.http.get<{ nombre: string }>(`${BASE_URL}/me`, { headers }).subscribe({
      next: (data) => {
        this.userName = data.nombre;
        this.userMenuOpen = false;
      },
      error: () => {
        this.userName = null;
        this.userMenuOpen = false;
      }
    });
  }

  toggleUserMenu() {
    this.userMenuOpen = !this.userMenuOpen;
  }

  closeUserMenu() {
    this.userMenuOpen = false;
  }

  goTo(route: string) {
    this.router.navigate([route]);
    this.closeUserMenu();
  }

  checkIsAdmin = async () => {
    try {
      const token = this.auth.getToken();
      if (!token) {
        this.isAdmin = false;
        return;
      }
      const headers = new HttpHeaders({
        Authorization: `Bearer ${token}`
      });
      const response = await this.http.get(`${BASE_URL}/me/admin`, { headers }).toPromise();
      this.isAdmin = true;
    } catch (error) {
      this.isAdmin = false;
    }
  };
}
