import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReceiver, Receiver } from 'app/shared/model/receiver.model';
import { ReceiverService } from './receiver.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

@Component({
  selector: 'jhi-receiver-update',
  templateUrl: './receiver-update.component.html'
})
export class ReceiverUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    uuid: [],
    name: [],
    email: [],
    phone: [],
    idId: []
  });

  constructor(
    protected receiverService: ReceiverService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receiver }) => {
      this.updateForm(receiver);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(receiver: IReceiver): void {
    this.editForm.patchValue({
      id: receiver.id,
      uuid: receiver.uuid,
      name: receiver.name,
      email: receiver.email,
      phone: receiver.phone,
      idId: receiver.idId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const receiver = this.createFromForm();
    if (receiver.id !== undefined) {
      this.subscribeToSaveResponse(this.receiverService.update(receiver));
    } else {
      this.subscribeToSaveResponse(this.receiverService.create(receiver));
    }
  }

  private createFromForm(): IReceiver {
    return {
      ...new Receiver(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      idId: this.editForm.get(['idId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceiver>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IProduct): any {
    return item.id;
  }
}
