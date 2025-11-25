import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { Employee } from '../../core/models/employee.model';
import { EmployeeService } from '../../core/services/employee.service';

@Component({
  selector: 'app-employee-delete',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './employee-delete.component.html',
  styleUrl: './employee-delete.component.css'
})
export class EmployeeDeleteComponent implements OnInit {
  employee?: Employee;
  isLoading = false;
  isDeleting = false;
  errorMessage: string | null = null;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly employeeService: EmployeeService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.errorMessage = 'Identifiant non valide.';
      return;
    }

    this.fetchEmployee(id);
  }

  confirmDelete(): void {
    if (!this.employee?.id || this.isDeleting) {
      return;
    }

    this.isDeleting = true;
    this.employeeService
      .deleteEmployee(this.employee.id)
      .pipe(finalize(() => (this.isDeleting = false)))
      .subscribe({
        next: () => this.router.navigate(['/employees']),
        error: () => {
          this.errorMessage = 'Impossible de supprimer cet employé.';
        }
      });
  }

  private fetchEmployee(id: number): void {
    this.isLoading = true;
    this.employeeService.getEmployee(id).subscribe({
      next: (employee) => {
        this.employee = employee;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Employé introuvable.';
        this.isLoading = false;
      }
    });
  }
}
