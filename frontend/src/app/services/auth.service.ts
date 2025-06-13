import { Injectable } from '@angular/core';
import { Auth, signInWithEmailAndPassword, signOut, User, onAuthStateChanged } from '@angular/fire/auth';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor(private auth: Auth, private router: Router) {
    onAuthStateChanged(this.auth, user => {
      this.currentUserSubject.next(user);
    });
  }

  login(email: string, password: string): Promise<void> {
    return signInWithEmailAndPassword(this.auth, email, password)
      .then(cred => {
        this.currentUserSubject.next(cred.user);
      });
  }

  logout(): Promise<void> {
    return signOut(this.auth).then(() => {
      this.currentUserSubject.next(null);
      this.router.navigate(['/login']);
    });
  }

  getToken(): Promise<string | undefined> {
    return this.auth.currentUser?.getIdToken() ?? Promise.resolve('');
  }

  getUser(): Observable<User | null> {
    return this.currentUser$;
  }

  isAuthenticated(): boolean {
    return !!this.auth.currentUser;
  }
}
