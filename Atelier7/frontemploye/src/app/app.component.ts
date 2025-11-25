import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AsyncPipe } from '@angular/common';

import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, AsyncPipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontemploye';
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  readonly currentUser$ = this.authService.currentUser$;

  logout(): void {
    this.authService.logout();
  }

  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }
}
