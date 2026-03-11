import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { InternshipService } from '../../core/services/internship.service';
import { Internship } from '../../shared/models/internship.model';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  standalone: true,
  selector: 'app-internship-list',
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './internship-list.component.html',
  styleUrls: ['./internship-list.component.scss']
})
export class InternshipListComponent implements OnInit {

  internships$!: Observable<Internship[]>;
  loading = true;
  errorMessage = '';
  searchTitle = '';
  searchTechnology = '';

  constructor(private internshipService: InternshipService) { }

  ngOnInit(): void {
    this.loadInternships();
  }

  loadInternships(): void {
    this.loading = true;
    this.internships$ = this.internshipService.getAll().pipe(
      tap(() => this.loading = false),
      catchError(() => {
        this.errorMessage = 'Failed to load internships.';
        this.loading = false;
        return of([]);
      })
    );
  }

  onSearch(): void {
    this.loading = true;
    this.internships$ = this.internshipService.search(this.searchTitle, this.searchTechnology).pipe(
      tap(() => this.loading = false),
      catchError(() => {
        this.errorMessage = 'Search failed.';
        this.loading = false;
        return of([]);
      })
    );
  }

  clearSearch(): void {
    this.searchTitle = '';
    this.searchTechnology = '';
    this.loadInternships();
  }
}