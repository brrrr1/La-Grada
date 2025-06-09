import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UserListResponse {
  id: string;
  username: string;
  nombre: string;
  apellidos: string;
  correo: string;
  equipoFavorito: string;
  enabled: boolean;
}

export interface DisableUserRequest {
  userId: string;
}

export interface EnableUserRequest {
  userId: string;
}

const BASE_URL = 'http://localhost:8080';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<UserListResponse[]> {
    return this.http.get<UserListResponse[]>(`${BASE_URL}/admin/users`);
  }

  disableUser(userId: string): Observable<UserListResponse> {
    const body: DisableUserRequest = { userId };
    return this.http.post<UserListResponse>(`${BASE_URL}/admin/users/disable`, body);
  }

  enableUser(userId: string): Observable<UserListResponse> {
    const body: EnableUserRequest = { userId };
    return this.http.post<UserListResponse>(`${BASE_URL}/admin/users/enable`, body);
  }
} 