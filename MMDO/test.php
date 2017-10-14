
<?php
function affiche_map($liste_station,$i){
	if(isset($_GET['latitude']) AND !empty($_GET['latitude']) ){
		$latitude=$_GET['latitude'];
	}
	if(isset($_GET['longitude']) AND !empty($_GET['longitude']) ){
		$longitude=$_GET['longitude'];
	}
	$la=$liste_station[$i]['latitude'];
	$lo=$liste_station[$i]['longitude'];
	

		echo "<a href=\"http://www.openstreetmap.org/?mlat=".$la."&mlon=".$lo."#map=15/".$la."/".$lo."\">";
		echo "MAP";
		echo "</a>";
		
	
}

function coloriage($parametre,$liste_station,$i,$tab){
	if ($parametre=='humidite' && $tab[4]==$i) {
						echo "<td";
						echo " style=\"border: 1px solid blue;\"";
						echo ">";
						print_r($liste_station[$i][$parametre]);
						echo "</td>";
				}
				elseif ($parametre=='humidite' && $tab[5]==$i){
						echo "<td";
						echo " style=\"border: 1px solid red;\"";
						echo ">";
						print_r($liste_station[$i][$parametre]);
						echo "</td>";
					}
				elseif($parametre=='pluie' && $tab[0]==$i){
					echo "<td";
					echo " style=\"border: 1px solid blue;\"";
					echo ">";
					print_r($liste_station[$i][$parametre]);
					echo "</td>";
				}
				elseif($parametre=='pluie' && $tab[1]==$i){
					echo "<td";
					echo " style=\"border: 1px solid red;\"";
					echo ">";
					print_r($liste_station[$i][$parametre]);
					echo "</td>";
				}
				elseif($parametre=='temperature' && $tab[2]==$i){
					echo "<td";
					echo " style=\"border: 1px solid blue;\"";
					echo ">";
					print_r($liste_station[$i][$parametre]);
					echo "</td>";
				}
				elseif($parametre=='temperature' && $tab[3]==$i){
					echo "<td";
					echo " style=\"border: 1px solid red;\"";
					echo ">";
					print_r($liste_station[$i][$parametre]);
					echo "</td>";
				}
	else{
					echo "<td>";
					print_r($liste_station[$i][$parametre]);
					echo "</td>";
				}

}
// TRES SALE ET LONG... TROUVE LE MIN ET MAX DE TEMP, PLUIE 	ET HUMIDITE
function minmax($liste_station){
	//ON TROUVE LES MAX ET MIN
	
	$liste_parametres=['temperature','pluie','humidite'];
	$min_humidite=$liste_station[0]['humidite'];$max_humidite=$liste_station[0]['humidite'];$indice_humidite_min=0;$indice_humidite_max=0;
	$min_pluie=$liste_station[0]['pluie'];$max_pluie=$liste_station[0]['pluie'];$indice_pluie_min=0;$indice_pluie_max=0;
	$min_temperature=$liste_station[0]['temperature'];$max_temperature=$liste_station[0]['temperature'];$indice_temperature_min=0;$indice_temperature_max=0;
	for ($i=1; $i <10 ; $i++) { 
		//HUMIDITE
		$j=0;
		while ($j<3) {
			
			if($i!=7 && $liste_parametres[$j]=='humidite' && ($liste_station[$i]['humidite']<$min_humidite || $liste_station[$i]['humidite']>$max_humidite)){


			if ($liste_station[$i]['humidite']<$min_humidite ) {
				$min_humidite=$liste_station[$i]['humidite'];
				$indice_humidite_min=$i;
				
			}
			elseif($liste_station[$i]['humidite']>$max_humidite){
				$max_humidite=$liste_station[$i]['humidite'];
				$indice_humidite_max=$i;
			}
			else{}
				
		}
		elseif($i!=7 && $liste_parametres[$j]=='temperature' && ($liste_station[$i]['temperature']<$min_temperature || $liste_station[$i]['temperature']>$max_temperature)){
			if ($liste_station[$i]['temperature']<$min_temperature ) {
				$min_temperature=$liste_station[$i]['temperature'];
				$indice_temperature_min=$i;
			}

			elseif($liste_station[$i]['temperature']>$max_temperature){
				$max_temperature=$liste_station[$i]['temperature'];
				$indice_temperature_max=$i;

			}
			else{}
		}
		elseif($i!=7 && $liste_parametres[$j]=='pluie' && ($liste_station[$i]['pluie']<$min_pluie || $liste_station[$i]['pluie']>$max_pluie)){
			if ($liste_station[$i]['pluie']<$min_pluie ) {
				$min_pluie=$liste_station[$i]['pluie'];
				$indice_pluie_min=$i;
			}
			elseif($liste_station[$i]['pluie']>$max_pluie){
				$max_pluie=$liste_station[$i]['pluie'];
				$indice_pluie_max=$i;

			}
			else{}
		}
		else{}  
		$j++;
		}	
	}
	$tab_min_max=[$indice_pluie_min,$indice_pluie_max,$indice_temperature_min,$indice_temperature_max,$indice_humidite_min,$indice_humidite_max];
	return($tab_min_max);		
}
?>
