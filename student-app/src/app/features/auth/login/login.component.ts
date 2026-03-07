import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../material.module';

import { AuthService } from '../../../core/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) { }

  login() {
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        const role = this.authService.getRole();

        if (role === 'STUDENT') {
          this.router.navigate(['']);
        } else {
          this.router.navigate(['/unauthorized']);
        }
      },

      error: (err) => {

        if (err.status === 403) {
          this.authService.logout();
          this.router.navigate(['/unauthorized']);
        }

        else if (err.status === 401) {
          this.errorMessage = 'Invalid username or password';
        }

        else {
          this.errorMessage = 'Login failed';
        }
      }
    });
  }
}