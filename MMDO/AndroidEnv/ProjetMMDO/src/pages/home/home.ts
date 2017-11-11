import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

interface Results{
  title : string,
  author:string,
  date : number,
  image: string
}

interface searchResult{
  researchlist : Results[]
}

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  toto : string = "toto";

  Film : Results ={
    title :"Truc",
    author : "Bidule",
    date :15,
    image :"TrucNul"
  };

  result : searchResult = {researchlist : []};
  searchInput : string = "";

  constructor(public navCtrl: NavController) {
    for (let index = 0; index < 3; index++) {
      this.result.researchlist[index]=this.Film;
      
    }
  }

  onInput($event){
    console.log(this.searchInput);    
    this.searchInput = "troll";
  }

}
