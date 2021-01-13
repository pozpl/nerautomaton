import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { UnprocessedAnnotationPageComponent } from './unprocessed-annotation-page.component';

describe('NerAnnotationPageComponent', () => {
  let component: UnprocessedAnnotationPageComponent;
  let fixture: ComponentFixture<UnprocessedAnnotationPageComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ UnprocessedAnnotationPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnprocessedAnnotationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
