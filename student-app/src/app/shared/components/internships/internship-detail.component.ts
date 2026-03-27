import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MaterialModule } from '../../../material.module';
import { InternshipService } from '../../../core/services/internship.service';
import { Internship } from '../../models/internship.model';
import { Observable, of } from 'rxjs';
import { catchError, tap, switchMap } from 'rxjs/operators';

@Component({
    standalone: true,
    selector: 'app-internship-detail',
    imports: [CommonModule, MaterialModule],
    templateUrl: './internship-detail.component.html',
    styleUrls: ['./internship-detail.component.scss']
})
export class InternshipDetailComponent implements OnInit {

    internship$!: Observable<Internship | null>;
    loading = true;
    errorMessage = '';

    constructor(
        private route: ActivatedRoute,
        private internshipService: InternshipService
    ) { }

    ngOnInit(): void {
        this.internship$ = this.route.paramMap.pipe(
            switchMap(params => {
                const id = Number(params.get('id'));
                if (!id) {
                    this.errorMessage = 'Invalid internship ID';
                    this.loading = false;
                    return of(null);
                }
                this.loading = true;
                return this.internshipService.getById(id).pipe(
                    tap(() => this.loading = false),
                    catchError(() => {
                        this.errorMessage = 'Failed to load internship';
                        this.loading = false;
                        return of(null);
                    })
                );
            })
        );
    }

    apply(internshipId: number) {
        const studentId = Number(localStorage.getItem('studentId'));
        if (!studentId) {
            alert('Student not logged in');
            return;
        }
        this.internshipService.apply(internshipId, studentId).subscribe({
            next: () => alert('Application sent successfully'),
            error: () => alert('Application failed')
        });
    }
}