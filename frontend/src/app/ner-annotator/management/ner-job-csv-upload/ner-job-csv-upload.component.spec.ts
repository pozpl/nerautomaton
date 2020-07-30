import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NerJobCsvUploadComponent } from './ner-job-csv-upload.component';

describe('NerJobCsvUploadComponent', () => {
  let component: NerJobCsvUploadComponent;
  let fixture: ComponentFixture<NerJobCsvUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NerJobCsvUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NerJobCsvUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
