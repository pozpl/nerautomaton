import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UsersListComponent} from "./users-page/users-list/users-list.component";
import {UserEditComponent} from "./users-page/user-edit/user-edit.component";
import {AdminGuard} from "../auth/admin.guard";



const routes: Routes = [
    {
        path: 'admin',
        canActivate: [AdminGuard],
        children: [
            {   path: 'users',   component: UsersListComponent   },
            {   path: 'user/edit',   component: UserEditComponent   },
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule { }
