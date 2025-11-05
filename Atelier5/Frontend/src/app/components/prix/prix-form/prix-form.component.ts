import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HistoCarb } from '../../../models/histo-carb.model';
import { PrixService } from '../../../services/prix.service';
import { StationService } from '../../../services/station.service';
import { CarburantService } from '../../../services/carburant.service';
import { Station } from '../../../models/station.model';
import { Carburant } from '../../../models/carburant.model';

@Component({
  selector: 'app-prix-form',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './prix-form.component.html',
  styleUrl: './prix-form.component.css'
})
export class PrixFormComponent implements OnInit {
  date: string = new Date().toISOString().split('T')[0];
  prixValue: number = 0;
  selectedStationId: number = 0;
  selectedCarburantId: number = 0;
  stations: Station[] = [];
  carburants: Carburant[] = [];

  constructor(
    private prixService: PrixService,
    private stationService: StationService,
    private carburantService: CarburantService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadStations();
    this.loadCarburants();
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

  onSubmit(): void {
    if (this.selectedStationId === 0 || this.selectedCarburantId === 0) {
      return;
    }

    const prixData: HistoCarb = {
      date: this.date,
      prix: this.prixValue,
      station: { id: this.selectedStationId },
      carburant: { id: this.selectedCarburantId }
    };

    this.prixService.createPrix(prixData).subscribe({
      next: () => {
        this.router.navigate(['/prix']);
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}

