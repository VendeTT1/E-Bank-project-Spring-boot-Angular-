import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Customer} from '../customers/model/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  serverHost:String="http://localhost:8080"

  constructor(private http:HttpClient) { }

  public getCustomers():Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(this.serverHost+"/customers")
  }

  public searchCustomers(keyword : String){
    return this.http.get<Array<Customer>>(this.serverHost+"/customers/search?keyword="+keyword)
  }

  public saveCustomer(customer : Customer):Observable<Customer>{
    return this.http.post<Customer>(this.serverHost+"/addCustomer",customer);
  }

  public deleteCustomer(id : number){
    return this.http.delete(this.serverHost+"/deleteCustomer/"+ id);
  }

}
