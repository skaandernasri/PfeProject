import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, ActivatedRoute } from '@angular/router';

interface BlogPost {
  id: number;
  title: string;
  date: string;
  content: string;
  image: string;
}

@Component({
  selector: 'app-blog-post',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './blog-post.component.html'
})
export class BlogPostComponent implements OnInit {
  post: BlogPost | undefined;

  private posts: BlogPost[] = [
    {
      id: 1,
      title: 'Latest Fashion Trends 2024',
      date: 'February 15, 2024',
      content: `Fashion is ever-evolving, and 2024 brings exciting new trends to the forefront. From sustainable materials to bold color choices, this year's fashion landscape is all about personal expression and conscious consumption.

      We're seeing a strong return to minimalist designs with a focus on quality materials and timeless cuts. Sustainable fashion continues to gain momentum, with more designers embracing eco-friendly practices and materials.

      Key trends include:
      - Oversized silhouettes with structured elements
      - Earth tones mixed with vibrant accents
      - Sustainable and recycled materials
      - Gender-neutral designs
      - Statement accessories with minimalist clothing`,
      image: 'https://images.unsplash.com/photo-1483985988355-763728e1935b?w=1000&q=80'
    },
    {
      id: 2,
      title: 'Sustainable Shopping Guide',
      date: 'February 12, 2024',
      content: `Making environmentally conscious shopping decisions is more important than ever. This guide will help you navigate the world of sustainable shopping and make choices that benefit both you and the planet.

      Understanding sustainable materials, production processes, and ethical practices is key to making informed decisions. We'll explore how to identify truly sustainable products and avoid greenwashing.

      Key points to consider:
      - Look for certified organic materials
      - Check for fair trade certifications
      - Research company sustainability practices
      - Consider second-hand and vintage options
      - Invest in quality over quantity`,
      image: 'https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=1000&q=80'
    },
    {
      id: 3,
      title: 'Home Decor Essentials',
      date: 'February 10, 2024',
      content: `Transform your living space into a haven of comfort and style with these essential home decor items. We'll explore how to create a cohesive look while maintaining functionality and personal touch.

      The key to great home decor is finding the right balance between aesthetics and practicality. Learn how to choose pieces that not only look good but also serve a purpose in your daily life.

      Essential elements to consider:
      - Statement lighting fixtures
      - Textured throw pillows and blankets
      - Indoor plants for natural elements
      - Artwork and wall decorations
      - Functional storage solutions`,
      image: 'https://images.unsplash.com/photo-1493663284031-b7e3aefcae8e?w=1000&q=80'
    }
  ];

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = +params['id'];
      this.post = this.posts.find(p => p.id === id);
    });
  }
}