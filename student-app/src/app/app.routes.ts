import { Routes } from '@angular/router';

import { LoginComponent } from './features/auth/login/login.component';
import { InternshipListComponent } from './features/internships/internship-list.component';
import { UnauthorizedComponent } from './features/auth/unauthorized/unauthorized';

import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: 'internships',
    component: InternshipListComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['STUDENT'] }
  },

  { path: 'unauthorized', component: UnauthorizedComponent },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];