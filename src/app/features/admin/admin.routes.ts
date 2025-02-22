import { Routes } from '@angular/router';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./layouts/admin-layout/admin-layout.component')
      .then(m => m.AdminLayoutComponent),
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: 'dashboard',
        loadComponent: () => import('./dashboard/dashboard.component')
          .then(m => m.DashboardComponent)
      },
      {
        path: 'users',
        loadComponent: () => import('./users-management/users-management.component')
          .then(m => m.UsersManagementComponent)
      },
      {
        path: 'orders',
        loadComponent: () => import('./orders-management/orders-management.component')
          .then(m => m.OrdersManagementComponent)
      }
    ]
  }
];