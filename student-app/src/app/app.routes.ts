import { Routes } from '@angular/router';

import { MainLayoutComponent } from './layout/main-layout.component.js';
import { MainViewComponent } from './view/main-view.component.js';
import { CVComponent } from './shared/components/cv/cv.component.js';
import { InternshipListComponent } from './shared/components/internships/internship-list.component.js';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './features/auth/login/login.component.js';
import { UnauthorizedComponent } from './features/auth/unauthorized/unauthorized.component.js';
import { WorklogComponent } from './shared/components/worklog/worklog.component.js';
import { RecommendationsComponent } from './shared/components/recommendations/recommendations.component.js';
import { InternshipDetailComponent } from './shared/components/internships/internship-detail.component.js';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  { path: 'unauthorized', component: UnauthorizedComponent },

  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', component: MainViewComponent },
      { path: 'cv', component: CVComponent },
      { path: 'internships', component: InternshipListComponent },
      { path: 'internships/:id', component: InternshipDetailComponent},
      { path: 'recommendations', component: RecommendationsComponent },
      { path: 'worklog', component: WorklogComponent }
    ]
  },

  { path: '**', redirectTo: '' }
];