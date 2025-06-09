import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipoCardComponent } from './equipo-card.component';

describe('EquipoCardComponent', () => {
  let component: EquipoCardComponent;
  let fixture: ComponentFixture<EquipoCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EquipoCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EquipoCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
