cmake_minimum_required(VERSION 2.8)

#Glob genere la liste des fichiers qui correspondent à la globbing expressions
#variable est le nom que l'on donne a la liste 
#Relativepath est le chemin ou on va chercher la globbing expressions 


file(GLOB include_H . *.h)

#on specifie le rep d'install

#install(files <files to add> dest <dir>)
install(FILES ${include_H} DESTINATION include)
