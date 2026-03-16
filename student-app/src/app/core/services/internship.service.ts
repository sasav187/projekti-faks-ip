import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Internship } from '../../shared/models/internship.model';
import { Page } from '../../shared/models/page.model';
import { I } from '@angular/cdk/keycodes';

@Injectable({
  providedIn: 'root'
})
export class InternshipService {

  private api = 'http://localhost:8081/api/internships';

  constructor(private http: HttpClient) { }

  getAll(page = 0, size = 10): Observable<Internship[]> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<Page<Internship>>(this.api, { params })
      .pipe(
        map(res => res.content)
      );
  }

  search(title?: string, technology?: string): Observable<Internship[]> {

    let params = new HttpParams();

    if (title) params = params.set('title', title);
    if (technology) params = params.set('technology', technology);

    return this.http
      .get<Page<Internship>>(`${this.api}/search`, { params })
      .pipe(
        map(res => res.content)
      );
  }

  apply(internshipId: number) {
    return this.http.post(
      'http://localhost:8081/api/internship-applications',
      { internshipId: internshipId }
    );
  }

}