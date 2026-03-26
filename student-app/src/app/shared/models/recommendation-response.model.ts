export interface RecommendationResponseDTO {
    id: number;
    studentId: number;
    internshipId: number;
    score?: number;
    explanation: string;
    createdAt: string;
}