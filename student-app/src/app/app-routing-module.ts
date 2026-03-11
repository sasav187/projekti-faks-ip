import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './features/auth/login/login.component';
import { UnauthorizedComponent } from './features/auth/unauthorized/unauthorized.component';
import { MainLayoutComponent } from './layout/main-layout.component';
import { InternshipListComponent } from './features/internships/internship-list.component';
import { CVComponent } from './features/cv/cv.component';

import { AuthGuard } from './core/guards/auth.guard';
import { StudentGuard } from './core/guards/student.guard';
import { WorklogComponent } from './features/worklog/worklog.component';
import { RecommendationsComponent } from './features/recommendations/recommendations.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'cv', component: CVComponent, canActivate: [StudentGuard]},
      { path: 'internships', component: InternshipListComponent, canActivate: [StudentGuard]},
      { path: 'recommendations', component: RecommendationsComponent, canActivate: [StudentGuard]},
      { path: 'worklogs', component: WorklogComponent, canActivate: [StudentGuard]}
    ]
  },

  { path: 'unauthorized', component: UnauthorizedComponent},

  { path: '**', redirectTo: '' } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}