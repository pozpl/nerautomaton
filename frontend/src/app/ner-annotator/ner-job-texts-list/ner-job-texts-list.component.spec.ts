import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NerJobTextsListComponent } from './ner-job-texts-list.component';

describe('NerJobTextsListComponent', () => {
  let component: NerJobTextsListComponent;
  let fixture: ComponentFixture<NerJobTextsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NerJobTextsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NerJobTextsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
