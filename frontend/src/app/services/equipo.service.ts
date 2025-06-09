import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = 'http://localhost:8080';

export interface Equipo {
  id: string;
  nombre: string;
  fotoEscudo: string;
  fotoFondo: string;
}

export interface CreateEquipoDto {
  nombre: string;
}

@Injectable({
  providedIn: 'root'
})
export class EquipoService {
  constructor(private http: HttpClient) {}

  getEquipos(): Observable<Equipo[]> {
    return this.http.get<Equipo[]>(`${BASE_URL}/equipo/`);
  }

  downloadImage(filename: string, token?: string): Observable<Blob> {
    let options: any = { responseType: 'blob' as const };
    if (token) {
      options.headers = new HttpHeaders({
        Authorization: `Bearer ${token}`
      });
    }
    return this.http.get(`${BASE_URL}/download/${filename}`, options) as unknown as Observable<Blob>;
  }

  getEquipoById(id: string): Observable<Equipo> {
    return this.http.get<Equipo>(`${BASE_URL}/equipo/${id}`);
  }

  updateEquipo(id: string, equipo: CreateEquipoDto, escudoFile?: File, fondoFile?: File): Observable<Equipo> {
    const formData = new FormData();
    formData.append('equipo', new Blob([JSON.stringify(equipo)], { type: 'application/json' }));
    
    if (escudoFile) {
      formData.append('file', escudoFile);
    }
    if (fondoFile) {
      formData.append('file2', fondoFile);
    }

    return this.http.put<Equipo>(`${BASE_URL}/equipo/${id}`, formData);
  }

  createEquipo(equipo: CreateEquipoDto, escudoFile: File, fondoFile: File): Observable<Equipo> {
    const formData = new FormData();
    formData.append('equipo', new Blob([JSON.stringify(equipo)], { type: 'application/json' }));
    formData.append('file', escudoFile);
    formData.append('file2', fondoFile);

    return this.http.post<Equipo>(`${BASE_URL}/equipo/`, formData);
  }
}
