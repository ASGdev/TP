#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
from math import *

def graphRep(xmin, xmax, fichier) :
    fichier.write("""
/cm { 28.3464567 mul} def
/repere {
/Arial findfont
.5 cm scalefont
setfont
newpath
0 0 moveto
10.5 cm 0 lineto
(x) show
0 0 moveto
0 12.5 cm lineto
(y) show
0 0 moveto
0 -100 cm lineto
0 0 moveto
-100 cm 0 cm lineto
30 30 scale
0.05 setlinewidth
stroke
newpath

100 1 moveto 
-100 1 lineto
100 -1 moveto 
-100 -1 lineto
100 2 moveto 
-100 2 lineto
100 -2 moveto 
-100 -2 lineto
100 3 moveto 
-100 3 lineto
100 -3 moveto 
-100 -3 lineto
100 4 moveto 
-100 4 lineto
100 -4 moveto 
-100 -4 lineto

2 -100 moveto
2 100 lineto
1 -100 moveto
1 100 lineto
-1 -100 moveto
-1 100 lineto
4 -100 moveto
4 100 lineto
3 -100 moveto
3 100 lineto
-3 -100 moveto
-3 100 lineto
-4 -100 moveto
-4 100 lineto
-2 -100 moveto
-2 100 lineto



0.01 setlinewidth
stroke
} def
10.5 cm 14.85 cm translate
repere


""")



def trace(function, xmin, xmax, nstep, output):
	output.write("x, %s\n" % function)
	function = eval("lambda x:" + function)
	fichier = open("test.ps", "w")
	if (xmin < 0) :
		output.write("hello there")
	step = 1.*(float(xmax)-float(xmin))/nstep
	fichier.write("%!")
	graphRep(xmin,xmax,fichier)
	fichier.write("\nnewpath")
	for i in range(nstep+1):
		try:
			x = (xmin + i*step)
			y = function(x)
		except:
			continue
		if( i == 0): 
			fichier.write("\n"+str(x)+" "+str(y)+" moveto")
		fichier.write("\n"+str(x)+" "+str(y)+" lineto") 
		#output.write("100 100 moveto \n" % (x, y))
		output.write("%s, %s \n" % (x, y))
	fichier.write("\n10.5 cm 14.85 cm translate")
	fichier.write("\n0.05 setlinewidth")
	fichier.write("\nstroke")
	fichier.write("\nshowpage")
		

def main(argv=None):
	if argv is None:
		argv = sys.argv	
	import getopt
	try:
		options, argv = getopt.getopt(argv[1:], "ho:n:", ["help","nstep=","output=","xmin=","xmax="])
	except getopt.GetoptError as message:
		#ajout de la ligne afficçhant le fait de rentrer des parametre d'une mauvaise facon
		sys.stdout.write("GETOPT a planté, les parametres suplémentaire n'ont pu être interprété")
		sys.exit(1)
		
	if len(argv) < 1:
    		#ajout de la ligne affichant la necessité de mettre un argument poru avoir un resultat
		sys.stdout.write("Veuillez introduire en paramêtre une fonction à calculer (e.g : sin(x))\n")
		sys.exit(1)
#	if len(argv) > 1 & len(options) < 1:
		#ajout de la ligne affichant la necessité de mettre un argument pour avoir un resultat
	#	sys.stdout.write("Veuillez entrer les parametres avant la fonction (E.G : -h X)\n")
	#	sys.exit(1)
		
	function = argv[0]	
	output = sys.stdout
	xmin,xmax = 0.,1.
	nstep=1000
	
	for option,value in options:
		if option in ["-h","--help"]:
			#DOC du logiciel
			sys.stdout.write("BLABLABLA\n")
			sys.exit(1)

		elif option in ["-o","--output"]:
			output = file(value,"w")
		elif option in ["--xmin"]:
			xmin = float(value)
		elif option in ["--xmax"]:
			xmax = float(value)
		elif option in ["--nstep","-n"]:
			nstep = int(value)

	if xmax<= xmin :
		xmax = xmin+1
			
	trace(function, xmin, xmax, nstep, output)


if __name__ == "__main__":
	sys.exit(main())
