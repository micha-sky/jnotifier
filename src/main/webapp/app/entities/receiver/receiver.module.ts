import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JnotifierSharedModule } from 'app/shared/shared.module';
import { ReceiverComponent } from './receiver.component';
import { ReceiverDetailComponent } from './receiver-detail.component';
import { ReceiverUpdateComponent } from './receiver-update.component';
import { ReceiverDeleteDialogComponent } from './receiver-delete-dialog.component';
import { receiverRoute } from './receiver.route';

@NgModule({
  imports: [JnotifierSharedModule, RouterModule.forChild(receiverRoute)],
  declarations: [ReceiverComponent, ReceiverDetailComponent, ReceiverUpdateComponent, ReceiverDeleteDialogComponent],
  entryComponents: [ReceiverDeleteDialogComponent]
})
export class JnotifierReceiverModule {}
