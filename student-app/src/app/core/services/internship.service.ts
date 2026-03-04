import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Internship } from '../../shared/models/internship.model';

@Injectable({
  providedIn: 'root'
})
export class InternshipService {

  private api = 'http://localhost:8081/api/internships';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Internship[]> {
    return this.http.get<any>(this.api).pipe(
      map(response => {
        console.log('FULL RESPONSE:', response);
        return response.content ?? [];
      })
    );
  }
}