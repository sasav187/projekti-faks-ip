import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternshipListComponent } from './internship-list';

describe('InternshipList', () => {
  let component: InternshipListComponent;
  let fixture: ComponentFixture<InternshipListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InternshipListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InternshipListComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
