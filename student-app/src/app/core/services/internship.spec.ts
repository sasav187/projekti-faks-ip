import { TestBed } from '@angular/core/testing';

import { InternshipService } from './internship.service';

describe('Internship', () => {
  let service: InternshipService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InternshipService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
