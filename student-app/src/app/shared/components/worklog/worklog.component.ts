import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../material.module';
import { WorkLogService } from '../../../core/services/worklog.service';
import { WorkLog } from '../../models/worklog.model';
import { Observable, of, BehaviorSubject, switchMap } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Component({
  standalone: true,
  selector: 'app-worklog',
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './worklog.component.html',
  styleUrls: ['./worklog.component.scss'],
})
export class WorklogComponent implements OnInit {

  applications: any[] = [];
  applicationId$ = new BehaviorSubject<number | null>(null);
  logs$!: Observable<WorkLog[]>;
  loading = true;
  errorMessage = '';
  weekNumber = 1;
  description = '';
  searchDescription = '';

  constructor(private workLogService: WorkLogService) { }

  ngOnInit(): void {
    this.loadApplications();
    this.logs$ = this.applicationId$.pipe(
      switchMap(appId => {
        if (!appId) return of([]);
        this.loading = true;
        return this.workLogService.getByApplication(appId).pipe(
          tap(() => this.loading = false),
          catchError(() => {
            this.errorMessage = 'Failed to load logs';
            this.loading = false;
            return of({ content: [] });
          }),
          switchMap(res => of(res.content))
        );
      })
    );
  }

  loadApplications(): void {
    this.loading = true;
    this.workLogService.getApplicationsByStudent().pipe(
      catchError(() => {
        this.errorMessage = 'Failed to load applications';
        return of([]);
      })
    ).subscribe(res => {
      this.applications = res;
      this.loading = false;
      if (this.applications.length > 0) {
        this.applicationId$.next(this.applications[0].id);
      }
    });
  }

  save(): void {
    const appId = this.applicationId$.value;
    if (!appId) return;
    const log: WorkLog = { applicationId: appId, weekNumber: this.weekNumber, description: this.description };
    this.workLogService.create(log).subscribe(() => {
      this.description = '';
      this.applicationId$.next(appId);
    });
  }

  delete(id: number): void {
    this.workLogService.delete(id).subscribe(() => {
      const appId = this.applicationId$.value;
      if (appId) this.applicationId$.next(appId);
    });
  }

  onSearch(): void {
    const appId = this.applicationId$.value;
    if (!appId) return;
    this.loading = true;
    this.workLogService.search(this.searchDescription).pipe(
      catchError(() => {
        this.errorMessage = 'Search failed';
        this.loading = false;
        return of({ content: [] });
      })
    ).subscribe(res => {
      this.logs$ = of(res.content);
      this.loading = false;
    });
  }

  clearSearch(): void {
    this.searchDescription = '';
    const appId = this.applicationId$.value;
    if (appId) this.applicationId$.next(appId);
  }

  onApplicationChange(id: number): void {
    this.applicationId$.next(id);
  }
}