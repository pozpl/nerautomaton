import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { TextCsvUploadDialogComponent } from './text-csv-upload-dialog.component';

describe('TextCsvUploadDialogComponent', () => {
  let component: TextCsvUploadDialogComponent;
  let fixture: ComponentFixture<TextCsvUploadDialogComponent>;

  beforeEach(waitForAsync(() => {
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
