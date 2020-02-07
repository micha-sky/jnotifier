import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceiver } from 'app/shared/model/receiver.model';

@Component({
  selector: 'jhi-receiver-detail',
  templateUrl: './receiver-detail.component.html'
})
export class ReceiverDetailComponent implements OnInit {
  receiver: IReceiver | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receiver }) => (this.receiver = receiver));
  }

  previousState(): void {
    window.history.back();
  }
}
