import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InternshipService } from '../../core/services/internship.service';
import { Internship } from '../../shared/models/internship.model';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  standalone: true,
  selector: 'app-internship-list',
  imports: [CommonModule],
  templateUrl: './internship-list.component.html',
  styleUrls: ['./internship-list.component.css']
})
export class InternshipListComponent implements OnInit {

  internships$!: Observable<Internship[]>;
  loading = true;
  errorMessage = '';

  constructor(private internshipService: InternshipService) {}

  ngOnInit(): void {
    this.internships$ = this.internshipService.getAll().pipe(
      tap(() => this.loading = false),
      catchError(err => {
        console.error('Error loading internships:', err);
        this.errorMessage = 'Failed to load internships.';
        this.loading = false;
        return of([]);
      })
    );
  }
}