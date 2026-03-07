import { Routes } from '@angular/router';

import { MainLayoutComponent } from './layout/main-layout.component.js';
import { MainViewComponent } from './view/main-view.component.js';
import { CVComponent } from './features/cv/cv.component.js';
import { InternshipListComponent } from './features/internships/internship-list.component.js';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './features/auth/login/login.component.js';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', component: MainViewComponent },
      { path: 'cv', component: CVComponent },
      { path: 'internships', component: InternshipListComponent },
      // { path: 'recommendations', component: RecommendationsComponent } // kasnije
    ]
  },

  { path: '**', redirectTo: '' }
];