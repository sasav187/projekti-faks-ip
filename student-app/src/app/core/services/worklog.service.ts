import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { WorkLog } from "../../shared/models/worklog.model";


@Injectable({
    providedIn: 'root'
})
export class WorkLogService {

    private api = 'http://localhost:8081/api/worklogs';
    private applicationsApi = 'http://localhost:8081/api/internship-applications';

    constructor(private http: HttpClient) { }

    getApplicationsByStudent() {
        return this.http.get<any[]>(`${this.applicationsApi}/my`);
    }

    getByApplication(applicationId: number, page = 0, size = 10) {
        return this.http.get<{ content: WorkLog[] }>(
            `${this.api}/application/${applicationId}?page=${page}&size=${size}`
        );
    }

    search(description: string, page = 0, size = 10) {
        return this.http.get<{ content: WorkLog[] }>(
            `${this.api}/search?description=${description}&page=${page}&size=${size}`
        );
    }

    create(log: WorkLog) {
        return this.http.post<WorkLog>(this.api, log);
    }

    update(id: number, log: WorkLog) {
        return this.http.put<WorkLog>(`${this.api}/${id}`, log);
    }

    delete(id: number) {
        return this.http.delete(`${this.api}/${id}`);
    }
}