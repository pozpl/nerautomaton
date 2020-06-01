import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NerAnnotationPageComponent } from './ner-annotation-page.component';

describe('NerAnnotationPageComponent', () => {
  let component: NerAnnotationPageComponent;
  let fixture: ComponentFixture<NerAnnotationPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NerAnnotationPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NerAnnotationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
