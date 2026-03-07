import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-unauthorized',
  templateUrl: './unauthorized.component.html',
  styleUrls: ['./unauthorized.component.css'],
  standalone: true
})
export class UnauthorizedComponent {

  constructor(private router: Router, private authService: AuthService) {}

  goToLogin() {
    this.authService.logout();   
    this.router.navigate(['/login']);
  }
}