import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable, from } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return from(this.authService.getToken() ?? Promise.resolve(undefined)).pipe(
      switchMap(token => {
        const cloned = token
          ? req.clone({
              setHeaders: {
                Authorization: `Bearer ${token}`
              }
            })
          : req;
        return next.handle(cloned); // assicurati che qui ci sia SEMPRE un return
      })
    );
  }
}
