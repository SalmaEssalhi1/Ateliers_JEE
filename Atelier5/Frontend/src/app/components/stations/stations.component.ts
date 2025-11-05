import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Station } from '../../models/station.model';
import { StationService } from '../../services/station.service';

@Component({
  selector: 'app-stations',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './stations.component.html',
  styleUrl: './stations.component.css'
})
export class StationListComponent implements OnInit {
  stations: Station[] = [];
  allStations: Station[] = [];
  loading = false;
  error: string | null = null;
  search: string = '';

  constructor(private stationService: StationService) { }

  ngOnInit(): void {
    this.loadStations();
  }

  loadStations(): void {
    this.loading = true;
    this.error = null;

    this.stationService.getAllStations().subscribe({
      next: (data) => {
        this.allStations = data;
        this.applySearch();
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des stations';
        this.loading = false;
        console.error(err);
      }
    });
  }

  applySearch(): void {
    const q = this.search?.toLowerCase().trim();
    if (!q) {
      this.stations = [...this.allStations];
      return;
    }
    this.stations = this.allStations.filter(s =>
      (s.nom || '').toLowerCase().includes(q) ||
      (s.ville || '').toLowerCase().includes(q) ||
      (s.adresse || '').toLowerCase().includes(q)
    );
  }

  deleteStation(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette station ?')) {
      this.stationService.deleteStation(id).subscribe({
        next: () => {
          this.loadStations(); // Recharger la liste
        },
        error: (err) => {
          this.error = 'Erreur lors de la suppression';
          console.error(err);
        }
      });
    }
  }
}
