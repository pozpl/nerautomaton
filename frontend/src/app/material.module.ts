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
    MatChipsModule,
    MatPaginatorModule, MatProgressSpinnerModule
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
