<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
    <link rel="stylesheet" href="style.css" />
	<title>METEO</title>
</head>
<body>
<form method="GET" action="meteo.php">
	<label>STATION</label>
	<input type="text" name="station">
	<input type="submit" name="Confirmer">
</form>

<?php 
	include("station.php");
?>


	

	<table style="width:100%">
	  	<?php
		affichage_synthetique($liste_station);
	  	?>
	  	
	</table>

</body>

</html>

