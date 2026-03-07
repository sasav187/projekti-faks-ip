import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material.module';

@Component({
  selector: 'app-main-view',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.scss']
})
export class MainViewComponent {

  constructor(private router: Router) {}

  goToCV() {
    this.router.navigate(['/cv']);
  }

  goToInternships() {
    this.router.navigate(['/internships']);
  }

  goToRecommendations() {
    this.router.navigate(['/recommendations']);
  }

  goToWorklog() {
    this.router.navigate(['/worklog']);
  }

}