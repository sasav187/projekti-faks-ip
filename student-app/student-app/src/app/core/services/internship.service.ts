import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Internship as InternshipModel } from '../../shared/models/internship.model';
import { environment } from '../../../enviroment';

@Injectable({
  providedIn: 'root',
})
export class InternshipService {
  
  private baseUrl = `${environment.apiUrl}/internships`; // Adjust the URL as needed

  constructor(private http: HttpClient) {}

  getAll(): Observable<InternshipModel[]> {
    return this.http.get<InternshipModel[]>(this.baseUrl);
  }
}