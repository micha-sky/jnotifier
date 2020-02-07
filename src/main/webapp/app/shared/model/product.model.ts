import { IReceiver } from 'app/shared/model/receiver.model';

export interface IProduct {
  id?: string;
  uuid?: string;
  name?: string;
  url?: string;
  uuids?: IReceiver[];
}

export class Product implements IProduct {
  constructor(public id?: string, public uuid?: string, public name?: string, public url?: string, public uuids?: IReceiver[]) {}
}
