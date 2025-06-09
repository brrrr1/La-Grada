import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipoDetalleComponent } from './equipo-detalle.component';

describe('EquipoDetalleComponent', () => {
  let component: EquipoDetalleComponent;
  let fixture: ComponentFixture<EquipoDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EquipoDetalleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EquipoDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
