import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {UserNerTasksDataSource} from "./user-ner-tasks-data.source";
import {UserNerTasksService} from "./user-ner-tasks.service";
import {PageEvent} from "@angular/material/paginator";

@Component({
    selector: 'app-tasks-list',
    templateUrl: './tasks-list.component.html',
    styleUrls: ['./tasks-list.component.scss']
})
export class TasksListComponent implements OnInit {

    private unsubscribe: Subject<void>;

    private dataSource: UserNerTasksDataSource;
    private page = 1;

    displayedColumns = ["name", "review", "continue"];

    constructor(userNerTasksService: UserNerTasksService) {
        this.dataSource = new UserNerTasksDataSource(userNerTasksService);

    }

    ngOnInit() {
        this.dataSource.list(1);
    }

    public pageChanged(event: PageEvent) {
        this.page = event.pageIndex;
        this.dataSource.list(event.pageIndex);
    }



}
