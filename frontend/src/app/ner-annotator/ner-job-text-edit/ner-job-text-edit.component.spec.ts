import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NerJobTextEditComponent } from './ner-job-text-edit.component';

describe('NerJobTextEditComponent', () => {
  let component: NerJobTextEditComponent;
  let fixture: ComponentFixture<NerJobTextEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NerJobTextEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NerJobTextEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
