import {Component, OnInit} from '@angular/core';
import {UserDto} from "../auth/user.dto.";
import {AuthService} from "../auth/auth.service";

@Component({
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

    user: UserDto | null = null;

    constructor(private authService: AuthService) {
    }


    ngOnInit(): void {
        // this.user = this.authService.getUser();
    }

}
