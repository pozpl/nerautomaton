import {NerJobDto} from "./ner-job.dto";

export class NerJobSaveStatus {
    nerJobDto: NerJobDto;

    status: string;

    errorCode: string;
}
