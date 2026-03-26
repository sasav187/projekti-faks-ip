import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../material.module';
import { RecommendationService } from '../../../core/services/recommendation.service';
import { RecommendationResponseDTO } from '../../models/recommendation-response.model';

@Component({
  standalone: true,
  selector: 'app-recommendations',
  imports: [
    CommonModule,
    MaterialModule
  ],
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.scss']
})
export class RecommendationsComponent implements OnInit {

  recommendations: RecommendationResponseDTO[] = [];
  loading = false;
  errorMessage = '';

  constructor(private recommendationService: RecommendationService) { }

  ngOnInit() {
    this.loadRecommendations();
  }

  loadRecommendations() {
    this.loading = true;
    this.recommendationService.getRecommendationsForStudent()
      .subscribe({
        next: data => {
          this.recommendations = data;
          this.loading = false;
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Failed to load recommendations';
          this.loading = false;
        }
      });
  }

  generateRecommendation(internshipId: number) {
    this.loading = true;
    this.recommendationService.generateRecommendation(internshipId)
      .subscribe({
        next: rec => {
          const index = this.recommendations.findIndex(r => r.internshipId === rec.internshipId);
          if (index >= 0) this.recommendations[index] = rec;
          else this.recommendations.unshift(rec);

          this.loading = false;
        },
        error: () => {
          this.errorMessage = 'Failed to generate recommendation';
          this.loading = false;
        }
      });
  }
}