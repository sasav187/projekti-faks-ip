import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../material.module'
import { ScrollingModule } from '@angular/cdk/scrolling';

import { InternshipService } from '../../../core/services/internship.service';
import { Internship } from '../../models/internship.model';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Component({
  standalone: true,
  selector: 'app-internship-list',
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule,
    ScrollingModule
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
    this.errorMessage = '';

    this.internships$ = this.internshipService
      .search(this.searchTitle, this.searchTechnology)
      .pipe(
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

  apply(internshipId: number) {

    const studentId = Number(localStorage.getItem('studentId'));

    this.internshipService
      .apply(internshipId, studentId)
      .subscribe({
        next: () => {
          alert("Application sent successfully");
        },
        error: () => {
          alert("Application failed");
        }
      });

  }

  displayedColumns: string[] = [
    'title',
    'company',
    'technologies',
    'period',
    'capacity',
    'actions'
  ];
}