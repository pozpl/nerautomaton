import { TestBed } from '@angular/core/testing';

import { JobOwnResultsDownloadService } from './job-own-results-download.service';

describe('JobOwnResultsDownloadService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: JobOwnResultsDownloadService = TestBed.get(JobOwnResultsDownloadService);
    expect(service).toBeTruthy();
  });
});
