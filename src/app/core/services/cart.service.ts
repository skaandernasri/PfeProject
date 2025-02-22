import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItemCountSubject = new BehaviorSubject<number>(0);
  cartItemCount$ = this.cartItemCountSubject.asObservable();

  constructor() {
    // Initialize with a sample value
    this.cartItemCountSubject.next(2);
  }

  updateCartCount(count: number): void {
    this.cartItemCountSubject.next(count);
  }
}