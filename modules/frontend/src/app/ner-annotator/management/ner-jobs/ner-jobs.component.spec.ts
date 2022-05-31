import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NerJobsComponent } from './ner-jobs.component';

describe('NerJobsComponent', () => {
  let component: NerJobsComponent;
  let fixture: ComponentFixture<NerJobsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NerJobsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NerJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
