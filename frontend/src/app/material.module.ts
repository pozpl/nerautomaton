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
  MatDialogModule
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
  ]
})
export class MaterialModule {}
