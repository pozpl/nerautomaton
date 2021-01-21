import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {flatMap, map, takeUntil} from "rxjs/operators";
import {ActivatedRoute, Router} from "@angular/router";
import {UserEditService} from "../user-edit.service";
import {Page} from "../../../shared-components/page";
import {UserEditDto} from "../user-edit.dto";
import {PageEvent} from "@angular/material/paginator";

@Component({
    selector: 'app-users-list',
    templateUrl: './users-list.component.html',
    styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnInit, OnDestroy {

    private unsubscribe: Subject<void> = new Subject();

    public pagedContent: Page<UserEditDto>;
    public page = 0;

    public displayedColumns = ['username', 'email', 'passwordResetCode', 'delete'];

    constructor(private userEditService: UserEditService,
                private router: Router,
                private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {
        this.activatedRoute.queryParamMap
            .pipe(
                takeUntil(this.unsubscribe),
                map(queryParams => {
                    this.page = Number(queryParams.get('page')) || 0;
                    return this.page;
                }),
                flatMap(page => {
                    return this.userEditService.list(page)
                })
            ).subscribe(users => {
            this.pagedContent = users;
        });
    }

    ngOnDestroy(): void {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }

    public pageChanged(event: PageEvent){
        this.page = event.pageIndex;
        this.router.navigate([], {
            queryParams: {
                page: this.page.toString()
            }
        });
    }

    public goToAddUser() {
        this.router.navigateByUrl("/admin/user/add/");
    }

    public goToEditUser(userId: number) {
        this.router.navigate(["/admin/user/edit/", userId])
    }

    public deleteUser(user: UserEditDto) {
        if(user.id != undefined) {
            this.userEditService.delete(user.id).subscribe(() => {
                if (this.pagedContent.content) {
                    this.pagedContent.content = this.pagedContent.content.filter(userDto => user.id !== userDto.id);
                }
            });
        }
    }

}
