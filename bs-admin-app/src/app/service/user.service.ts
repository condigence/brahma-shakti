import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user.model';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Profile } from '../model/profile.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {}

  
  private nodeBaseUrl = 'http://localhost:3000/neerseva/api/v1';

  private usersAPI = 'http://localhost:3000/neerseva/api/v1';

    ///// Brahma Shakti APIS Starts here 

  private baseUrl = environment.BS_API_URL;


  generateOTP(user: User): Observable<User> {
    return this.http.post<any>(this.baseUrl, user);
  }

  validateOTP(user: User): Observable<User> {
    return this.http.post<any>(this.baseUrl+'validate-otp', user);
  }

  getUserByContact(contact: any): Observable<User> {
    return this.http.get<User>(this.baseUrl + 'api/bs-user/one-by-contact/'+contact);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl+'api/bs-user/users');
  }

  updateUser(user: User) {    
    return this.http.put(this.baseUrl+'api/bs-user/update', user);
  }

  deleteUser(id: string) {
    return this.http.delete(this.baseUrl+'api/bs-user/' + id);
  }

  addUser(user: User) {
    return this.http.post(this.baseUrl+'api/bs-user/', user);
  }

  getUserById(id: any): Observable<User> {
    return this.http.get<User>(this.baseUrl +'api/bs-user/'+ id);
  }

    ///// Brahma Shakti APIS ends here 





















  register(user: User) {
    return this.http.post(this.usersAPI, user);
  }

  getAllUsersCount() {
    return this.http.get(this.nodeBaseUrl + '/usercounts');
  }

  getAllVendorCount() {
    return this.http.get(this.nodeBaseUrl + '/vendorscount');
  }
  
  getAllCustomerCount() {
    return this.http.get(this.nodeBaseUrl + '/customerscount');
  }

  getAllOrderCount() {
    return this.http.get(this.nodeBaseUrl + '/ordercount');
  }
  

}
