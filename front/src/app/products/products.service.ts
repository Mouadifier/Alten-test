import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Product } from './product.class';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

    private static productslist: Product[] = null;
    private products$: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);
    private httpOptions = {
        headers: new HttpHeaders({ 
          'Access-Control-Allow-Origin':'*',
          'Content-Type': 'application/json'
        })}
    private backEndUrl = 'http://localhost:8080/products';

    constructor(private http: HttpClient) { }

    getProducts(): Observable<Product[]> {
        if( ! ProductsService.productslist )
        {
            this.http.get<any>(this.backEndUrl, this.httpOptions).subscribe(data => {
                ProductsService.productslist = data.results;
                
                this.products$.next(ProductsService.productslist);
            });
        }
        else
        {
            this.products$.next(ProductsService.productslist);
        }

        return this.products$;
    }

    create(prod: Product): Observable<Product[]> {

        this.http.post<any>(this.backEndUrl, prod, this.httpOptions).subscribe(data => {
            if(data.status == "Success"){
                ProductsService.productslist.push(prod);
            }
            this.products$.next(ProductsService.productslist);
        });
        
        return this.products$;
    }

    update(prod: Product): Observable<Product[]>{
        this.http.put<any>(this.backEndUrl + '/' + prod.id, prod, this.httpOptions).subscribe(data => {
            var updatedProductIndex = ProductsService.productslist.findIndex(element => element.id == prod.id)

            ProductsService.productslist[updatedProductIndex] = prod;

        });
        // ProductsService.productslist.forEach(element => {
        //     if(element.id == prod.id)
        //     {
        //         element.name = prod.name;
        //         element.category = prod.category;
        //         element.code = prod.code;
        //         element.description = prod.description;
        //         element.image = prod.image;
        //         element.inventoryStatus = prod.inventoryStatus;
        //         element.price = prod.price;
        //         element.quantity = prod.quantity;
        //         element.rating = prod.rating;
        //     }
        // });
        this.products$.next(ProductsService.productslist);

        return this.products$;
    }


    delete(id: number): Observable<Product[]>{
        
        this.http.delete<any>(this.backEndUrl + '/' + id, this.httpOptions).subscribe(data => {
            if(data.status == "Success"){
                ProductsService.productslist = ProductsService.productslist.filter(value => { return value.id !== id } );
            }
            this.products$.next(ProductsService.productslist);
        })

        return this.products$;
    }
}