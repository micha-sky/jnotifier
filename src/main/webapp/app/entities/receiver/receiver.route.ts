import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReceiver, Receiver } from 'app/shared/model/receiver.model';
import { ReceiverService } from './receiver.service';
import { ReceiverComponent } from './receiver.component';
import { ReceiverDetailComponent } from './receiver-detail.component';
import { ReceiverUpdateComponent } from './receiver-update.component';

@Injectable({ providedIn: 'root' })
export class ReceiverResolve implements Resolve<IReceiver> {
  constructor(private service: ReceiverService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReceiver> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((receiver: HttpResponse<Receiver>) => {
          if (receiver.body) {
            return of(receiver.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Receiver());
  }
}

export const receiverRoute: Routes = [
  {
    path: '',
    component: ReceiverComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Receivers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReceiverDetailComponent,
    resolve: {
      receiver: ReceiverResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Receivers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReceiverUpdateComponent,
    resolve: {
      receiver: ReceiverResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Receivers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReceiverUpdateComponent,
    resolve: {
      receiver: ReceiverResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Receivers'
    },
    canActivate: [UserRouteAccessService]
  }
];
