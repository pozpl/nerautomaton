import {Component, OnInit} from '@angular/core';
import {UserNerTasksDataSource} from "./user-ner-tasks-data.source";
import {UserNerTasksService} from "./user-ner-tasks.service";
import {PageEvent} from "@angular/material/paginator";
import {Router} from "@angular/router";

@Component({
    selector: 'app-tasks-list',
    templateUrl: './tasks-list.component.html',
    styleUrls: ['./tasks-list.component.scss']
})
export class TasksListComponent implements OnInit {

    public dataSource: UserNerTasksDataSource;
    private page = 0;

    displayedColumns = ["name", "review", "continue"];

    constructor(private userNerTasksService: UserNerTasksService,
                private router: Router) {
        this.dataSource = new UserNerTasksDataSource(userNerTasksService);

    }

    ngOnInit() {
        this.dataSource.list(1);
    }

    public pageChanged(event: PageEvent) {
        this.page = event.pageIndex;
        this.dataSource.list(event.pageIndex);
    }

    public reviewTask(jobId: number){
         this.router.navigate(['ner/job/annotate/processed/', jobId]);
    }

    public continueTask(jobId: number){
        this.router.navigate(['ner/job/unprocessed/', jobId]);
    }

}
