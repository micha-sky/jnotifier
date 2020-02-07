import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JnotifierTestModule } from '../../../test.module';
import { ReceiverUpdateComponent } from 'app/entities/receiver/receiver-update.component';
import { ReceiverService } from 'app/entities/receiver/receiver.service';
import { Receiver } from 'app/shared/model/receiver.model';

describe('Component Tests', () => {
  describe('Receiver Management Update Component', () => {
    let comp: ReceiverUpdateComponent;
    let fixture: ComponentFixture<ReceiverUpdateComponent>;
    let service: ReceiverService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JnotifierTestModule],
        declarations: [ReceiverUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReceiverUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReceiverUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReceiverService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Receiver('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Receiver();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
