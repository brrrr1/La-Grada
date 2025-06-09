import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap, Subject } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

const BASE_URL = 'http://localhost:8080';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${BASE_URL}/auth/login`, { username, password }).pipe(
      tap((res: any) => {
        this.setTokens(res.token, res.refreshToken);
      })
    );
  }

  register(data: any): Observable<any> {
    return this.http.post(`${BASE_URL}/auth/register`, data);
  }

  refreshToken(refreshToken: string): Observable<any> {
    return this.http.post(`${BASE_URL}/auth/refresh/token`, { refreshToken }).pipe(
      tap((res: any) => {
        this.setTokens(res.token, res.refreshToken);
      })
    );
  }

  logout(): Observable<any> {
    const token = this.getToken();
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.post(`${BASE_URL}/auth/logout`, {}, { headers }).pipe(
      tap(() => this.clearTokens())
    );
  }

  setTokens(token: string, refreshToken: string) {
    localStorage.setItem('token', token);
    localStorage.setItem('refreshToken', refreshToken);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRefreshToken(): string | null {
    return localStorage.getItem('refreshToken');
  }

  clearTokens() {
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  activateAccount(token: string): Observable<any> {
    return this.http.post(`${BASE_URL}/activate/account`, { token });
  }

  getUserId(): string | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      const decoded: any = jwtDecode(token);
      return decoded.sub || decoded.id || decoded.userId || null;
    } catch {
      return null;
    }
  }

  chooseFavoriteTeam(teamId: string) {
    const token = this.getToken();
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.post(`${BASE_URL}/user/choose-favorite-team/${teamId}`, {}, { headers });
  }

  changeFavoriteTeam(newTeamId: string) {
    const token = this.getToken();
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.post(`${BASE_URL}/user/change-favorite-team/${newTeamId}`, {}, { headers });
  }

  removeFavoriteTeam() {
    const token = this.getToken();
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.delete(`${BASE_URL}/user/remove-favorite-team`, { headers });
  }

  changePassword(data: import('../models/user.model').EditUserPasswordDto) {
    const token = this.getToken();
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.put(`${BASE_URL}/user/edit-password`, data, { headers });
  }

  checkUsername(username: string) {
    return this.http.get<boolean>(`${BASE_URL}/auth/check-username`, { params: { username } });
  }

  checkEmail(correo: string) {
    return this.http.get<boolean>(`${BASE_URL}/auth/check-email`, { params: { correo } });
  }

  userChanged$ = new Subject<void>();

  notifyUserChanged() {
    this.userChanged$.next();
  }

  isAdmin(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      const decoded: any = jwtDecode(token);
      const roles = decoded.roles || decoded.authorities || [];
      if (Array.isArray(roles)) {
        return roles.includes('ADMIN');
      } else if (typeof roles === 'string') {
        return roles === 'ADMIN';
      }
      return false;
    } catch {
      return false;
    }
  }
}
