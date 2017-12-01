import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { DetailsPage } from '../details/details';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { HttpParams } from '@angular/common/http/src/params';


interface Result {
  title: string;
  author: string;
  date: number;
  image: string;
}

const result: Result[]=[{  
  title:"Film1",
  author:"ezf",
  date:15,
  image:"ddd"
},{  
  title:"Film2",
  author:"ezf",
  date:15,
  image:"ddd"
}]
@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
  
})

export class HomePage {
  private api_key: string = 'ebb02613ce5a2ae58fde00f4db95a9c1';
  private url_base: string = 'https://api.themoviedb.org/3/search/movie';
  results: string[];
  searchInput: string= "";
  // Inject HttpClient into your component or service.

  searchresult:Result=result[1];
  items=result;
  pushPage: any;
  constructor(public navCtrl: NavController, private http: HttpClient) {}
  //changement de page
  private showDetails(){
    this.navCtrl.push(DetailsPage)
  }

  private searchEngine():Observable<Result[]>{
    // Make the HTTP request:
    return this.http.get<Result[]>(this.url_base,{
      params: new HttpParams().set('api_key',this.api_key).set('query',this.searchInput)
    }).pluck('results');
  }


  onInput($event){
  if(!this.searchInput){
      this.results=[];
      return;
    }
  else{
     this.results=this.searchEngine();
    }
  };
}

