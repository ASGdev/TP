import { Component } from '@angular/core';
import { DetailsPage } from '../details/details';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';


export interface Result {
  title: string;
  author: string;
  date: number;
  image: string;
}

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
  
})
export class HomePage {

  private api_key: string = 'ebb02613ce5a2ae58fde00f4db95a9c1';
  private url_base: string = 'https://api.themoviedb.org/3/search/movie';
  results: Observable<Result[]>;
  searchInput: string= "";
  // Inject HttpClient into your component or service.
  constructor(private http: HttpClient) {}
  //changement de page avec paramètres
  aboutDetails=DetailsPage;

  private searchEngine():Observable<Result[]>{
    // Make the HTTP request:
    return this.http.get<Result[]>(this.url_base,{
      params: new HttpParams().set('api_key',this.api_key).set('query',this.searchInput)
    }).pluck('results');
  }


  onInput(){
  if(!this.searchInput){
      this.results = Observable.of([]);
    }
  else{ 
      this.results = this.searchEngine();
    }
  };
}

