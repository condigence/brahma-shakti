import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Product } from '../model/product.model';
import { environment } from 'src/environments/environment';
import { Item } from '../model/item.model';
import { BSProduct } from '../model/bs-product.model';



const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class BSProductService {

  constructor(private http: HttpClient) {}

  private baseUrl = environment.BS_API_URL;


  private itemUrl = environment.PRODUCT_API_URL;

  getAllProdcts() {
    return this.http.get<BSProduct[]>(this.baseUrl+'api/bs-products');
  }


  getProductById(id: number) {
    return this.http.get<Product>(this.baseUrl + '/' + id);
  }

  addProduct(product: BSProduct) {
    return this.http.post(this.itemUrl, product);
  }

  updateProduct(product: Product) {
    return this.http.put(this.baseUrl, product);
  }

  deleteProduct(id: number) {
    return this.http.delete(this.baseUrl + '/' + id);
  }
}
