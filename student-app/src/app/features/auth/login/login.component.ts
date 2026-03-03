import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';

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

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        const role = this.authService.getRole();
        if (role === 'STUDENT') this.router.navigate(['/internships']);
        else if (role === 'COMPANY') this.router.navigate(['/company/dashboard']);
        else if (role === 'FACULTY') this.router.navigate(['/faculty/dashboard']);
        else this.router.navigate(['/unauthorized']);
      },
      error: () => {
        this.errorMessage = 'Invalid username or password';
      }
    });
  }
}