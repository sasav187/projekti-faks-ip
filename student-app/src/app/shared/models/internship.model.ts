export interface Internship {
    id: number;
    companyId: number;
    companyName: string;
    title: string;
    description: string;
    period: string;
    conditions: string;
    capacity: number;
    createdAt: string | null;
}