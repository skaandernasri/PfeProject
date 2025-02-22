import { Component } from '@angular/core';
import { ThemeService, Theme } from '../../../core/services/theme.service';

@Component({
  selector: 'app-theme-selector',
  standalone: true,
  templateUrl: './theme-selector.component.html'
})
export class ThemeSelectorComponent {
  currentTheme: Theme = 'light';

  constructor(private themeService: ThemeService) {
    this.themeService.currentTheme$.subscribe(
      theme => this.currentTheme = theme
    );
  }

  onThemeChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    this.themeService.setTheme(select.value as Theme);
  }
}