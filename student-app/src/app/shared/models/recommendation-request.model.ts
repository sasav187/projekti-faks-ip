export interface RecommendationRequestDTO {
  studentId: number;
  internshipId: number;
  score?: number;
  explanation?: string;
}