import { TestBed } from '@angular/core/testing';

import { NerLabelsAccessService } from './ner-labels-access.service';

describe('NerLabelsAccessService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NerLabelsAccessService = TestBed.get(NerLabelsAccessService);
    expect(service).toBeTruthy();
  });
});
