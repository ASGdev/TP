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
stroke
} def
10.5 cm 14.85 cm translate
repere
""")

def trace(function, xmin, xmax, nstep, output):
	output.write("x, %s\n" % function)
	function = eval("lambda x:" + function)
	fichier = open("test.ps", "w")
	step = 1.*(float(xmax)-float(xmin))/nstep
	fichier.write("%!")
	graphRep(xmin,xmax,fichier);
	for i in range(nstep+1):
		try:
			x = xmin + i*step
			y = function(x)
		except:
			continue
		if( i == 0): 
			fichier.write("\n"+str(x)+" "+str(y)+" moveto")
		fichier.write("\n"+str(x)+" "+str(y)+" lineeto") 
		#output.write("100 100 moveto \n" % (x, y))
		output.write("%s, %s \n" % (x, y))
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
	nstep=10
	
	for option,value in options:
		if option in ["-h","--help"]:
			#DOC du logiciel
			sys.stdout.write("BLABLABLA\n")
			sys.exit(1)

		elif option in ["-o","--output"]:
			output = file(value,"w")
		elif option in ["--xmin"]:
			xmin = value
		elif option in ["--xmax"]:
			xmax = value
		elif option in ["--nstep","-n"]:
			xmax = value

	if float(xmax)<=float(xmin) :
		xmax = xmin+1
			
	trace(function, xmin, xmax, nstep, output)


if __name__ == "__main__":
	sys.exit(main())
