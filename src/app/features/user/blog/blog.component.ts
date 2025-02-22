import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

interface BlogPost {
  id: number;
  title: string;
  date: string;
  excerpt: string;
  image: string;
}

@Component({
  selector: 'app-blog',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './blog.component.html'
})
export class BlogComponent {
  blogPosts: BlogPost[] = [
    {
      id: 1,
      title: 'Latest Fashion Trends 2024',
      date: 'February 15, 2024',
      excerpt: 'Discover the hottest fashion trends that are making waves this season.',
      image: 'https://images.unsplash.com/photo-1483985988355-763728e1935b?w=500&q=80'
    },
    {
      id: 2,
      title: 'Sustainable Shopping Guide',
      date: 'February 12, 2024',
      excerpt: 'Learn how to make environmentally conscious shopping decisions.',
      image: 'https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=500&q=80'
    },
    {
      id: 3,
      title: 'Home Decor Essentials',
      date: 'February 10, 2024',
      excerpt: 'Transform your living space with these must-have decor items.',
      image: 'https://images.unsplash.com/photo-1493663284031-b7e3aefcae8e?w=500&q=80'
    }
  ];
}