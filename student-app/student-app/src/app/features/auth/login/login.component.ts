import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        const token = response.token || response.accessToken || response.jwt;
        if (token) {
          localStorage.setItem('token', token);
          this.router.navigate(['/internships']);
        } else {
          this.errorMessage = 'No token returned from server';
        }
      },
      error: () => {
        this.errorMessage = 'Invalid username or password';
      }
    });
  }
}