import {NerJobDto} from "../../management/ner-jobs/ner-job.dto";

export interface UserNerTaskDescriptionDto {
    job: NerJobDto;
    processed: number;
    unprocessed: number;
}
