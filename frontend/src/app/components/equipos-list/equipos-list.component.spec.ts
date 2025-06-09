import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquiposListComponent } from './equipos-list.component';

describe('EquiposListComponent', () => {
  let component: EquiposListComponent;
  let fixture: ComponentFixture<EquiposListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EquiposListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EquiposListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
