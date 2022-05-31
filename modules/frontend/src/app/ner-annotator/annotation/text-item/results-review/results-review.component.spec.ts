import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ResultsReviewComponent } from './results-review.component';

describe('ResultsReviewComponent', () => {
  let component: ResultsReviewComponent;
  let fixture: ComponentFixture<ResultsReviewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ResultsReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultsReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
