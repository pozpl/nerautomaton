import {Injectable} from '@angular/core';
import {HttpClient, HttpEventType, HttpRequest, HttpResponse} from "@angular/common/http";
import {BehaviorSubject, Observable, of, Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class TextCsvFileUploadService {

    public static readonly CSV_UPLOAD_URL = '/ner/texts/csv/upload';
    public static readonly DOC_UPLOAD_URL = 'ner/texts/word-doc/upload';

    constructor(private http: HttpClient) {
    }

    public upload(files: Set<File>,
                  jobId: number):
        { [key: string]: { progress: Observable<number> , error: Observable<boolean>} } {

        // this will be the our resulting map
        const status: { [key: string]: { progress: Observable<number>, error: Observable<boolean> } } = {};

        files.forEach(file => {
            // create a new multipart-form for every file
            const formData: FormData = new FormData();
            formData.append('file', file, file.name);
            formData.append('jobId', jobId.toString());

            // create a http-post request and pass the form
            // tell it to report the upload progress
            const uploadUrl = this.resolveRequestFromFileExtension(file.name);
            const req = new HttpRequest('POST', uploadUrl, formData, {
                reportProgress: true
            });

            // create a new progress-subject for every file
            const progress = new Subject<number>();
            const errorSubj = new BehaviorSubject<boolean>(false);

            // send the http-request and subscribe for progress-updates
            this.http.request(req)
                .subscribe(event => {
                        if (event.type === HttpEventType.UploadProgress) {
                            const total = event.total || 1;
                            // calculate the progress percentage
                            const percentDone = Math.round(100 * event.loaded / total);

                            // pass the percentage into the progress-stream
                            progress.next(percentDone);
                        } else if (event instanceof HttpResponse) {

                            // Close the progress-stream if we get an answer form the API
                            // The upload is complete
                            progress.complete();
                            errorSubj.complete();
                        }
                    },
                    error => {
                        progress.next(100);
                        progress.complete()
                        errorSubj.next(true);
                        errorSubj.complete();
                    });

            // Save every progress-observable in a map of all observables
            status[file.name] = {
                progress: progress.asObservable(),
                error: errorSubj.asObservable()
            };
        });

        // return the map of progress.observables
        return status;
    }

    private resolveRequestFromFileExtension(fileName: string): string {
        if(fileName.endsWith(".csv")) {
            return TextCsvFileUploadService.CSV_UPLOAD_URL;
        }else if(fileName.endsWith('.doc') || fileName.endsWith('.docx')) {
            return TextCsvFileUploadService.DOC_UPLOAD_URL;
        }else{
            //AS a fallback assume that it's CSV for now
            return TextCsvFileUploadService.CSV_UPLOAD_URL;
        }
    }

}
