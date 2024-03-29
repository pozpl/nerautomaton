import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./auth/login/login.component";
import {NerJobsComponent} from "./ner-annotator/management/ner-jobs/ner-jobs.component";
import {NerJobEditComponent} from "./ner-annotator/management/ner-jobs/ner-job-edit/ner-job-edit.component";
import {TextItemComponent} from "./ner-annotator/annotation/text-item/text-item.component";
import {TasksListComponent} from "./ner-annotator/annotation/tasks-list/tasks-list.component";
import {ProcessedTextsListComponent} from "./ner-annotator/annotation/processed-texts-list/processed-texts-list.component";
import {UnprocessedAnnotationPageComponent} from "./ner-annotator/annotation/unprocessed-annotation-page/unprocessed-annotation-page.component";
import {ReviewAnnotationPageComponent} from "./ner-annotator/annotation/review-annotation-page/review-annotation-page.component";
import {RegisterComponent} from "./auth/register/register.component";


const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: 'dashboard'},
    {path: 'home', component: HomeComponent},
    {path: 'dashboard', component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'ner/jobs', component: NerJobsComponent},
    {path: 'ner/job/edit/', component: NerJobEditComponent},
    {path: 'ner/job/edit/:id', component: NerJobEditComponent},
    {path: 'ner/user/tasks', component: TasksListComponent},
    {path: 'ner/job/annotate/processed/:jobId', component: ProcessedTextsListComponent},
    {path: 'ner/job/annotate/review/:id', component: TasksListComponent},
    {path: 'ner/job/unprocessed/:jobId', component: UnprocessedAnnotationPageComponent},
    {path: 'ner/review/processed/text', component: ReviewAnnotationPageComponent},

    {path: 'ner/text-item', component: TextItemComponent}
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes, { useHash: true, relativeLinkResolution: 'legacy' })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
