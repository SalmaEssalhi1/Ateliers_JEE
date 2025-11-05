import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HistoCarb } from '../../models/histo-carb.model';
import { PrixService } from '../../services/prix.service';
import { StationService } from '../../services/station.service';
import { CarburantService } from '../../services/carburant.service';
import { Station } from '../../models/station.model';
import { Carburant } from '../../models/carburant.model';

@Component({
  selector: 'app-prix',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './prix.component.html',
  styleUrl: './prix.component.css'
})
export class PrixComponent implements OnInit {
  prix: HistoCarb[] = [];
  stations: Station[] = [];
  carburants: Carburant[] = [];
  loading = false;
  error: string | null = null;
  selectedStationId: number | null = null;
  selectedDate: string = '';

  constructor(
    private prixService: PrixService,
    private stationService: StationService,
    private carburantService: CarburantService
  ) { }

  ngOnInit(): void {
    this.loadStations();
    this.loadCarburants();
    this.loadPrix();
  }

  loadStations(): void {
    this.stationService.getAllStations().subscribe({
      next: (data) => {
        this.stations = data;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  loadCarburants(): void {
    this.carburantService.getAllCarburants().subscribe({
      next: (data) => {
        this.carburants = data;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  loadPrix(): void {
    this.loading = true;
    this.error = null;

    this.prixService.getAllPrix().subscribe({
      next: (data) => {
        this.prix = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des prix';
        this.loading = false;
        console.error(err);
      }
    });
  }

  filterPrix(): void {
    if (this.selectedStationId) {
      this.loading = true;
      this.error = null;
      this.prixService.getPrixByStation(this.selectedStationId, this.selectedDate || undefined).subscribe({
        next: (data) => {
          this.prix = data;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Erreur lors du filtrage';
          this.loading = false;
          console.error(err);
        }
      });
    } else {
      this.loadPrix();
    }
  }

  getStationName(station: any): string {
    if (typeof station === 'object' && station.nom) {
      return station.nom;
    }
    const found = this.stations.find(s => s.id === station?.id);
    return found ? found.nom : 'N/A';
  }

  getCarburantName(carburant: any): string {
    if (typeof carburant === 'object' && carburant.nom) {
      return carburant.nom;
    }
    const found = this.carburants.find(c => c.id === carburant?.id);
    return found ? found.nom : 'N/A';
  }

  deletePrix(prix: HistoCarb): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce prix ?')) {
      const stationId = typeof prix.station === 'object' ? prix.station.id : prix.station;
      const carburantId = typeof prix.carburant === 'object' ? prix.carburant.id : prix.carburant;
      
      if (stationId && carburantId) {
        this.prixService.deletePrix(prix.date, stationId, carburantId).subscribe({
          next: () => {
            this.loadPrix();
          },
          error: (err) => {
            this.error = 'Erreur lors de la suppression';
            console.error(err);
          }
        });
      }
    }
  }
}
