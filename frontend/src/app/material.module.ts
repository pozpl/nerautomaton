import {NgModule} from '@angular/core';
import {
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatSidenavModule,
    MatToolbarModule,
    MatTableModule,
    MatSelectModule,
    MatDialogModule,
    MatChipsModule
} from "@angular/material";

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
        MatChipsModule
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
        MatChipsModule
    ]
})
export class MaterialModule {
}
