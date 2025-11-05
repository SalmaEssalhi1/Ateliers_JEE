import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Carburant } from '../../models/carburant.model';
import { CarburantService } from '../../services/carburant.service';

@Component({
  selector: 'app-carburants',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './carburants.component.html',
  styleUrl: './carburants.component.css'
})
export class CarburantsComponent implements OnInit {
  carburants: Carburant[] = [];
  allCarburants: Carburant[] = [];
  loading = false;
  error: string | null = null;
  search: string = '';

  constructor(private carburantService: CarburantService) { }

  ngOnInit(): void {
    this.loadCarburants();
  }

  loadCarburants(): void {
    this.loading = true;
    this.error = null;

    this.carburantService.getAllCarburants().subscribe({
      next: (data) => {
        this.allCarburants = data;
        this.applySearch();
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des carburants';
        this.loading = false;
        console.error(err);
      }
    });
  }

  applySearch(): void {
    const q = this.search?.toLowerCase().trim();
    if (!q) {
      this.carburants = [...this.allCarburants];
      return;
    }
    this.carburants = this.allCarburants.filter(c =>
      (c.nom || '').toLowerCase().includes(q) ||
      (c.description || '').toLowerCase().includes(q)
    );
  }

  deleteCarburant(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce carburant ?')) {
      this.carburantService.deleteCarburant(id).subscribe({
        next: () => {
          this.loadCarburants();
        },
        error: (err) => {
          this.error = 'Erreur lors de la suppression';
          console.error(err);
        }
      });
    }
  }
}
