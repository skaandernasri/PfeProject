const defaultTheme = require('tailwindcss/defaultTheme')

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        // Light theme
        'light-primary': '#2563eb',
        'light-secondary': '#4f46e5',
        'light-background': '#ffffff',
        'light-surface': '#f3f4f6',
        'light-text': '#1f2937',
        
        // Dark theme
        'dark-primary': '#60a5fa',
        'dark-secondary': '#818cf8',
        'dark-background': '#111827',
        'dark-surface': '#1f2937',
        'dark-text': '#f9fafb',
        
        // Colorful theme
        'colorful-primary': '#f472b6',
        'colorful-secondary': '#fb923c',
        'colorful-background': '#fdf2f8',
        'colorful-surface': '#fce7f3',
        'colorful-text': '#831843',
      },
      fontFamily: {
        sans: ['Inter var', ...defaultTheme.fontFamily.sans],
      },
    },
  },
  plugins: [],
}