import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'receiver',
        loadChildren: () => import('./receiver/receiver.module').then(m => m.JnotifierReceiverModule)
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.JnotifierProductModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JnotifierEntityModule {}
