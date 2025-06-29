import { Injectable } from '@angular/core';
import { Auth, signInWithEmailAndPassword, signOut } from '@angular/fire/auth';
import { Router } from '@angular/router';
import { authState } from '@angular/fire/auth';
import { Observable, firstValueFrom } from 'rxjs';
import { User } from 'firebase/auth';

@Injectable({ providedIn: 'root' })
export class AuthService {

  public currentUser$: Observable<User | null>;

  constructor(private auth: Auth, private router: Router) {
    this.currentUser$ = authState(this.auth);
  }

  login(email: string, password: string): Promise<void> {
    return new Promise((resolve, reject) => {
          signInWithEmailAndPassword(this.auth, email, password)
            .then(() => resolve())
            .catch(err => reject(err));
    });
  }

  logout(): Promise<void> {
    return signOut(this.auth).then(() => {
        this.router.navigate(['/login']);
    });
  }

  getToken(): Promise<string | undefined> {
    return this.auth.currentUser?.getIdToken() ?? Promise.resolve(undefined);
  }

  getUser(): Observable<User | null> {
    return this.currentUser$;
  }

  async isAuthenticated(): Promise<boolean> {
    const user = await firstValueFrom(this.currentUser$);
    return !!user;
  }
}