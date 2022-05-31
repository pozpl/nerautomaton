import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UsersPageModule} from "./users-page/users-page.module";
import {AdminRoutingModule} from "./admin-routing.module";



@NgModule({
  declarations: [],
  imports: [
      CommonModule,
      UsersPageModule,
      AdminRoutingModule
  ]
})
export class AdminModule { }
