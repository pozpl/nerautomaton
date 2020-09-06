import {Component, OnInit} from '@angular/core';
import {UserDto} from "../auth/user.dto.";
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

    user: UserDto | null = null;

    constructor(private tokenStorageService: TokenStorageService) {
    }


    ngOnInit(): void {
        this.user = this.tokenStorageService.getUser();
    }

}
