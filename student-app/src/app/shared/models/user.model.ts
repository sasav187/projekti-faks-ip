export interface User {
    id: number;
    username: string;
    role: 'STUDENT' | 'FACULTY' | 'COMPANY';
}