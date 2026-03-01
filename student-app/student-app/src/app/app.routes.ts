import { Routes } from '@angular/router';

import { LoginComponent } from './features/auth/login/login.component';
import { MainLayoutComponent } from './layout/main-layout.component';
import { InternshipListComponent } from './features/internships/internship-list.component';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'internships', pathMatch: 'full' },
      { path: 'internships', component: InternshipListComponent }
    ]
  },

  // wildcard can be removed now that base path handles redirect,
  // but it doesn't hurt to keep it for any unknown URLs
  { path: '**', redirectTo: 'internships' }
];
