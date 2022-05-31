import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UsersListComponent} from './users-list/users-list.component';
import {UserEditComponent} from './user-edit/user-edit.component';
import {MaterialModule} from "../../material.module";


@NgModule({
    declarations: [
        UsersListComponent,
        UserEditComponent
    ],
    imports: [
        CommonModule,
        MaterialModule,
    ]
})
export class UsersPageModule {
}
