import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultsReviewComponent } from './results-review.component';

describe('ResultsReviewComponent', () => {
  let component: ResultsReviewComponent;
  let fixture: ComponentFixture<ResultsReviewComponent>;

  beforeEach(async(() => {
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
