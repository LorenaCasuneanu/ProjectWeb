import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";



import {environment} from "../../environments/environment";
import {Image} from "../entities/image";



@Injectable({
  providedIn: 'root'
})
export class ImageService {

  private readonly url = environment.apiUrl  ;
  password: any;
  constructor(private http: HttpClient) {
  }

  public encryptImage(image: Image): Observable<any> {
    return this.http.post<string>(this.url, image, {observe: 'response'})
      .pipe(map((res: any) => res));
  }

}
