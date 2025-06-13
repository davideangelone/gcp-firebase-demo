import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { provideFirebaseApp, initializeApp } from '@angular/fire/app';
import { getAuth, provideAuth } from '@angular/fire/auth';
import { environment } from '../environments/environment';
import { LoginComponent } from './login/login.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './services/jwt.interceptor';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MessagesComponent } from './messages/messages.component';
import { HeaderComponent } from './header/header.component';

@NgModule({
  declarations: [AppComponent, HeaderComponent, LoginComponent, DashboardComponent, MessagesComponent],
  imports: [
    BrowserModule, 
    AppRoutingModule, 
    FormsModule, 
    HttpClientModule, 
    ReactiveFormsModule,
    provideFirebaseApp(() => initializeApp(environment.firebaseConfig)),
    provideAuth(() => getAuth())
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule {}
