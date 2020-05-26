import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessedTextsListComponent } from './processed-texts-list.component';

describe('ProcessedTextsListComponent', () => {
  let component: ProcessedTextsListComponent;
  let fixture: ComponentFixture<ProcessedTextsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessedTextsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessedTextsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
