import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export type Theme = 'light' | 'dark' | 'colorful';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private readonly THEME_KEY = 'selected-theme';
  private currentThemeSubject = new BehaviorSubject<Theme>(this.getStoredTheme());

  currentTheme$ = this.currentThemeSubject.asObservable();

  constructor() {
    this.applyTheme(this.currentThemeSubject.value);
  }

  private getStoredTheme(): Theme {
    return (localStorage.getItem(this.THEME_KEY) as Theme) || 'light';
  }

  setTheme(theme: Theme): void {
    localStorage.setItem(this.THEME_KEY, theme);
    this.currentThemeSubject.next(theme);
    this.applyTheme(theme);
  }

  private applyTheme(theme: Theme): void {
    document.documentElement.classList.remove('theme-light', 'theme-dark', 'theme-colorful');
    document.documentElement.classList.add(`theme-${theme}`);
  }
}