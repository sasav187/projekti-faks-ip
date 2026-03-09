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
  cvId?: number;
  name: string;
  level: string;
}

export interface CVInterest {
  id?: number;
  cvId?: number
  name: string;
}

export interface Education {
  id?: number;
  cvId?: number;
  institution: string;
  degree: string;
  fieldOfStudy: string;
  startDate: string;
  endDate: string | null;
  description: string;
}

export interface WorkExperience {
  id?: number;
  cvId?: number;
  companyName: string;
  position: string;
  startDate: string;
  endDate: string | null;
  description: string;
}

export interface Language {
  id?: number;
  cvId?: number;
  name: string;
  listeningLevel: string;
  readingLevel: string;
  writingLevel: string;
  spokenLevel: string;
}

export interface AdditionalInfo {
  id?: number;
  cvId?: number;
  content: string;
}

