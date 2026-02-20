import { Component, OnInit } from '@angular/core';
import { Internship } from '../../../shared/models/internship.model';
import { InternshipService } from '../../../core/services/internship.service';

@Component({
  selector: 'app-internship-list',
  imports: [],
  templateUrl: './internship-list.html',
  styleUrl: './internship-list.css',
})
export class InternshipListComponent implements OnInit {
  
  internships: Internship[] = [];

  constructor(private internshipService: InternshipService) {}

  ngOnInit() {
    this.internshipService.getAll().subscribe(data => {
      this.internships = data;
    });
  }
}
