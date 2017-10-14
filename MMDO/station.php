<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="style.css" />
	<title>Station</title>
</head>
<body>
<?php
include("test.php"); 


$indice_station=[33,34,72,73,98,100,110,118,120,131];

//Remplit l'array des données de chaque station
function fill_array($indice_station){
	$liste_station=null;
	for ($i=0; $i <=9 ; $i++) { 
		$api_url = 'http://romma.fr/releves_romma_xml.php?id=e50aa81fbd8e831dae&station='.$indice_station[$i];
		$api_content = file_get_contents($api_url);
		$liste_station[$i]= json_decode($api_content,true);
	}
	//print_r($liste_station);
	return($liste_station);
}



$liste_station=fill_array($indice_station); //Liste station+data
//REMPLIT info_station avec toutes les données d'une station
function fill_data_station($liste_station){
	$valide=0;
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
	if($info_station!=0){ // Vérifie qu'une station soit écrite
	echo "<tr>";
	echo "<th>Données Fixe</th>";
	echo "<th>Valeurs</th>"; 
	 	foreach($info_station as $key => $value) // affiche tab
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
   	}
   	echo "</tr>";	
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
	//Il y a 10 stations à parcourir
	for ($i=0; $i <10 ; $i++) {
		echo "<tr>"; 
		// 9 paramètre par station à afficher		
		for ($j=0; $j <9 ; $j++) {
			$parametre=$liste_parametres[$j];
			if($liste_parametres[$j]=='station'){
				$nom=$liste_station[$i][$liste_parametres[$j]];
				echo "<td>";
				echo "<a href=\"station.php?nom=".$nom."\">";
				print_r($liste_station[$i][$parametre]);
				echo "</a>";
				echo "</td>";
			}
			else{
				//COLORIE LE MIN/MAX
				coloriage($parametre,$liste_station,$i,$tab);			  	
			}
									
		}
	echo "<td>";
	affiche_map($liste_station,$i);	
	echo "</td>";
	echo "</tr>";
	}

}
?>

</body>
</html>


