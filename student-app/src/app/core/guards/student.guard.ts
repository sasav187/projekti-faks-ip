import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class StudentGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const role = this.authService.getRole(); // pretpostavimo da vraća 'STUDENT', 'FACULTY' ili 'COMPANY'
    
    if (role === 'STUDENT') {
      return true; // dozvoli pristup
    } else {
      // preusmeri na login ili unauthorized stranicu
      this.router.navigate(['/login']);
      return false;
    }
  }
}