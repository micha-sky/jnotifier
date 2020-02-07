import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReceiver } from 'app/shared/model/receiver.model';
import { ReceiverService } from './receiver.service';

@Component({
  templateUrl: './receiver-delete-dialog.component.html'
})
export class ReceiverDeleteDialogComponent {
  receiver?: IReceiver;

  constructor(protected receiverService: ReceiverService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.receiverService.delete(id).subscribe(() => {
      this.eventManager.broadcast('receiverListModification');
      this.activeModal.close();
    });
  }
}
