<html><body>
<?php
//require_once("tp2-helpers.php"); 
// A simple web site in Cloud9 that runs through Apache
// Press the 'Run' button on the top to start the web server,
// then click the URL that is emitted to the Output tab of the console





/*  return a standard accesspoint structure
    @return array $accesspoint
*/



function initAccesspoint($name,$adr,$lon,$lat) {
  return array(
      'name' => $name, //string
      'adr' => $adr,  //string
      'lon' => $lon,  //float, in decimal degrees
      'lat' => $lat   //float, in decimal degrees
     );  
}


function distance ($p, $q) { //returns meter
  $scale = 10000000 / 90; // longueur d'un degré le long d'un méridien
  $a = ($p['lon'] - $q['lon']);
  $b = (cos($p['lat']/180.0*M_PI) * ($p['lat'] - $q['lat']));
  $res = $scale * sqrt(pow($a,2) + pow($b,2) );
  return $res;
}



function arrasize($array){
    foreach ($array as $value) {
        $i++;
    }
    return $i;
}

function placeAccess($lon,$lat,$csv,$around){
    $res = array();
    $i=0;
    $p = initAccesspoint('place','non',$lon,$lat);
    foreach ($csv as $value) {
        if(distance($p,$value)<=$around){
            $res[$i]=$value;
            $i++;
        }
    }
    return $res;
    
}

function cmp($a, $b) // sorting array on dist
{
    if ($a['dist'] == $b['dist']) {
        return 0;
    }
    return ($a['dist'] < $b['dist']) ? -1 : 1;
}


function apiWSTopAccess($lon,$lat,$csv,$n){
    $resint = array();
    $res = array();
    $i=0;
    $p = initAccesspoint('place','non',$lon,$lat);
    foreach ($csv as $value) {
            $value['dist']=distance($p,$value);
            $resint[$i]=$value;
            $i++;
    }
    usort($resint,cmp);   
    for($i=0;$i<$n;$i++){
     $res[$i]=$resint[$i];   
    }
    return $res;
}

function JSONParser($filename){
    $json= json_decode(file_get_contents($filename));
}



    $tab = file('borneswifi_EPSG4326.csv');
    $tabbis = JSONParser('borneswifi_EPSG4326.csv');
    var_dump($tabbis);
    //var_dump($tab);
    $finalcsvarray = array();
    $arraysize =0;
    $i=0;
    foreach ($tab as $row) {
        $csvline = str_getcsv($row, ",");
        $finalcsvarray[$i] = initAccesspoint($csvline{0},$csvline{1},$csvline{2},$csvline{3}); // ne se souvient plus comment concaténé deux array dynamiquement...
        $arraysize++;
        $i++;
    }
    
    //echo("il y a $i point d'accés");
    
    
    //TEST DES POINTS ACESS PAR GEO
    
    $grenette = placeAccess(5.72752, 45.19102, $finalcsvarray, 200);
    echo("les points d'accés pres de grenette sont :");
    $i=0;
    foreach ($grenette as $value) {
        $i++;
        echo($value['adr']);
        echo(" ; ");
    }
    
     //echo("il y a $i point d'accés pres de grenette");
     
     
     //TEST DU WEB SERVICE
     
     $res = apiWSTopAccess(5.72752, 45.19102, $finalcsvarray, 5);
     //var_dump(json_encode($res));
     var_dump($res);
     /*foreach ($res as $value) {
         echo($value['adr']);
         echo($value['dist']);
     }
     
    */
    
    echo("\n TEST de la requete via POST : \n\n");
    
    
    $lon = $_POST['longitude'];
    $lat = $_POST['latitude'];
    $num = $_POST['nbPoint'];
     $res = apiWSTopAccess($lon, $lat, $finalcsvarray, $num);
    var_dump($res);
    

    
    



    
    
?>
</body>
</html>