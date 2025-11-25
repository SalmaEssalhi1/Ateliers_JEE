import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-employee-shell',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './employee-shell.component.html',
  styleUrl: './employee-shell.component.css'
})
export class EmployeeShellComponent {}
