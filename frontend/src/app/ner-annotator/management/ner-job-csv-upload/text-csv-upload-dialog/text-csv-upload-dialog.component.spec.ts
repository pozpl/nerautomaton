import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TextCsvUploadDialogComponent } from './text-csv-upload-dialog.component';

describe('TextCsvUploadDialogComponent', () => {
  let component: TextCsvUploadDialogComponent;
  let fixture: ComponentFixture<TextCsvUploadDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TextCsvUploadDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TextCsvUploadDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
