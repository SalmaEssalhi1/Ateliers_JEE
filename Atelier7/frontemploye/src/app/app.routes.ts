import { Routes } from '@angular/router';

import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./auth/login/login.component').then((m) => m.LoginComponent)
  },
  {
    path: 'employees',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    loadComponent: () => import('./employees/employee-shell/employee-shell.component').then((m) => m.EmployeeShellComponent),
    children: [
      {
        path: '',
        loadComponent: () => import('./employees/employee-list/employee-list.component').then((m) => m.EmployeeListComponent)
      },
      {
        path: 'new',
        loadComponent: () => import('./employees/employee-form/employee-form.component').then((m) => m.EmployeeFormComponent)
      },
      {
        path: ':id',
        loadComponent: () => import('./employees/employee-detail/employee-detail.component').then((m) => m.EmployeeDetailComponent)
      },
      {
        path: ':id/edit',
        loadComponent: () => import('./employees/employee-form/employee-form.component').then((m) => m.EmployeeFormComponent)
      },
      {
        path: ':id/delete',
        loadComponent: () => import('./employees/employee-delete/employee-delete.component').then((m) => m.EmployeeDeleteComponent)
      }
    ]
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'employees'
  },
  {
    path: '**',
    redirectTo: 'employees'
  }
];
