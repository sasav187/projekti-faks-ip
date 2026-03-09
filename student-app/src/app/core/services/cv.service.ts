import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CV } from '../../shared/models/cv.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CVService {

  private apiUrl = 'http://localhost:8081/api/cvs';

  constructor(private http: HttpClient) {}

  getMyCV(): Observable<CV> {
    return this.http.get<CV>(`${this.apiUrl}/me`);
  }

  saveCV(cv: CV): Observable<CV> {
    if (cv.id) {
      return this.http.put<CV>(`${this.apiUrl}/me`, cv);
    } else {
      return this.http.post<CV>(`${this.apiUrl}`, cv);
    }
  }
}