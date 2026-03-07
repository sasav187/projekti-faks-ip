import { Component, OnInit } from '@angular/core';
import { CVService } from '../../core/services/cv.service';
import { CV, CVSkill, CVInterest, Education, WorkExperience, Language, AdditionalInfo } from '../../shared/models/cv.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cv',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cv.component.html'
})
export class CVComponent implements OnInit {

  cv: CV | null = null;
  loading = true;

  constructor(private cvService: CVService) {}

  ngOnInit(): void {
    this.loadCV();
  }

  loadCV(): void {
    this.cvService.getMyCV().subscribe({
      next: (res) => {
        this.cv = res;
        this.loading = false;
      },
      error: () => {
        // Kreiranje praznog CV-a ako ne postoji
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
        this.loading = false;
      }
    });
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

  // --- Dodavanje i uklanjanje polja ---
  addSkill() { this.cv?.skills.push({ skillName: '' }); }
  removeSkill(index: number) { this.cv?.skills.splice(index, 1); }

  addInterest() { this.cv?.interests.push({ interestName: '' }); }
  removeInterest(index: number) { this.cv?.interests.splice(index, 1); }

  addEducation() { 
    this.cv?.educationList.push({ institution: '', degree: '', startYear: new Date().getFullYear(), endYear: new Date().getFullYear() });
  }
  removeEducation(index: number) { this.cv?.educationList.splice(index, 1); }

  addWorkExperience() {
    this.cv?.workExperiences.push({ company: '', position: '', startDate: '', endDate: '', description: '' });
  }
  removeWorkExperience(index: number) { this.cv?.workExperiences.splice(index, 1); }

  addLanguage() { this.cv?.languages.push({ language: '', level: '' }); }
  removeLanguage(index: number) { this.cv?.languages.splice(index, 1); }

  addAdditionalInfo() { this.cv?.additionalInfos.push({ content: '' }); }
  removeAdditionalInfo(index: number) { this.cv?.additionalInfos.splice(index, 1); }

}