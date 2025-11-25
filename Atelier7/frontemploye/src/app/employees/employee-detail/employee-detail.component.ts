import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { EmployeeService } from '../../core/services/employee.service';
import { Employee } from '../../core/models/employee.model';

@Component({
  selector: 'app-employee-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './employee-detail.component.html',
  styleUrl: './employee-detail.component.css'
})
export class EmployeeDetailComponent implements OnInit {
  employee?: Employee;
  isLoading = false;
  errorMessage: string | null = null;

  constructor(private readonly route: ActivatedRoute, private readonly employeeService: EmployeeService) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.errorMessage = 'Identifiant non valide.';
      return;
    }

    this.fetchEmployee(id);
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
