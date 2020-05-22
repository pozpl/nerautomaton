import { TestBed } from '@angular/core/testing';

import { NerAnnotationDataService } from './ner-annotation-data.service';

describe('NerAnnotationDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NerAnnotationDataService = TestBed.get(NerAnnotationDataService);
    expect(service).toBeTruthy();
  });
});
