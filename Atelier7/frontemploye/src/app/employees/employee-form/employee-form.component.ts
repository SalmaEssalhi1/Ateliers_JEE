import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { EmployeeService } from '../../core/services/employee.service';
import { Employee } from '../../core/models/employee.model';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './employee-form.component.html',
  styleUrl: './employee-form.component.css'
})
export class EmployeeFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly employeeService = inject(EmployeeService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  readonly form = this.fb.nonNullable.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    salary: [0, [Validators.required, Validators.min(0)]]
  });

  mode: 'create' | 'edit' = 'create';
  employeeId?: number;
  isLoading = false;
  isSaving = false;
  errorMessage: string | null = null;

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.mode = 'edit';
      this.employeeId = Number(id);
      this.fetchEmployee();
    }
  }

  submit(): void {
    if (this.form.invalid || this.isSaving) {
      this.form.markAllAsTouched();
      return;
    }

    this.isSaving = true;
    this.errorMessage = null;
    const payload = this.form.getRawValue() as Employee;

    const operation =
      this.mode === 'create' || !this.employeeId
        ? this.employeeService.createEmployee(payload)
        : this.employeeService.updateEmployee(this.employeeId, payload);

    operation
      .pipe(finalize(() => (this.isSaving = false)))
      .subscribe({
        next: () => this.router.navigate(['/employees']),
        error: (error) => {
          this.errorMessage = error?.error ?? 'Une erreur est survenue, veuillez réessayer.';
        }
      });
  }

  private fetchEmployee(): void {
    if (!this.employeeId) {
      return;
    }

    this.isLoading = true;
    this.employeeService
      .getEmployee(this.employeeId)
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe({
        next: (employee) => this.form.patchValue(employee),
        error: () => {
          this.errorMessage = 'Impossible de charger cet employé.';
        }
      });
  }
}
