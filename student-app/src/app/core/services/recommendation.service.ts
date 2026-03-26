import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecommendationResponseDTO } from '../../shared/models/recommendation-response.model';

@Injectable({
    providedIn: 'root'
})
export class RecommendationService {

    private apiUrl = 'http://localhost:8081/api/recommendations';

    constructor(private http: HttpClient) { }

    private getHeaders(): HttpHeaders {
        const token = localStorage.getItem('token') || '';
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        });
    }

    generateRecommendation(internshipId: number): Observable<RecommendationResponseDTO> {
        return this.http.post<RecommendationResponseDTO>(
            `${this.apiUrl}/generate`,
            {}, // body empty
            {
                headers: this.getHeaders(),
                params: new HttpParams().set('internshipId', internshipId)
            }
        );
    }

    getRecommendationsForStudent(): Observable<RecommendationResponseDTO[]> {
        return this.http.get<RecommendationResponseDTO[]>(
            `${this.apiUrl}/student`,
            { headers: this.getHeaders() }
        );
    }
}