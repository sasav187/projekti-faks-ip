export interface CV {

  id?: number;

  studentId?: number;

  firstName: string;
  lastName: string;

  email: string;
  phone: string;

  address: string;
  nationality: string;

  dateOfBirth: string;

  summary: string;

  imagePath?: string;

  createdAt?: string;
  lastUpdated?: string;

  skills: CVSkill[];
  interests: CVInterest[];
  educationList: Education[];
  workExperiences: WorkExperience[];
  languages: Language[];
  additionalInfos: AdditionalInfo[];
}

export interface CVSkill {
  id?: number;
  skillName: string;
}

export interface CVInterest {
  id?: number;
  interestName: string;
}

export interface Education {
  id?: number;
  institution: string;
  degree: string;
  startYear: number;
  endYear: number;
}

export interface WorkExperience {
  id?: number;
  company: string;
  position: string;
  startDate: string;
  endDate: string;
  description: string;
}

export interface Language {
  id?: number;
  language: string;
  level: string;
}

export interface AdditionalInfo {
  id?: number;
  content: string;
}