import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MainHeaderComponent } from '../../../../shared/components/main-header/main-header.component';
import { ThemeSelectorComponent } from '../../theme-selector/theme-selector.component';
import { FooterComponent } from '../../../../shared/components/footer/footer.component';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [RouterOutlet, MainHeaderComponent, ThemeSelectorComponent, FooterComponent],
  templateUrl: './admin-layout.component.html'
})
export class AdminLayoutComponent {}