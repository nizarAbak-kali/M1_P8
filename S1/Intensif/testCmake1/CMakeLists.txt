cmake_minimum_required(VERSION 2.8)

# add_executable(nomdupg source1 source2 ...)
add_executable(test hello.c)

# project(<projectname> [languageName1])
project(helloProjet C)

#message([STATUS|WARNING|AUTHOR_WARNING|FATAL_ERROR|SEND_ERROR]"message to diplay")
message("REPERTOIRE SRC DU PROJET: ${hello_SOURCE_DIR}")
message("REPERTOIRE COMPILATION DU PROJET: ${hello_BINARY_DIR}")

#ajout des sous-rep 
add_subdirectory(include)
add_subdirectory(src)
     
include_directoties(include)
