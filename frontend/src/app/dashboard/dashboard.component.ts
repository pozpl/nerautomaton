import { Component, OnInit } from '@angular/core';
import {HeroService} from "../hero.service";
import {Hero} from "../heroes/model/hero";
import {DataService} from '../data/data.service';
import {DataSource} from "@angular/cdk/table";
import {Observable} from "rxjs";
import {Post} from "../Post";
import {AuthService} from "../auth.service";
import {MatDialog} from "@angular/material";
import {PostDialogComponent} from "../post-dialog/post-dialog.component";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  heroes: Hero[] = [];

  displayedColumns = ['date_posted', 'title', 'category', 'delete'];
  dataSource: PostDataSource;

  constructor(private heroService: HeroService,
              private dataService: DataService,
              public auth: AuthService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.getHeroes();
    this.dataSource = new PostDataSource(this.dataService);
  }

  getHeroes(): void {
    this.heroService.getHeroes()
      .subscribe(heroes => this.heroes = heroes.slice(1, 5));
  }

  deletePost(id) {
    if (this.auth.authenticated) {
      this.dataService.deletePost(id);
      this.dataSource = new PostDataSource(this.dataService);
    } else {
      alert('Login in Before');
    }
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(PostDialogComponent, {
      width: '600px',
      data: 'Add Post'
    });
    dialogRef.componentInstance.event.subscribe((result) => {
      this.dataService.addPost(result.data);
      this.dataSource = new PostDataSource(this.dataService);
    });
  }

}

export class PostDataSource extends DataSource<any> {
  constructor(private dataService: DataService) {
    super();
  }

  connect(): Observable<Post[]> {
    return this.dataService.getData();
  }

  disconnect() {
  }
}
