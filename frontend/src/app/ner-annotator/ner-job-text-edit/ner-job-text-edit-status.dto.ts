import {NerJobTextDto} from "../ner-job-texts-list/ner-job-text.dto";

export class NerJobTextSaveStatus {
    nerJobDto: NerJobTextDto;

    status: string;

    errorCode: string;
}
