import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MaterialModule } from '../../../material.module';
import { InternshipService } from '../../../core/services/internship.service';
import { Internship } from '../../models/internship.model';

@Component({
    standalone: true,
    selector: 'app-internship-detail',
    imports: [CommonModule, MaterialModule],
    templateUrl: './internship-detail.component.html',
    styleUrls: ['./internship-detail.component.scss']
})
export class InternshipDetailComponent implements OnInit {

    internship?: Internship;
    loading = true;
    errorMessage = '';

    constructor(
        private route: ActivatedRoute,
        private internshipService: InternshipService
    ) { }

    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            const id = Number(params.get('id'));
            if (!id) {
                this.errorMessage = 'Invalid internship ID';
                this.loading = false;
                return;
            }

            this.internshipService.getById(id).subscribe({
                next: data => {
                    this.internship = data;
                    this.loading = false;
                },
                error: () => {
                    this.errorMessage = 'Failed to load internship';
                    this.loading = false;
                }
            });
        });
    }
}