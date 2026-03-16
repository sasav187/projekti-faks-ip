import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { tap } from 'rxjs';

import { jwtDecode } from 'jwt-decode';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private apiUrl = 'http://localhost:8081/api/auth';

  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    return this.http.post<any>(`${this.apiUrl}/login`, {
      username,
      password
    })
      .pipe(
        tap(response => {
          if (response.token) localStorage.setItem('token', response.token);
          if (response.role) localStorage.setItem('role', response.role);
          localStorage.setItem('username', username);
        })
      );
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  isTokenExpiringSoon(): boolean {

    const token = this.getToken();
    if (!token) return false;

    const decoded: any = jwtDecode(token);

    const exp = decoded.exp * 1000;
    const now = Date.now();

    const minutesLeft = (exp - now) / 60000;

    return minutesLeft < 5;
  }

  isTokenExpired(): boolean {

    const token = this.getToken();
    if (!token) return true;

    const decoded: any = jwtDecode(token);

    return Date.now() >= decoded.exp * 1000;
  }

  getUserName(): string | null {
    return localStorage.getItem('username');
  }
}