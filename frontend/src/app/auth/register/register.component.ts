import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth.service";
import {MatDialog} from "@angular/material/dialog";
import {RegistrationSuccessfulDialogComponent} from "./registration-successful-dialog.component";

@Component({
  selector: 'ner-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

    hidePassword = true;

    public form = {
        username: null,
        email: null,
        password: null
    };
    isSuccessful = false;
    isSignUpFailed = false;
    errorMessage = '';

    constructor(private authService: AuthService,
                private dialog: MatDialog) { }

    ngOnInit(): void {
    }

    onSubmit(): void {
        this.authService.register(this.form).subscribe(
            data => {
                this.isSuccessful = true;
                this.isSignUpFailed = false;
                this.openDialog();
            },
            err => {
                this.errorMessage = err.error.message;
                this.isSignUpFailed = true;
            }
        );
    }

    openDialog() {
        this.dialog.open(RegistrationSuccessfulDialogComponent);
    }

}
