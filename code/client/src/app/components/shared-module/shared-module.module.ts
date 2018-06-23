import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { LoadingComponent } from './loading/loading.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [
      LoadingComponent
  ],
  exports: [
      LoadingComponent,
      CommonModule,
      FormsModule
  ]
})
export class SharedModule { }
