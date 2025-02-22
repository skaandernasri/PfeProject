import { Routes } from '@angular/router';

export const USER_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./layouts/user-layout/user-layout.component')
      .then(m => m.UserLayoutComponent),
    children: [
      {
        path: '',
        loadComponent: () => import('./home/home.component')
          .then(m => m.HomeComponent)
      },
      {
        path: 'products',
        loadComponent: () => import('./products/products.component')
          .then(m => m.ProductsComponent)
      },
      {
        path: 'cart',
        loadComponent: () => import('./cart/cart.component')
          .then(m => m.CartComponent)
      },
      {
        path: 'checkout',
        loadComponent: () => import('./checkout/checkout.component')
          .then(m => m.CheckoutComponent)
      },
      {
        path: 'profile',
        loadComponent: () => import('./profile/profile.component')
          .then(m => m.ProfileComponent)
      },
      {
        path: 'blog',
        loadComponent: () => import('./blog/blog.component')
          .then(m => m.BlogComponent)
      },
      {
        path: 'blog/:id',
        loadComponent: () => import('./blog/blog-post/blog-post.component')
          .then(m => m.BlogPostComponent)
      }
    ]
  }
];