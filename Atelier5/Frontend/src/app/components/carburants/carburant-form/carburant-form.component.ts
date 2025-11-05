import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { Carburant } from '../../../models/carburant.model';
import { CarburantService } from '../../../services/carburant.service';

@Component({
  selector: 'app-carburant-form',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './carburant-form.component.html',
  styleUrl: './carburant-form.component.css'
})
export class CarburantFormComponent implements OnInit {
  carburant: Carburant = {
    nom: '',
    description: ''
  };
  isEditMode = false;
  carburantId?: number;

  constructor(
    private carburantService: CarburantService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.carburantId = +id;
      this.loadCarburant(this.carburantId);
    }
  }

  loadCarburant(id: number): void {
    this.carburantService.getCarburantById(id).subscribe({
      next: (data) => {
        this.carburant = data;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  onSubmit(): void {
    if (this.isEditMode && this.carburantId) {
      this.carburantService.updateCarburant(this.carburantId, this.carburant).subscribe({
        next: () => {
          this.router.navigate(['/carburants']);
        },
        error: (err) => {
          console.error(err);
        }
      });
    } else {
      this.carburantService.createCarburant(this.carburant).subscribe({
        next: () => {
          this.router.navigate(['/carburants']);
        },
        error: (err) => {
          console.error(err);
        }
      });
    }
  }
}

