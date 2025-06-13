import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { User } from 'firebase/auth';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  user: User | null = null;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authService.getUser().subscribe(u => {
      this.user = u;
    });
  }

  logout(): void {
    this.authService.logout().then(() => {
      this.router.navigate(['/login']);
    });
  }
  get isLoggedIn(): boolean {
    return this.user !== null;
  }
  getUser(): string {
    return this.user ? this.user.email : '';
  }

}
