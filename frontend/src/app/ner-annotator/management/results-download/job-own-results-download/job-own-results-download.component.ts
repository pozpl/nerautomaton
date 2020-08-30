import {Component, Input, OnInit} from '@angular/core';
import {JobOwnResultsDownloadService} from "./job-own-results-download.service";
import { saveAs } from 'file-saver';

@Component({
  selector: 'job-own-results-download',
  templateUrl: './job-own-results-download.component.html',
  styleUrls: ['./job-own-results-download.component.scss']
})
export class JobOwnResultsDownloadComponent implements OnInit {

    @Input() jobId: number;

    constructor(private downloadService: JobOwnResultsDownloadService) { }

    ngOnInit() {
    }

    download(): void {
        this.downloadService.downloadForOwner(this.jobId)
            .subscribe(blob => saveAs(blob, 'annotation-result.txt'));
    }

}
