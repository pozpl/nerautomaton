import {NerJobTextDto} from "../ner-job-texts-list/ner-job-text.dto";

export class NerJobTextEditStatusDto {

    jobTextDto: NerJobTextDto;

    status: string;

    errorCode: NerJobTextSaveErrorType;
}

type NerJobTextSaveErrorType = 'EMPTY_TEXT'
