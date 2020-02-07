import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReceiver } from 'app/shared/model/receiver.model';

type EntityResponseType = HttpResponse<IReceiver>;
type EntityArrayResponseType = HttpResponse<IReceiver[]>;

@Injectable({ providedIn: 'root' })
export class ReceiverService {
  public resourceUrl = SERVER_API_URL + 'api/receivers';

  constructor(protected http: HttpClient) {}

  create(receiver: IReceiver): Observable<EntityResponseType> {
    return this.http.post<IReceiver>(this.resourceUrl, receiver, { observe: 'response' });
  }

  update(receiver: IReceiver): Observable<EntityResponseType> {
    return this.http.put<IReceiver>(this.resourceUrl, receiver, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IReceiver>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReceiver[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
