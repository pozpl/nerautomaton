import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { TextItemComponent } from './text-item.component';

describe('TextItemComponent', () => {
  let component: TextItemComponent;
  let fixture: ComponentFixture<TextItemComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TextItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TextItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
