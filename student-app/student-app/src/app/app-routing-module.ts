import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes } from '@angular/router';
import { InternshipListComponent } from './features/components/internship-list/internship-list';

const routes: Routes = [
  { path: '', redirectTo: 'internships', pathMatch: 'full' },
  { path: 'internships', component: InternshipListComponent }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class AppRoutingModule { }
