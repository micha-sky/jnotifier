export interface IReceiver {
  id?: string;
  name?: string;
  email?: string;
  phone?: string;
  idId?: string;
}

export class Receiver implements IReceiver {
  constructor(public id?: string, public name?: string, public email?: string, public phone?: string, public idId?: string) {}
}
