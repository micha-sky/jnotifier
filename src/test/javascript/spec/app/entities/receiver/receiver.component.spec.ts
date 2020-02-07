import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JnotifierTestModule } from '../../../test.module';
import { ReceiverComponent } from 'app/entities/receiver/receiver.component';
import { ReceiverService } from 'app/entities/receiver/receiver.service';
import { Receiver } from 'app/shared/model/receiver.model';

describe('Component Tests', () => {
  describe('Receiver Management Component', () => {
    let comp: ReceiverComponent;
    let fixture: ComponentFixture<ReceiverComponent>;
    let service: ReceiverService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JnotifierTestModule],
        declarations: [ReceiverComponent]
      })
        .overrideTemplate(ReceiverComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReceiverComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReceiverService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Receiver('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.receivers && comp.receivers[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
