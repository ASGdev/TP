<html><body>
<?php

    function smartcurl($url) {
    $ch = curl_init();

	echo "$url\n";
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

    $output = curl_exec($ch);
    curl_close($ch);      

    return $output;
    }

    function tmdbget($urlcomponent, $params=null) {
    $apikey = 'ebb02613ce5a2ae58fde00f4db95a9c1';
    $apiprefix = 'http://api.themoviedb.org/3/movie';  //3rd API version
	$targeturl = $apiprefix . $urlcomponent . '?api_key=' . $apikey;
    $targeturl .= (isset($params) ? '&' . http_build_query($params) : '');
    return smartcurl($targeturl);
    }

    function getFromId($id){
         $json = tmdbget("/".$id);
         return json_decode($id,true);
    }
    function getFromIdCredits($id){
         $json = tmdbget("/".$id."/credits");
         return json_decode($json,true);
    }
    function getFromIdWDL($id,$lang){
        $associative["language"] = $lang;
         $json = tmdbget("/".$id,$associative);
         return json_decode($json,true);
    }
    function getFromIdWDLImage($id,$lang){
        $associative["include_image_language"]=$lang;
        $associative["language"] = $lang;
         $json = tmdbget("/".$id."/images",$associative);
         $json = json_decode($json,true);
         $url = "https://image.tmdb.org/t/p/".$json["posters"]["0"]["width"].$json["posters"]["0"]["file_path"]; 
         return $url;
    }
    
    function getCredits($id){
       $json = tmdbget("/".$id."/credits");
        $json = json_decode($json,true);
        return $json;
    }
    
    
    function wikiRequest($request){
        $baseurl="https://query.wikidata.org/sparql?query=SPARQL";
        $request=$baseurl.'&'."format=json".'&'.urlencode($request);
        return json_decode(smartcurl($request,true));
    }
    
    
    
    
    /*function getFromName($name){
        $associative["query"] = $name;
         $json = tmdbget($null,$associative);
         return json_decode($json,true);
    }*/ 
    //intérrogation par nom ne fonctionne pas...
    
    
   
   // var_dump(getFromIdWDLImage($_POST["filmid"],"fr"));
    $requestFR = getFromIdWDL($_POST["filmid"],"fr");
    $requestEN = getFromIdWDL($_POST["filmid"],"en");
    $requestVO = getFromIdWDL($_POST["filmid"],$requestFR["original_language"]);
    $dataFR["title"] = $requestFR["title"];
    $dataFR["original_title"] = $requestFR["original_title"];
    $dataFR["tagline"] = $requestFR["tagline"];
    $dataFR["overview"] = $requestFR["overview"];
    $dataFR["poster_path"] = $requestFR["poster_path"];
    $dataEN["title"] = $requestEN["title"];
    $dataEN["original_title"] = $requestEN["original_title"];
    $dataEN["tagline"] = $requestEN["tagline"];
    $dataEN["overview"] = $requestEN["overview"];
    $dataEN["poster_path"] = $requestEN["poster_path"];
    $dataVO["title"] = $requestVO["title"];
    $dataVO["original_title"] = $requestVO["original_title"];
    $dataVO["tagline"] = $requestVO["tagline"];
    $dataVO["overview"] = $requestVO["overview"];
    $dataVO["poster_path"] = $requestVO["poster_path"];
    
    $credits = getCredits($_POST["filmid"]);
    $credits = $credits["cast"];
    
    echo "<table>";
    echo "<tr> <th> Actors </th> <td> ";
    foreach ($credits as $value) {
       echo $value["name"]."(".$value["character"]."), ";
    }
    echo "</td> </tr>";
    echo "<tr>
        <th> VO </th>
        <th> FR </th>
        <th> EN </th>
    </tr>";
    foreach($dataFR as $key => $value){
        echo "<tr>";
        for($j=0;$j<3;$j++){
            if($j == 0){
                $data = $dataVO;    
            }else if($j ==1){
                $data = $dataFR;
            }else if($j == 2){
                $data = $dataEN;
            }
            
            echo "<td>";
                if($key == "poster_path"){
                    echo "<img src= \"https://image.tmdb.org/t/p/w342".$data[$key]."\">";
                }else{
                    echo $key." : ".$data[$key];
                }
              
            echo "</td>";
        }
         echo "</tr>";
    }
    echo "</table>";
    
    
    
    /*
    echo "\n\nSPARQL : \n";
    var_dump(wikiRequest("SELECT ?item ?itemLabel WHERE {
?item wdt:P397 wd:Q2 .
SERVICE wikibase:label { bd:serviceParam wikibase:language \"fr\" }
}"));*/


    
?>
</body>
</html>