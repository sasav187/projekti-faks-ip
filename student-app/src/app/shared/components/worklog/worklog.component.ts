import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../material.module';

import { WorkLog } from '../../models/worklog.model';
import { WorkLogService } from '../../../core/services/worklog.service';

@Component({
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
  applicationId!: number;
  weekNumber = 1;
  description = '';

  constructor(private workLogService: WorkLogService) { }

  ngOnInit() {
    console.log("WORKLOG COMPONENT LOADED");
    this.loadLogs();
  }

  loadLogs() {
    console.log("LOADING LOGS...");
    this.workLogService.getAll().subscribe(res => {
      this.logs = res.content;
    });
  }

  save() {
    const log: WorkLog = { applicationId: this.applicationId, weekNumber: this.weekNumber, description: this.description };
    this.workLogService.create(log).subscribe(() => {
      this.description = '';
      this.loadLogs();
    });
  }

  delete(id: number) {
    this.workLogService.delete(id).subscribe(() => this.loadLogs());
  }
}
