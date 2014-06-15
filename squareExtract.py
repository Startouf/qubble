#!/usr/bin/python2.7

with open('signature2.txt', 'r') as fichier:
	with open('perfect.txt', 'w') as resultat:
		ecrit = ''
		contenu = fichier.read()
		lignes = contenu.split("\n")
		i = -2
		for ligne in lignes:
			ecrit = ecrit + "perfectSquare[{0}] = (float) {1};\n".format(i, ligne[0:ligne.find(" ##")])
			i = i + 1
			ecrit = ecrit + "perfectSquare[{0}] = (float) {1};\n".format(i, ligne[0:ligne.find(" ##")])
			i = i + 1
		resultat.write(ecrit)
			





