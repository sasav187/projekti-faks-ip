import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

interface Credentials {
  username: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _loggedIn = false;

  isLoggedIn(): boolean {
    return this._loggedIn;
  }

  login(_creds: Credentials): Observable<void> {
    this._loggedIn = true;
    return of(undefined);
  }

  logout(): void {
    this._loggedIn = false;
  }
}

