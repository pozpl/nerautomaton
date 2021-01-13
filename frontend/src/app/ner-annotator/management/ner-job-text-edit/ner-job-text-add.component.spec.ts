import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NerJobTextAddComponent } from './ner-job-text-add.component';

describe('NerJobTextEditComponent', () => {
  let component: NerJobTextAddComponent;
  let fixture: ComponentFixture<NerJobTextAddComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NerJobTextAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NerJobTextAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
