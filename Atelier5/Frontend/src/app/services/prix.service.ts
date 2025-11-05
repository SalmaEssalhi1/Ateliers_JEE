import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HistoCarb } from '../models/histo-carb.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PrixService {
  private apiUrl = `${environment.apiUrl}/api/prix`;

  constructor(private http: HttpClient) { }

  // GET - Récupérer tous les prix
  getAllPrix(): Observable<HistoCarb[]> {
    return this.http.get<HistoCarb[]>(this.apiUrl);
  }

  // GET - Récupérer un prix par clé composite
  getPrixById(date: string, stationId: number, carburantId: number): Observable<HistoCarb> {
    return this.http.get<HistoCarb>(`${this.apiUrl}/${date}/${stationId}/${carburantId}`);
  }

  // GET - Récupérer les prix d'une station
  getPrixByStation(stationId: number, date?: string): Observable<HistoCarb[]> {
    let params = new HttpParams();
    if (date) {
      params = params.set('date', date);
    }
    return this.http.get<HistoCarb[]>(`${this.apiUrl}/station/${stationId}`, { params });
  }

  // POST - Créer un nouveau prix
  createPrix(prix: HistoCarb): Observable<HistoCarb> {
    return this.http.post<HistoCarb>(this.apiUrl, prix);
  }

  // PUT - Modifier un prix
  updatePrix(date: string, stationId: number, carburantId: number, prix: HistoCarb): Observable<HistoCarb> {
    return this.http.put<HistoCarb>(`${this.apiUrl}/${date}/${stationId}/${carburantId}`, prix);
  }

  // DELETE - Supprimer un prix
  deletePrix(date: string, stationId: number, carburantId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${date}/${stationId}/${carburantId}`);
  }
}
