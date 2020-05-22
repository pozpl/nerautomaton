import { TestBed } from '@angular/core/testing';

import { NerJobsService } from './ner-jobs.service';

describe('NerJobsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NerJobsService = TestBed.get(NerJobsService);
    expect(service).toBeTruthy();
  });
});
