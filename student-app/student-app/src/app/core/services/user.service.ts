import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User as UserModel } from '../../shared/models/user.model';  
import { environment } from '../../../enviroment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  
  private baseUrl = `${environment.apiUrl}/users`; // Adjust the URL as needed

  constructor(private http: HttpClient) {}

  getAll(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(this.baseUrl);
  }

  getById(id: number): Observable<UserModel> {
    return this.http.get<UserModel>(`${this.baseUrl}/${id}`);
  }
}
