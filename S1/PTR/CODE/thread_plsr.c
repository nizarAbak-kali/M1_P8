#include<stdio.h>
#include<unistd.h>
#include<pthread.h>


void* funcp(void *_av){
  printf("thread %d \n", (int) pthread_self());
}


int main(){
  printf("thread principale %d \n", (int) pthread_self());

  funcp(0);

  pthread_t ID[10];
  pthread_attr_t attr;
  pthread_attr_init(&attr);
  
  int i ;
  for(i =  0 ;i <10 ;i++ )
    pthread_create(&ID[i],&attr,funcp,0);

  return 0 ;
}
