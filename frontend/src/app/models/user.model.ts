export interface User {
  id: string;
  nombre: string;
  apellidos: string;
  correo: string;
  equipoFavorito?: string;
  equipoFavoritoId?: string;
}

export interface EditUserInfoDto {
  nombre: string;
  apellidos: string;
  correo: string;
  equipoFavoritoId: string | null;
}

export interface EditUserPasswordDto {
  oldPassword: string;
  newPassword: string;
} 