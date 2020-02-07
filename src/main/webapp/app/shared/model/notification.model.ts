export interface INotification {
  id?: string;
  uuid?: number;
  name?: string;
  url?: string;
  email?: string;
  phone?: string;
  status?: string;
}

export class Notification implements INotification {
  constructor(
    public id?: string,
    public uuid?: number,
    public name?: string,
    public url?: string,
    public email?: string,
    public phone?: string,
    public status?: string
  ) {}
}
