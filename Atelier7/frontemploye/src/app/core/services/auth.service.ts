import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { Router } from '@angular/router';

import { API_BASE_URL } from '../config/api.config';
import { LoginPayload, LoginResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly tokenKey = 'atelier7_token';
  private readonly usernameKey = 'atelier7_username';
  private readonly endpoint = `${API_BASE_URL}/auth`;

  private readonly currentUserSubject = new BehaviorSubject<string | null>(this.getStoredUsername());
  readonly currentUser$ = this.currentUserSubject.asObservable();

  constructor(private readonly http: HttpClient, private readonly router: Router) {}

  login(payload: LoginPayload): Observable<void> {
    return this.http.post<LoginResponse>(`${this.endpoint}/login`, payload).pipe(
      tap((response) => this.persistSession(response)),
      map(() => void 0)
    );
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.usernameKey);
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  getUsername(): string | null {
    return this.currentUserSubject.value;
  }

  private persistSession(response: LoginResponse): void {
    localStorage.setItem(this.tokenKey, response.token);
    localStorage.setItem(this.usernameKey, response.username);
    this.currentUserSubject.next(response.username);
  }

  private getStoredUsername(): string | null {
    return localStorage.getItem(this.usernameKey);
  }
}
