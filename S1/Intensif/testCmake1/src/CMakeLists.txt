cmake_minimum_required(VERSION 2.8)


add_library(hello ${SRC})
install(TARGETS hello DESTINATION lib)
