import { IReceiver } from 'app/shared/model/receiver.model';

export interface IProduct {
  id?: string;
  name?: string;
  url?: string;
  ids?: IReceiver[];
}

export class Product implements IProduct {
  constructor(public id?: string, public name?: string, public url?: string, public ids?: IReceiver[]) {}
}
