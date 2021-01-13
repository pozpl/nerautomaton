import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { JobOwnResultsDownloadComponent } from './job-own-results-download.component';

describe('JobOwnResultsDownloadComponent', () => {
  let component: JobOwnResultsDownloadComponent;
  let fixture: ComponentFixture<JobOwnResultsDownloadComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ JobOwnResultsDownloadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JobOwnResultsDownloadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
