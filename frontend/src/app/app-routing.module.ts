import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./auth/login/login.component";
import {NerJobsComponent} from "./ner-annotator/management/ner-jobs/ner-jobs.component";
import {NerJobEditComponent} from "./ner-annotator/management/ner-jobs/ner-job-edit/ner-job-edit.component";
import {TextItemComponent} from "./ner-annotator/annotation/text-item/text-item.component";
import {TasksListComponent} from "./ner-annotator/annotation/tasks-list/tasks-list.component";


const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: 'dashboard'},
    {path: 'home', component: HomeComponent},
    {path: 'dashboard', component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'ner/jobs', component: NerJobsComponent},
    {path: 'ner/job/edit/', component: NerJobEditComponent},
    {path: 'ner/job/edit/:id', component: NerJobEditComponent},
    {path: 'ner/user/tasks', component: TasksListComponent},
    {path: 'ner/text-item', component: TextItemComponent}
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes, {useHash: true})
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
