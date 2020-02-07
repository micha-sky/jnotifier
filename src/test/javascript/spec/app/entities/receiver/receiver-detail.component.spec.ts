import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JnotifierTestModule } from '../../../test.module';
import { ReceiverDetailComponent } from 'app/entities/receiver/receiver-detail.component';
import { Receiver } from 'app/shared/model/receiver.model';

describe('Component Tests', () => {
  describe('Receiver Management Detail Component', () => {
    let comp: ReceiverDetailComponent;
    let fixture: ComponentFixture<ReceiverDetailComponent>;
    const route = ({ data: of({ receiver: new Receiver('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JnotifierTestModule],
        declarations: [ReceiverDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReceiverDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReceiverDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load receiver on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.receiver).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
