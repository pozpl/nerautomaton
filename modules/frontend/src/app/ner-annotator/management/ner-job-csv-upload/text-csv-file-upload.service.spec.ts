import { TestBed } from '@angular/core/testing';

import { TextCsvFileUploadService } from './text-csv-file-upload.service';

describe('FileUploadService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TextCsvFileUploadService = TestBed.get(TextCsvFileUploadService);
    expect(service).toBeTruthy();
  });
});
