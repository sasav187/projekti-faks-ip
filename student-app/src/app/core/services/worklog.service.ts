import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { WorkLog } from "../../shared/models/worklog.model";


@Injectable({
    providedIn: 'root'
})
export class WorkLogService {

    private api = 'http://localhost:8081/api/work-logs';

    constructor(private http: HttpClient) { }

    getAll(page = 0, size = 10) {
        return this.http.get<{ content: WorkLog[] }>(`${this.api}?page=${page}&size=${size}`);
    }

    search(description: string, page = 0, size = 10) {
        return this.http.get<{ content: WorkLog[] }>(`${this.api}/search?description=${description}&page=${page}&size=${size}`);
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