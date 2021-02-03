import {Component, Input, OnInit} from '@angular/core';
import {JobOwnResultsDownloadService} from "./job-own-results-download.service";
import { saveAs } from 'file-saver';
import {NerJobDto} from "../../ner-jobs/ner-job.dto";

@Component({
  selector: 'job-own-results-download',
  templateUrl: './job-own-results-download.component.html',
  styleUrls: ['./job-own-results-download.component.scss']
})
export class JobOwnResultsDownloadComponent implements OnInit {

    @Input() job: NerJobDto;

    constructor(private downloadService: JobOwnResultsDownloadService) { }

    ngOnInit() {
    }

    download(): void {
        if(this.job.id) {
            this.downloadService.downloadBLIOU(this.job.id)
                .subscribe(blob => {
                    if (this.job.name) {
                        saveAs(blob, this.prepareFileNameFromJobName(this.job.name) + '.txt');
                    } else {
                        saveAs(blob, 'annotation-result.txt');
                    }
                });
        }
    }

    downloadJson(): void {
        if(this.job) {
            this.downloadService.downloadSpacyJson(this.job.id)
                .subscribe(blob => {
                    if (this.job.name) {
                        saveAs(blob, this.prepareFileNameFromJobName(this.job.name) + '.json');
                    } else {
                        saveAs(blob, 'annotation-result.json');
                    }
                });
        }
    }

    private prepareFileNameFromJobName(jobName: string) {
        return jobName.replace(" ", '_');
    }

}
