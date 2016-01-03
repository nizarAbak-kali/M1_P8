/*
 * version.c
 *
 *  Created on: 3 oct. 2015
 *      Author: nizar
 */
#define _POSIX_VERSION
#include<unistd.h>
#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>

int main (void){
#if _POSIX_VERSION < 199506L
	printf("sans threads , posix version %d \n",_POSIX_VERSION);
#else
	printf("avec threads posix \n");
#ifdef _POSIX_PTHREAD_PRIORITY_SCHEDULING
	printf("avec ordo .. \n");
#else
	printf("pas de ordo .. \n") ;
#endif
#endif


	return 0;
}
