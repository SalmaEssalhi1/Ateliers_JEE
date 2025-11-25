import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../config/api.config';
import { Employee } from '../models/employee.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private readonly endpoint = `${API_BASE_URL}/employees`;

  constructor(private readonly http: HttpClient) {}

  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.endpoint);
  }

  getEmployee(id: number): Observable<Employee> {
    return this.http.get<Employee>(`${this.endpoint}/${id}`);
  }

  createEmployee(payload: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.endpoint, payload);
  }

  updateEmployee(id: number, payload: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.endpoint}/${id}`, payload);
  }

  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.endpoint}/${id}`);
  }
}
