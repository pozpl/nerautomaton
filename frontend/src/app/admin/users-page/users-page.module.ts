import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersListComponent } from './users-list/users-list.component';
import { UserEditComponent } from './user-edit/user-edit.component';



@NgModule({
  declarations: [
      UsersListComponent,
      UserEditComponent
  ],
  imports: [
    CommonModule
  ]
})
export class UsersPageModule { }
