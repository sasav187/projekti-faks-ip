import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';

import { CVService } from '../../core/services/cv.service';
import { CV, CVSkill, CVInterest, Education, WorkExperience, Language, AdditionalInfo } from '../../shared/models/cv.model';

@Component({
  selector: 'app-cv',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './cv.component.html'
})
export class CVComponent implements OnInit {

  cv: CV | null = null;
  loading = true;

  constructor(private cvService: CVService) { }

  ngOnInit(): void {
    this.loadCV();
  }

  loadCV(): void {
    this.cvService.getMyCV().subscribe({
      next: (res) => {
        if (res) {
          this.cv = res;
        } else {
          this.initNewCV();
        }
        this.loading = false;
      },
      error: () => {
        this.initNewCV();
        this.loading = false;
      }
    });
  }

  initNewCV(): void {
    this.cv = {
      studentId: 0,
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      address: '',
      nationality: '',
      dateOfBirth: '',
      summary: '',
      skills: [],
      interests: [],
      educationList: [],
      workExperiences: [],
      languages: [],
      additionalInfos: []
    };
  }

  save(): void {
    if (!this.cv) return;

    this.cvService.saveCV(this.cv).subscribe({
      next: (res) => {
        this.cv = res;
        alert(this.cv.id ? 'CV updated' : 'CV created');
      },
      error: (err) => {
        console.error(err);
        alert('Error saving CV');
      }
    });
  }

  // ======= SKILLS =======
  addSkill() { 
    this.cv?.skills.push({ name: '', level: 'BEGINNER' }); 
  }
  removeSkill(index: number) { this.cv?.skills.splice(index, 1); }

  // ======= INTERESTS =======
  addInterest() { 
    this.cv?.interests.push({ name: '' }); 
  }
  removeInterest(index: number) { this.cv?.interests.splice(index, 1); }

  // ======= EDUCATION =======
  addEducation() {
    this.cv?.educationList.push({
      institution: '',
      degree: '',
      fieldOfStudy: '',
      startDate: new Date().toISOString().split('T')[0],
      endDate: '',
      description: ''
    });
  }
  removeEducation(index: number) { this.cv?.educationList.splice(index, 1); }

  // ======= WORK EXPERIENCES =======
  addWorkExperience() {
    this.cv?.workExperiences.push({
      companyName: '',
      position: '',
      startDate: new Date().toISOString().split('T')[0],
      endDate: '',
      description: ''
    });
  }
  removeWorkExperience(index: number) { this.cv?.workExperiences.splice(index, 1); }

  // ======= LANGUAGES =======
  addLanguage() {
    this.cv?.languages.push({
      name: '',
      listeningLevel: '',
      readingLevel: '',
      writingLevel: '',
      spokenLevel: ''
    });
  }
  removeLanguage(index: number) { this.cv?.languages.splice(index, 1); }

  // ======= ADDITIONAL INFO =======
  addAdditionalInfo() {
    this.cv?.additionalInfos.push({ content: '' });
  }
  removeAdditionalInfo(index: number) { this.cv?.additionalInfos.splice(index, 1); }

}