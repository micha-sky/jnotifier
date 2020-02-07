import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReceiver } from 'app/shared/model/receiver.model';
import { ReceiverService } from './receiver.service';
import { ReceiverDeleteDialogComponent } from './receiver-delete-dialog.component';

@Component({
  selector: 'jhi-receiver',
  templateUrl: './receiver.component.html'
})
export class ReceiverComponent implements OnInit, OnDestroy {
  receivers?: IReceiver[];
  eventSubscriber?: Subscription;

  constructor(protected receiverService: ReceiverService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.receiverService.query().subscribe((res: HttpResponse<IReceiver[]>) => (this.receivers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReceivers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReceiver): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReceivers(): void {
    this.eventSubscriber = this.eventManager.subscribe('receiverListModification', () => this.loadAll());
  }

  delete(receiver: IReceiver): void {
    const modalRef = this.modalService.open(ReceiverDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.receiver = receiver;
  }
}
