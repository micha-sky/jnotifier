export interface IReceiver {
  id?: string;
  uuid?: number;
  name?: string;
  email?: string;
  phone?: string;
  idId?: string;
}

export class Receiver implements IReceiver {
  constructor(
    public id?: string,
    public uuid?: number,
    public name?: string,
    public email?: string,
    public phone?: string,
    public idId?: string
  ) {}
}
