import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../material.module';

import { WorkLog } from '../../models/worklog.model';
import { WorkLogService } from '../../../core/services/worklog.service';

import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  standalone: true,
  selector: 'app-worklog',
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule
  ],
  templateUrl: './worklog.component.html',
  styleUrls: ['./worklog.component.scss'],
})
export class WorklogComponent implements OnInit {

  logs: WorkLog[] = [];
  loading = true;
  errorMessage = '';

  applicationId!: number;
  weekNumber = 1;
  description = '';

  searchDescription = '';

  constructor(private workLogService: WorkLogService) { }

  ngOnInit() {
    this.loadLogs();
  }

  loadLogs() {
    this.loading = true;

    this.workLogService.getAll()
      .pipe(
        catchError(() => {
          this.errorMessage = 'Failed to load logs';
          return of({ content: [] });
        })
      )
      .subscribe(res => {
        this.logs = res.content;
        this.loading = false;
      });
  }

  save() {
    const log: WorkLog = {
      applicationId: this.applicationId,
      weekNumber: this.weekNumber,
      description: this.description
    };

    this.workLogService.create(log).subscribe(() => {
      this.description = '';
      this.loadLogs();
    });
  }

  delete(id: number) {
    this.workLogService.delete(id).subscribe(() => this.loadLogs());
  }

  onSearch() {
    this.loading = true;
    this.errorMessage = '';

    this.workLogService.search(this.searchDescription)
      .pipe(
        catchError(() => {
          this.errorMessage = 'Search failed';
          return of({ content: [] });
        })
      )
      .subscribe(res => {
        this.logs = res.content;
        this.loading = false;
      });
  }

  clearSearch() {
    this.searchDescription = '';
    this.loadLogs();
  }
}