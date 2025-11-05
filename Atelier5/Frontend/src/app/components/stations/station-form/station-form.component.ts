import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { Station } from '../../../models/station.model';
import { StationService } from '../../../services/station.service';

@Component({
  selector: 'app-station-form',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './station-form.component.html',
  styleUrl: './station-form.component.css'
})
export class StationFormComponent implements OnInit {
  station: Station = {
    nom: '',
    ville: '',
    adresse: ''
  };
  isEditMode = false;
  stationId?: number;

  constructor(
    private stationService: StationService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.stationId = +id;
      this.loadStation(this.stationId);
    }
  }

  loadStation(id: number): void {
    this.stationService.getStationById(id).subscribe({
      next: (data) => {
        this.station = data;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  onSubmit(): void {
    if (this.isEditMode && this.stationId) {
      this.stationService.updateStation(this.stationId, this.station).subscribe({
        next: () => {
          this.router.navigate(['/stations']);
        },
        error: (err) => {
          console.error(err);
        }
      });
    } else {
      this.stationService.createStation(this.station).subscribe({
        next: () => {
          this.router.navigate(['/stations']);
        },
        error: (err) => {
          console.error(err);
        }
      });
    }
  }
}

