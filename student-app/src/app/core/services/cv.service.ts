import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CV } from '../../shared/models/cv.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CVService {

  private apiUrl = 'http://localhost:8081/api/cvs';

  constructor(private http: HttpClient) { }

  getMyCV(): Observable<CV> {
    return this.http.get<CV>(`${this.apiUrl}/me`);
  }

  uploadImage(cvId: number, file: File) {
    const formData = new FormData()
    formData.append('file', file);

    return this.http.post(
      `${this.apiUrl}/upload-image/${cvId}`,
      formData
    )
  }

  saveCV(cv: CV): Observable<CV> {
    if (cv.id) {
      return this.http.put<CV>(`${this.apiUrl}/${cv.id}`, cv);
    } else {
      return this.http.post<CV>(`${this.apiUrl}`, cv);
    }
  }
}