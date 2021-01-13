import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ReviewAnnotationPageComponent } from './review-annotation-page.component';

describe('ReviewAnnotationPageComponent', () => {
  let component: ReviewAnnotationPageComponent;
  let fixture: ComponentFixture<ReviewAnnotationPageComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewAnnotationPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewAnnotationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
