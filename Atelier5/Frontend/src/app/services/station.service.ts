import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Station } from '../models/station.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StationService {
  private apiUrl = `${environment.apiUrl}/api/stations`;

  constructor(private http: HttpClient) { }

  // GET - Récupérer toutes les stations
  getAllStations(): Observable<Station[]> {
    return this.http.get<Station[]>(this.apiUrl);
  }

  // GET - Récupérer une station par ID
  getStationById(id: number): Observable<Station> {
    return this.http.get<Station>(`${this.apiUrl}/${id}`);
  }

  // POST - Créer une nouvelle station
  createStation(station: Station): Observable<Station> {
    return this.http.post<Station>(this.apiUrl, station);
  }

  // PUT - Modifier une station
  updateStation(id: number, station: Station): Observable<Station> {
    return this.http.put<Station>(`${this.apiUrl}/${id}`, station);
  }

  // DELETE - Supprimer une station
  deleteStation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
