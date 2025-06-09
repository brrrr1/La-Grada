import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './pages/main/main.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ActivateAccountComponent } from './pages/activate-account/activate-account.component';
import { EquiposComponent } from './pages/equipos/equipos.component';
import { EquipoDetalleComponent } from './pages/equipos/equipo-detalle/equipo-detalle.component';
import { EventoDetalleComponent } from './pages/evento-detalle/evento-detalle/evento-detalle.component';
import { EventosListComponent } from './pages/eventos-list/eventos-list.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { EntradasListComponent } from './pages/entradas/entradas-list/entradas-list.component';
import { HistorialComponent } from './pages/historial/historial.component';
import { AboutComponent } from './components/about/about.component';
import { AdminComponent } from './pages/admin/admin.component';

const routes: Routes = [
  { path: '', redirectTo: 'main', pathMatch: 'full' },
  { path: 'main', component: MainComponent },
  { path: 'equipos', component: EquiposComponent },
  { path: 'equipos/:id', component: EquipoDetalleComponent },
  { path: 'eventos', component: EventosListComponent },
  { path: 'eventos/:id', component: EventoDetalleComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'activate-account', component: ActivateAccountComponent },
  { path: 'perfil', component: ProfileComponent },
  { path: 'entradas', component: EntradasListComponent },
  { path: 'historial', component: HistorialComponent },
  { path: 'sobre-nosotros', component: AboutComponent },
  { path: 'admin', component: AdminComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
