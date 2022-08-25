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
    return this.http.put(this.baseUrl+'api/bs-profile/update', user);
  }


  // updateProfile(user: any) {    
  //   return this.http.put(this.baseUrl+'api/bs-profile/update', user);
  // }

  // getProfileByUserId(userId: any): Observable<Profile> {
  //   return this.http.get<Profile>(this.baseUrl + 'api/bs-profile/my-profile/'+userId);
  // }

    ///// Brahma Shakti APIS ends here 













  getUserById(id: any): Observable<User> {
    return this.http.get<User>(this.usersAPI + id);
  }

  deleteUser(id: string) {
    return this.http.delete(this.usersAPI + id);
  }

  addUser(user: User) {
    return this.http.post(this.usersAPI, user);
  }



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
