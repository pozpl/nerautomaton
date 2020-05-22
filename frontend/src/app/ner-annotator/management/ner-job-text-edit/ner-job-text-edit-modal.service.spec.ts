import { TestBed } from '@angular/core/testing';

import { NerJobTextEditModalService } from './ner-job-text-edit-modal.service';

describe('NerJobTextEditModalService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NerJobTextEditModalService = TestBed.get(NerJobTextEditModalService);
    expect(service).toBeTruthy();
  });
});
