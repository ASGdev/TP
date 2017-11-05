<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="style.css">
	<title></title>
</head>
<body>
<?php
require ("tp3-helpers.php");




//Question3 => TMDB
//on récupère le film avec l'id
function getfilm_id($id){
	$url='movie/'.$id;
	$json=tmdbget($url);
	$content=(json_decode($json,true));
}



function request_langue($id,$langue){
	$option["language"]=$langue;
	$json=tmdbget('movie/'.$id,$option);
	$content=(json_decode($json,true));
	return $content;
}

function search_film($film_name){
	$url_base = 'https://api.themoviedb.org/3/search/movie?api_key=ebb02613ce5a2ae58fde00f4db95a9c1&query=';
  //transforme les espaces en +
	$film_name=str_replace(' ','+',$film_name);
	$url_film=$url_base.$film_name;
    $api_content = file_get_contents($url_film);
    $info_film= json_decode($api_content,true);
    $indice=$info_film['total_results'];
    for ($i=0; $i<$indice  ; $i++) { 
      echo $i.':';
      foreach ($info_film['results'][$i] as $key => $value) {
        if ($key=='title' || $key=='id') {
          echo '<br>';
          print_r($value) ; 
        }
    	} 	
	} 
}

function getActors($id){
        $name_memory=null;
        $json = tmdbget('movie/'.$id."/credits");
        $list_actors=json_decode($json,true);
        for ($i=0; $i<count($list_actors['cast']);$i++) { 
          echo 'acteur '.$i.':';
          echo '<B>'.$list_actors['cast'][$i]['name'].'</B>'.'  dans le rôle de '.'<B>'.$list_actors['cast'][$i]['character'].'</B>';
          echo "<br>";
        } 
}



// recupère les data dans chaque langue
$request_FR=request_langue($_POST["film_id"],"fr");
$request_EN=request_langue($_POST["film_id"],"en");
$request_VO=request_langue($_POST["film_id"],$request_FR["original_language"]);
//récupère les data voulus dans chaque langue
$dataFR["title"] = $request_FR["title"];
$dataFR["original_title"] = $request_FR["original_title"];
$dataFR["tagline"] = $request_FR["tagline"];
$dataFR["overview"] = $request_FR["overview"];
$dataFR["poster_path"]=$request_FR["poster_path"];

$dataEN["title"] = $request_EN["title"];
$dataEN["original_title"] = $request_EN["original_title"];
$dataEN["tagline"] = $request_EN["tagline"];
$dataEN["overview"] = $request_EN["overview"];
$dataEN["poster_path"]=$request_EN["poster_path"];

$dataVO["title"] = $request_VO["title"];
$dataVO["original_title"] = $request_VO["original_title"];
$dataVO["tagline"] = $request_VO["tagline"];
$dataVO["overview"] = $request_VO["overview"];
$dataVO["poster_path"]=$request_VO["poster_path"];

//Affichage de la table du film
    echo "<table>";   
    echo "<tr>
    	
        <th> VO </th>
        <th> FR </th>
        <th> EN </th>
   	</tr>";
   foreach($dataFR as $key => $value){
   	echo "<tr>";
   	for ($i=0; $i <3 ; $i++) { 
   		if ($i==0) {
   			$data=$dataVO;
   		}
   		elseif ($i==1) {
   			$data=$dataFR;
   		}
   		else{
   			$data=$dataEN;
   		}
   		echo "<td>";
        if($key == "poster_path"){
                    echo "<img src= \"https://image.tmdb.org/t/p/w185".$data[$key]."\">";
                }else{
                    echo $key." : ".$data[$key];
                }
        echo "</td>";
   	}
     
        
	echo "</tr>";
    }
   
    echo "</table>"; 

echo " <h4> RECHERCHE DE FILM :</h4>  ";
search_film("Seigneur des anneaux");
echo "<br>";
echo " <h4> LISTE DES ACTEURS  : </h4>";
getActors($_POST["film_id"]);

?>
</body>
</html>
