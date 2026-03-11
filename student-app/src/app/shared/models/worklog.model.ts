export interface WorkLog {
    id?: number;
    applicationId: number;
    weekNumber: number;
    description: string;
    createdAt?: string;
}