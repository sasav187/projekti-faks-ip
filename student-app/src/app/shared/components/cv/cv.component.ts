import { Component, OnInit } from '@angular/core'
import { FormsModule } from '@angular/forms'
import { CommonModule } from '@angular/common'
import { ChangeDetectorRef } from '@angular/core'

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
  originalCV!: CV

  isEditing = false
  hasCV = false

  selectedImage: File | null = null

  skillLevels = ['BEGINNER', 'INTERMEDIATE', 'ADVANCED']
  languageLevels = ['A1', 'A2', 'B1', 'B2', 'C1', 'C2']

  constructor(
    private cvService: CVService,
    private cd: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadCV()
  }

  onImageSelected(event: any) {
    const file = event.target.files[0]
    if (file) {
      this.selectedImage = file
    }
  }

  loadCV() {
    this.cvService.getMyCV().subscribe({
      next: res => {

        if (res && res.id && this.isCVFilled(res)) {

          this.cv = this.normalizeCV(res)
          this.originalCV = JSON.parse(JSON.stringify(this.cv))

          this.hasCV = true
          this.isEditing = false

        } else {
          this.initNewCV()
        }

        this.cd.detectChanges()

      },
      error: () => {
        this.initNewCV()
        this.cd.detectChanges()
      }
    })
  }

  isCVFilled(cv: CV): boolean {
    return !!(
      cv.firstName ||
      cv.lastName ||
      cv.email ||
      cv.phone ||
      cv.summary ||
      (cv.skills && cv.skills.length > 0) ||
      (cv.educationList && cv.educationList.length > 0)
    )
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

    this.cv = JSON.parse(JSON.stringify(this.originalCV))
    this.isEditing = true

  }

  cancelEdit() {

    this.cv = JSON.parse(JSON.stringify(this.originalCV))
    this.selectedImage = null
    this.isEditing = false

  }

  save() {

    this.cvService.saveCV(this.cv).subscribe(res => {

      this.cv = this.normalizeCV(res)
      this.originalCV = JSON.parse(JSON.stringify(this.cv))
      this.hasCV = true

      if (this.selectedImage && this.cv.id) {

        this.cvService.uploadImage(this.cv.id, this.selectedImage)
          .subscribe(() => {

            this.selectedImage = null
            this.loadCV()

          })

      }

      this.isEditing = false

    })

  }

  trackByIndex(index: number) {
    return index
  }

  addSkill() {
    this.cv.skills.push({ name: '', level: 'BEGINNER' })
  }

  removeSkill(i: number) {
    this.cv.skills.splice(i, 1)
  }

  addInterest() {
    this.cv.interests.push({ name: '' })
  }

  removeInterest(i: number) {
    this.cv.interests.splice(i, 1)
  }

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

  removeEducation(i: number) {
    this.cv.educationList.splice(i, 1)
  }

  addWorkExperience() {
    this.cv.workExperiences.push({
      companyName: '',
      position: '',
      startDate: '',
      endDate: '',
      description: ''
    })
  }

  removeWorkExperience(i: number) {
    this.cv.workExperiences.splice(i, 1)
  }

  addLanguage() {
    this.cv.languages.push({
      name: '',
      listeningLevel: '',
      readingLevel: '',
      writingLevel: '',
      spokenLevel: ''
    })
  }

  removeLanguage(i: number) {
    this.cv.languages.splice(i, 1)
  }

  addAdditionalInfo() {
    this.cv.additionalInfos.push({ content: '' })
  }

  removeAdditionalInfo(i: number) {
    this.cv.additionalInfos.splice(i, 1)
  }

}