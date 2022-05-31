import { TestBed } from '@angular/core/testing';

import { UserNerTasksService } from './user-ner-tasks.service';

describe('UserNerTasksService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserNerTasksService = TestBed.get(UserNerTasksService);
    expect(service).toBeTruthy();
  });
});
