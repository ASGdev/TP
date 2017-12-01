import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { DetailsPage } from '../details/details';
import {HttpClientModule} from '@angular/common/http';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/pluck';

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
  url_base = 'https://api.themoviedb.org/3/search/movie?api_key=ebb02613ce5a2ae58fde00f4db95a9c1&query=';
  results: string[];
  searchInput: string= "";
  // Inject HttpClient into your component or service.

  searchresult:Result=result[1];
  items=result;
  pushPage: any;
  constructor(public navCtrl: NavController, private http: HttpClientModule) {}
  //changement de page
  private showDetails(){
    this.navCtrl.push(DetailsPage)
  }

  private searchEngine(req){
    // Make the HTTP request:
    this.http.get(this.url_base + encodeURI(req)).subscribe(data => {
      // Read the result field from the JSON response.
      this.results = data['results'];
    });
  }


  onInput($event){
  if(!this.searchInput || this.searchInput!='Film'){
      this.items=[];
      return;
    }
  else{
      this.items=result;
    }
  };
}

