import { Routes } from '@angular/router';
import { StationListComponent } from './components/stations/stations.component';
import { StationFormComponent } from './components/stations/station-form/station-form.component';
import { CarburantsComponent } from './components/carburants/carburants.component';
import { CarburantFormComponent } from './components/carburants/carburant-form/carburant-form.component';
import { PrixComponent } from './components/prix/prix.component';
import { PrixFormComponent } from './components/prix/prix-form/prix-form.component';

export const routes: Routes = [
  { path: 'stations', component: StationListComponent },
  { path: 'stations/new', component: StationFormComponent },
  { path: 'stations/edit/:id', component: StationFormComponent },
  { path: 'carburants', component: CarburantsComponent },
  { path: 'carburants/new', component: CarburantFormComponent },
  { path: 'carburants/edit/:id', component: CarburantFormComponent },
  { path: 'prix', component: PrixComponent },
  { path: 'prix/new', component: PrixFormComponent },
  { path: '', redirectTo: '/stations', pathMatch: 'full' }
];
