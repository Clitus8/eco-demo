import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';


export const BASIC_URL = "http://localhost:8080/"

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

private readonly endpoints = {
  products: `${BASIC_URL}api/admin/products`,
  product: (id: number) => `${BASIC_URL}api/admin/product/${id}`,
  search: (name: string) => `${BASIC_URL}api/admin/search/${name}`,
  categories: `${BASIC_URL}api/admin/categories`,
  addProduct: `${BASIC_URL}api/admin/product`,
  deleteProduct: (id: number) => `${BASIC_URL}api/admin/product/${id}`,
};

getAllProducts(): Observable<any> {
  return this.http.get(this.endpoints.products, {
      headers: this.createAuthorizationHeader()
  });
}

getProductById(productId: any): Observable<any> {
  return this.http.get(this.endpoints.product(productId), {
      headers: this.createAuthorizationHeader()
  });
}

getAllProductsByName(name: any): Observable<any> {
  return this.http.get(this.endpoints.search(name), {
      headers: this.createAuthorizationHeader()
  });
}

addProduct(productDto: any): Observable<any> {
  return this.http.post(this.endpoints.addProduct, productDto, {
      headers: this.createAuthorizationHeader()
  });
}

deleteProduct(productId: any): Observable<any> {
  return this.http.delete(this.endpoints.deleteProduct(productId), {
      headers: this.createAuthorizationHeader()
  });
}


  getAllCategories(): Observable<any> {
    return this.http.get(`${BASIC_URL}api/admin/categories`, {headers: this.createAuthorizationHeader()})
}



  // POST METHOD

  addCategory(categoryDto: any): Observable<any> {
    return this.http.post(BASIC_URL + `api/admin/category`, categoryDto,{headers: this.createAuthorizationHeader()})    
  }


  // PUT METHOD

  updateProduct(productId: number, productDto: any): Observable<any> {
    return this.http.put(BASIC_URL + `api/admin/${productId}`, productDto,{headers: this.createAuthorizationHeader()})

  }

   private createAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders().set(
      'Authorization', 'Bearer' + UserStorageService.getToken()
    )
  }
}
