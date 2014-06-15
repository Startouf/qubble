with open('signature1.txt', 'r') as fichier:
	with open('perfect.txt', 'w') as resultat:
		ecrit = ''
		contenu = fichier.read()
		lignes = contenu.split("\n")
		i = 0
		for ligne in lignes:
			ecrit = "perfectSquare[{0}] = {1}\n".format(i, ligne[0:ligne.find(" ##")])
		resultat.write(ecrit)
			





