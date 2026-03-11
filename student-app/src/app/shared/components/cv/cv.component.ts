import { Component, OnInit } from '@angular/core'
import { FormsModule } from '@angular/forms'
import { CommonModule } from '@angular/common'

import { MaterialModule } from '../../../material.module'
import { CVService } from '../../../core/services/cv.service'
import { CV } from '../../models/cv.model'

@Component({
  selector: 'app-cv',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule
  ],
  templateUrl: './cv.component.html',
  styleUrls: ['./cv.component.scss']
})
export class CVComponent implements OnInit {

  cv!: CV
  isEditing = false
  hasCV = false

  skillLevels = ['BEGINNER', 'INTERMEDIATE', 'ADVANCED']
  languageLevels = ['A1', 'A2', 'B1', 'B2', 'C1', 'C2']

  constructor(private cvService: CVService) { }

  ngOnInit(): void {
    this.loadCV()
  }

  loadCV() {
    this.cvService.getMyCV().subscribe({
      next: res => {
        if (res && res.id) {
          this.cv = this.normalizeCV(res)
          this.hasCV = true
          this.isEditing = false
        } else {
          this.initNewCV()
        }
      },
      error: () => {
        this.initNewCV()
      }
    })
  }

  normalizeCV(res: CV): CV {
    return {
      ...res,
      skills: res.skills ?? [],
      interests: res.interests ?? [],
      educationList: res.educationList ?? [],
      workExperiences: res.workExperiences ?? [],
      languages: res.languages ?? [],
      additionalInfos: res.additionalInfos ?? []
    }
  }

  initNewCV() {
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
    }
    this.hasCV = false
    this.isEditing = true
  }

  createCV() {
    this.initNewCV()
  }

  startEdit() {
    if (!this.cv) this.initNewCV()
    this.isEditing = true
  }

  save() {
    this.cvService.saveCV(this.cv).subscribe(res => {
      this.cv = this.normalizeCV(res)
      this.hasCV = true
      this.isEditing = false
    })
  }

  addSkill() { this.cv.skills.push({ name: '', level: 'BEGINNER' }) }
  removeSkill(i: number) { this.cv.skills.splice(i, 1) }

  addInterest() { this.cv.interests.push({ name: '' }) }
  removeInterest(i: number) { this.cv.interests.splice(i, 1) }

  addEducation() {
    this.cv.educationList.push({
      institution: '',
      degree: '',
      fieldOfStudy: '',
      startDate: '',
      endDate: '',
      description: ''
    })
  }
  removeEducation(i: number) { this.cv.educationList.splice(i, 1) }

  addWorkExperience() {
    this.cv.workExperiences.push({
      companyName: '',
      position: '',
      startDate: '',
      endDate: '',
      description: ''
    })
  }
  removeWorkExperience(i: number) { this.cv.workExperiences.splice(i, 1) }

  addLanguage() {
    this.cv.languages.push({
      name: '',
      listeningLevel: '',
      readingLevel: '',
      writingLevel: '',
      spokenLevel: ''
    })
  }
  removeLanguage(i: number) { this.cv.languages.splice(i, 1) }

  addAdditionalInfo() { this.cv.additionalInfos.push({ content: '' }) }
  removeAdditionalInfo(i: number) { this.cv.additionalInfos.splice(i, 1) }

}