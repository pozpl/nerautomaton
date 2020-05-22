import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NerJobEditComponent } from './ner-job-edit.component';

describe('NerJobEditComponent', () => {
  let component: NerJobEditComponent;
  let fixture: ComponentFixture<NerJobEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NerJobEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NerJobEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
