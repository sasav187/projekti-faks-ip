import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../material.module';
import { filter } from 'rxjs/operators';
import { RecommendationService } from '../../../core/services/recommendation.service';
import { RecommendationResponseDTO } from '../../models/recommendation-response.model';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Component({
  standalone: true,
  selector: 'app-recommendations',
  imports: [CommonModule, MaterialModule, RouterModule],
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.scss']
})
export class RecommendationsComponent implements OnInit {
  recommendations$!: Observable<RecommendationResponseDTO[]>;
  loading = true;
  errorMessage = '';

  constructor(
    private recommendationService: RecommendationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadRecommendations();
    this.router.events.pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => this.loadRecommendations());
  }

  loadRecommendations(): void {
    this.loading = true;
    this.errorMessage = '';
    this.recommendations$ = this.recommendationService.getRecommendationsForStudent().pipe(
      tap(() => this.loading = false),
      catchError(() => {
        this.errorMessage = 'Failed to load recommendations';
        this.loading = false;
        return of([]);
      })
    );
  }
}