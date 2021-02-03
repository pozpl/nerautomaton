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
    @Input() jobName?: string;

    constructor(private downloadService: JobOwnResultsDownloadService) { }

    ngOnInit() {
    }

    download(): void {
        this.downloadService.downloadBLIOU(this.jobId)
            .subscribe(blob => {
                if(this.jobName){
                    saveAs(blob, this.prepareFileNameFromJobName(this.jobName) + '.txt');
                }else{
                    saveAs(blob, 'annotation-result.txt');
                }
            });
    }

    downloadJson(): void {
        this.downloadService.downloadSpacyJson(this.jobId)
            .subscribe(blob => {
                if(this.jobName){
                    saveAs(blob, this.prepareFileNameFromJobName(this.jobName) + '.json');
                }else{
                    saveAs(blob, 'annotation-result.json');
                }
            });
    }

    private prepareFileNameFromJobName(jobName: string) {
        return jobName.replace(" ", '_');
    }

}
