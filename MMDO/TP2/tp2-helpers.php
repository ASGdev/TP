<?php

/* 
   @param $p array('lon'=>int, 'lat'=>int)
   @param $q array('lon'=>int, 'lat'=>int)
   @return int distance (approx.) in meters
*/
function distance ($p, $q) {
  $scale = 10000000 / 90; // longueur d'un degré le long d'un méridien
  $a = ($p['lon'] - $q['lon']);
  $b = (cos($p['lat']/180.0*M_PI) * ($p['lat'] - $q['lat']));
  $res = $scale * sqrt(pow($a,2) + pow($b,2) );
  return $res;
}


/*  return a standard accesspoint structure
    @return array $accesspoint
*/
function initAccesspoint() {
  return array(
      'name' => null, //string
      'adr' => null,  //string
      'lon' => null,  //float, in decimal degrees
      'lat' => null   //float, in decimal degrees
     );  
}

