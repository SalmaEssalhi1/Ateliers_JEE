import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

import { EmployeeService } from '../../core/services/employee.service';
import { Employee } from '../../core/models/employee.model';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.css'
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];
  isLoading = false;
  errorMessage: string | null = null;

  constructor(private readonly employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  refresh(): void {
    this.loadEmployees();
  }

  trackById(_index: number, employee: Employee): number | undefined {
    return employee.id;
  }

  private loadEmployees(): void {
    this.isLoading = true;
    this.errorMessage = null;

    this.employeeService.getEmployees().subscribe({
      next: (employees) => {
        this.employees = employees;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Impossible de charger les employés pour le moment.';
        this.isLoading = false;
      }
    });
  }
}
