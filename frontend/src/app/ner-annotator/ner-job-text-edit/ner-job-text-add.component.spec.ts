import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NerJobTextAddComponent } from './ner-job-text-add.component';

describe('NerJobTextEditComponent', () => {
  let component: NerJobTextAddComponent;
  let fixture: ComponentFixture<NerJobTextAddComponent>;

  beforeEach(async(() => {
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
