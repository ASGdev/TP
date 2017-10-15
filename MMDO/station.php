<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="style.css" />
	<title>Station</title>
</head>
<body>
<?php
include("function_alias.php");
include("precision.php"); 


$indice_station=[33,34,72,73,98,100,110,118,120,131];

//Remplit l'array des données de chaque station
function fill_array($indice_station){
	
	$liste_station=null;
	for ($i=0; $i <=9 ; $i++) { 
		$api_url = 'http://romma.fr/releves_romma_xml.php?id=e50aa81fbd8e831dae&station='.$indice_station[$i];
		$api_content = file_get_contents($api_url);
		$liste_station[$i]= json_decode($api_content,true);
	}
	return($liste_station);
}


 //Liste station+data
$liste_station=fill_array($indice_station);
//on recup le nom de la station



//REMPLIT info_station avec toutes les données d'une station
function fill_data_station($liste_station){
	$valide=0;
	$cite=null;
	$info_station=null;
	$station_name=null; //Nom rentré dans le formulaire
	if(isset($_GET['station']) AND !empty($_GET['station']) ){
		$station_name=$_GET['station'];
		

	}
	else{
		if(isset($_GET['nom']) AND !empty($_GET['nom']) ){
		$station_name=$_GET['nom'];
		}
		else
			return 0;
	}
 	foreach ($liste_station as $n => $s) {
 		//station correspond à celle entrée dans le formulaire
		if($s["station"]==$station_name){
			//print_r($s); POUR AFFICHER LE JSON
			$valide=1;
			$info_station=$s;
			break;
		}
	}
	//Mauvaise station entrée dans le formulaire
	if(!$valide){
		echo "la station n'existe pas";
		return(0);
	}
	return($info_station);
}
$info_station=fill_data_station($liste_station); 	
//AFFICHAGE EN Dynamique/statique
function affichage_tableaux($info_station){
	$city=null;
	$cite=geocodage($info_station);
	//on regarde si c'est un village ou une ville
	if($cite['address']!=null && !array_key_exists('city',$cite['address'])){
		$city=$cite['address']['village'];
	}
	else
		$city=$cite['address']['city'];
	
 // Vérifie qu'une station soit écrite
	if($info_station!=0){
		$arrayStatic = array('licence' =>$info_station['licence'],'id' =>$info_station['id'],'station' =>$info_station['station'],'altitude' =>$info_station['altitude'],'departement' =>$info_station['departement'],'latitude' =>$info_station['latitude'],'longitude' =>$info_station['longitude'] );
		$arrayDynamic = array('valide' =>$info_station['valide'],'date' =>$info_station['date'],'heure' =>$info_station['heure'],'temperature' =>$info_station['temperature'],'pluie' =>$info_station['pluie'],'humidite' =>$info_station['humidite'],'rayonnement' =>$info_station['rayonnement'],'vent_moyen_10' =>$info_station['vent_moyen_10'],'direction' =>$info_station['direction'],'rafale_maxi' =>$info_station['rafale_maxi'],'dewpoint' =>$info_station['rayonnement']);
		echo "<table style=\"width:100%\">";
		echo "<tr>";
		echo "<th>Données Dynamique</th>";
		echo "<th>Valeurs</th>"; 
	 	foreach($arrayDynamic as $key => $value)
		{
			echo "<tr>";
			echo "<td>";
		 	print_r($key);
		 	echo "</td>";
		 	echo "<td>";
			print_r($value);
			echo "</td>";
			echo "</tr>";
		}
		echo "</tr>";   
   		echo "</table>";	
   		echo "<table style=\"width:100%\">";
		echo "<tr>";
		echo "<th>Données Statique</th>";
		echo "<th>Valeurs</th>"; 
	 	foreach($arrayStatic as $key => $value)
		{
			echo "<tr>";
			echo "<td>";
		 	print_r($key);
		 	echo "</td>";
		 	echo "<td>";
			print_r($value);
			echo "</td>";
			echo "</tr>";
		}
		//AFFICHAGE DU NOM DE LA CITY
		echo "<tr>";
		echo "<td>";
		print_r("nom");
		echo "</td>";
	    echo "<td>";
		print_r($city);
		echo "</td>";
		echo "</tr>"; 
		//fin du tableau
   		echo "</tr>";   
   		echo "</table>";	
   	}
  	
}




//AFFICHAGE TABLEAUX SYNTHETIQUE
function affichage_synthetique($liste_station){
	$tab=minmax($liste_station);
	$liste_parametres=['station','latitude','longitude','valide','date','heure','temperature','pluie','humidite'];
	//affiche les paramètres(station,latitude...)
	for ($k=0; $k <9 ; $k++) {  
		
		echo "<td>";
		print_r($liste_parametres[$k]);
		echo "</td>";
	}	
	echo "<td>";
	echo "MAP";;
	echo "</td>";
	echo "<td>";
	echo "wiki";;
	echo "</td>";
	//Il y a 10 stations à parcourir
	for ($i=0; $i <10 ; $i++) {
		$city=null;
		$cite=geocodage($liste_station[$i]);
		//on regarde si c'est un village ou une ville
		if($cite['address']!=null && !array_key_exists('city',$cite['address'])){
		$city=$cite['address']['village'];
		}
		else
			$city=$cite['address']['city'];
		echo "</td>";
		echo "<tr>"; 
		// 9 paramètre par station à afficher		
		for ($j=0; $j <9 ; $j++) {
			$parametre=$liste_parametres[$j];
			if($liste_parametres[$j]=='station'){
				$nom=$liste_station[$i][$liste_parametres[$j]];
				echo "<td>";
				echo "<a href=\"meteo.php?nom=".$nom."\">";
				print_r($liste_station[$i][$parametre]);
				echo "</a>";
			}
			else{
				//COLORIE LE MIN/MAX
				coloriage($parametre,$liste_station,$i,$tab);			  	
			}
									
		}
	echo "<td>";
	affiche_map($liste_station,$i);	
	echo "</td>";
	echo "<td>";
	affiche_wikipedia($city);	
	echo "</td>";
	}

}
?>

</body>
</html>


