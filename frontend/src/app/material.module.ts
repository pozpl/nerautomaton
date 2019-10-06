import {NgModule} from '@angular/core';
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatChipsModule } from "@angular/material/chips";
import { MatDialogModule } from "@angular/material/dialog";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatListModule } from "@angular/material/list";
import { MatMenuModule } from "@angular/material/menu";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatSelectModule } from "@angular/material/select";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatTableModule } from "@angular/material/table";
import { MatToolbarModule } from "@angular/material/toolbar";

@NgModule({
    imports: [
        MatButtonModule,
        MatCardModule,
        MatInputModule,
        MatListModule,
        MatToolbarModule,
        MatMenuModule,
        MatIconModule,
        MatSidenavModule,
        MatTableModule,
        MatDialogModule,
        MatInputModule,
        MatSelectModule,
        MatChipsModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
    ],
    exports: [
        MatButtonModule,
        MatCardModule,
        MatInputModule,
        MatListModule,
        MatToolbarModule,
        MatMenuModule,
        MatIconModule,
        MatSidenavModule,
        MatTableModule,
        MatDialogModule,
        MatInputModule,
        MatSelectModule,
        MatChipsModule,
        MatPaginatorModule,
        MatProgressSpinnerModule
    ]
})
export class MaterialModule {
}
