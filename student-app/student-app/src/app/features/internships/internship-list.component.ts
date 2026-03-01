import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InternshipService } from '../../core/services/internship.service';
import { Internship } from '../../shared/models/internship.model';

@Component({
  standalone: true,
  selector: 'app-internship-list',
  imports: [CommonModule],
  templateUrl: './internship-list.html',
  styleUrls: ['./internship-list.css']
})
export class InternshipListComponent implements OnInit {

  internships: Internship[] = [];
  loading = true;
  errorMessage = '';

  constructor(private internshipService: InternshipService) {}

  ngOnInit(): void {
    this.internshipService.getAll().subscribe({
      next: (data: Internship[]) => {
        this.internships = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Failed to load internships.';
        this.loading = false;
      }
    });
  }
}