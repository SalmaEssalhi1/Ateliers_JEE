import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, finalize, takeUntil } from 'rxjs';

import { AuthService } from '../../core/services/auth.service';
import { LoginPayload } from '../../core/models/auth.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit, OnDestroy {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  readonly form = this.fb.nonNullable.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  errorMessage: string | null = null;
  isSubmitting = false;
  private readonly destroy$ = new Subject<void>();
  private returnUrl = '/employees';

  ngOnInit(): void {
    const url = this.route.snapshot.queryParamMap.get('returnUrl');
    if (url) {
      this.returnUrl = url;
    }
  }

  submit(): void {
    if (this.form.invalid || this.isSubmitting) {
      this.form.markAllAsTouched();
      return;
    }

    this.errorMessage = null;
    this.isSubmitting = true;
    const payload = this.form.getRawValue() as LoginPayload;

    this.authService
      .login(payload)
      .pipe(
        takeUntil(this.destroy$),
        finalize(() => (this.isSubmitting = false))
      )
      .subscribe({
        next: () => this.router.navigateByUrl(this.returnUrl),
        error: (error) => {
          console.error('Login error:', error);
          
          // Gérer différents types d'erreurs
          if (error instanceof Error) {
            // Erreur réseau (CORS, connexion refusée, etc.)
            if (error.message.includes('CORS') || error.message.includes('Failed to fetch')) {
              this.errorMessage = 'Erreur de connexion au serveur. Vérifiez que le backend est démarré sur le port 8080.';
            } else {
              this.errorMessage = error.message;
            }
          } else if (error?.error) {
            // Erreur HTTP avec body
            this.errorMessage = typeof error.error === 'string' ? error.error : error.error?.message || 'Identifiants invalides';
          } else if (error?.status === 401) {
            this.errorMessage = 'Identifiants invalides. Vérifiez votre nom d\'utilisateur et mot de passe.';
          } else if (error?.status === 0 || error?.statusText === 'Unknown Error') {
            this.errorMessage = 'Impossible de se connecter au serveur. Vérifiez que le backend Spring Boot est démarré.';
          } else {
            this.errorMessage = 'Impossible de se connecter. Vérifiez vos identifiants.';
          }
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
