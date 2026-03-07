import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './features/auth/login/login.component';
import { MainLayoutComponent } from './layout/main-layout.component';
import { InternshipListComponent } from './features/internships/internship-list.component';
import { CVComponent } from './features/cv/cv.component'; // dodano

import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'internships', component: InternshipListComponent },
      { path: 'cv', component: CVComponent } // ruta za CV
    ]
  },

  { path: '**', redirectTo: 'internships' } // fallback
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}