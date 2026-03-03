import { Routes } from '@angular/router';

import { LoginComponent } from './features/auth/login/login.component';
import { InternshipListComponent } from './features/internships/internship-list.component';
import { CompanyDashboardComponent } from './features/dashboard/company-dashboard/company-dashboard';
import { FacultyDashboardComponent } from './features/dashboard/faculty-dashboard/faculty-dashboard';
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
  {
    path: 'company/dashboard',
    component: CompanyDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['COMPANY'] }
  },
  {
    path: 'faculty/dashboard',
    component: FacultyDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['FACULTY'] }
  },

  { path: 'unauthorized', component: UnauthorizedComponent },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];