import { TestBed } from '@angular/core/testing';

import { InMemoryStorageService } from './in-memory-storage.service';

describe('InMemoryStorageService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InMemoryStorageService = TestBed.get(InMemoryStorageService);
    expect(service).toBeTruthy();
  });
});
