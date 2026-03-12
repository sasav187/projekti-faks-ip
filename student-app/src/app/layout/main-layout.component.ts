import { Component, OnInit } from '@angular/core';
import { RouterOutlet, Router, RouterModule } from '@angular/router';
import { AuthService } from '../core/services/auth.service';
import { MaterialModule } from '../material.module';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterModule, 
    MaterialModule
  ],
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.scss']
})
export class MainLayoutComponent implements OnInit {

  username: string | null = '';
  warningShown = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {

    this.username = this.authService.getUserName();

    setInterval(() => {

      if (this.authService.isTokenExpiringSoon() && !this.warningShown) {

        this.snackBar.open(
          'Your session will expire soon.',
          'OK',
          { duration: 5000 }
        );

        this.warningShown = true;
      }

      if (this.authService.isTokenExpired()) {

        this.snackBar.open(
          'Session expired. Please login again.',
          'Login',
          { duration: 5000 }
        );

        this.logout();
      }

    }, 30_000);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}