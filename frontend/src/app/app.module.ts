import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import { AuthInterceptor } from './core/auth.interceptor';
import { AuthExpiredInterceptor } from './core/auth-expired.interceptor';
import { MainComponent } from './pages/main/main.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ActivateAccountComponent } from './pages/activate-account/activate-account.component';
import { NavbarComponent } from './core/navbar/navbar.component';
import { EquiposComponent } from './pages/equipos/equipos.component';
import { EquipoCardComponent } from './components/equipo-card/equipo-card.component';
import { EquiposListComponent } from './components/equipos-list/equipos-list.component';
import { EventoCardComponent } from './components/evento-card/evento-card.component';
import { EventosListComponent } from './pages/eventos-list/eventos-list.component';
import { EquipoDetalleComponent } from './pages/equipos/equipo-detalle/equipo-detalle.component';
import { EventoDetalleComponent } from './pages/evento-detalle/evento-detalle/evento-detalle.component';
import { EntradasListComponent } from './pages/entradas/entradas-list/entradas-list.component';
import { HistorialComponent } from './pages/historial/historial.component';
import { EntradaCardComponent } from './components/entrada-card/entrada-card.component';
import { AboutComponent } from './components/about/about.component';
import { AdminModule } from './pages/admin/admin.module';
import { SharedModule } from './components/modal/modal/shared.module';
import { ProfileModule } from './pages/profile/profile.module';
import { NotificationModule } from './components/notification/notification.module';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LoginComponent,
    RegisterComponent,
    ActivateAccountComponent,
    NavbarComponent,
    EquiposComponent,
    EquipoCardComponent,
    EquiposListComponent,
    EventoCardComponent,
    EventosListComponent,
    EquipoDetalleComponent,
    EventoDetalleComponent,
    EntradasListComponent,
    HistorialComponent,
    EntradaCardComponent,
    AboutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ProfileModule,
    CommonModule,
    AdminModule,
    SharedModule,
    NotificationModule
  ],
  providers: [
    provideAnimationsAsync(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
