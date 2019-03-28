import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {combineAll} from "rxjs/operators";
import {HeroesComponent} from "./heroes/heroes.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {HeroDetailComponent} from "./hero-detail/hero-detail.component";
import {TextItemComponent} from "./ner-annotator/text-item/text-item.component";
import {NerAnnotatorModule} from "./ner-annotator/ner-annotator.module";
import {NerJobsComponent} from "./ner-annotator/ner-jobs/ner-jobs.component";

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard'},
  { path: 'home', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'heroes', component: HeroesComponent},
  { path: 'dashboard', component: NerJobsComponent},
  { path: 'detail/:id', component: HeroDetailComponent },
  { path: 'ner/text-item', component: TextItemComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {useHash: true}),
    NerAnnotatorModule
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
