import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './features/auth/login/login.component.js';
import { MainLayoutComponent } from './layout/main-layout.component.js';
import { InternshipListComponent } from './features/internships/internship-list.component.js';

import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [

  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'internships', component: InternshipListComponent }
    ]
  },

  { path: '**', redirectTo: 'internships' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }