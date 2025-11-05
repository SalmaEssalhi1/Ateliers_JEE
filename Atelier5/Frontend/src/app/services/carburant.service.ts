import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Carburant } from '../models/carburant.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CarburantService {
  private apiUrl = `${environment.apiUrl}/api/carburants`;

  constructor(private http: HttpClient) { }

  getAllCarburants(): Observable<Carburant[]> {
    return this.http.get<Carburant[]>(this.apiUrl);
  }

  getCarburantById(id: number): Observable<Carburant> {
    return this.http.get<Carburant>(`${this.apiUrl}/${id}`);
  }

  createCarburant(carburant: Carburant): Observable<Carburant> {
    return this.http.post<Carburant>(this.apiUrl, carburant);
  }

  updateCarburant(id: number, carburant: Carburant): Observable<Carburant> {
    return this.http.put<Carburant>(`${this.apiUrl}/${id}`, carburant);
  }

  deleteCarburant(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
