import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {HeroesComponent} from "./heroes/heroes.component";
import {HeroDetailComponent} from "./hero-detail/hero-detail.component";
import {TextItemComponent} from "./ner-annotator/text-item/text-item.component";
import {NerJobsComponent} from "./ner-annotator/ner-jobs/ner-jobs.component";
import {NerJobEditComponent} from "./ner-annotator/ner-jobs/ner-job-edit/ner-job-edit.component";

const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: 'dashboard'},
    {path: 'home', component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'heroes', component: HeroesComponent},
    {path: 'ner/jobs', component: NerJobsComponent},
    {path: 'ner/jobs', component: NerJobsComponent},
    {path: 'ner/job/edit/:id?', component: NerJobEditComponent},
    {path: 'detail/:id', component: HeroDetailComponent},
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
