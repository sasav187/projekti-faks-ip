import { Routes } from '@angular/router';

import { LoginComponent } from './features/auth/login/login.component';
import { MainLayoutComponent } from './layout/main-layout.component';
import { InternshipListComponent } from './features/internships/internship-list.component';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'internships', component: InternshipListComponent},
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
