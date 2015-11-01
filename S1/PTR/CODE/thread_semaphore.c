#include<stdio.h>
#include<unistd.h>
#include<pthread.h>
#include <semaphore.h>

sem_t turnA;
sem_t turnB;

void* f0(void *_av){
  int i ;
  for(i = 0 ;i<1000;i++){
    sem_wait(&turnA);
    printf("A");
    sem_post(&turnB);
  }
  printf("thread %d \n", (int) pthread_self());
}
void* f1(void *_av){
  int i ;
  for(i = 0 ;i<1000;i++){
    sem_wait(&turnB);
    printf("B");
    sem_post(&turnA);
  }
  printf("thread %d \n", (int) pthread_self());
}




int main(){
  printf("thread principale %d \n", (int) pthread_self());

  pthread_t ID[2];
  pthread_attr_t attr;
  pthread_attr_init(&attr);
 
  sem_init(&turnA,0,0);
  sem_init(&turnB,0,0);
  sem_post(&turnA);
  
  
  pthread_create(&ID[0],&attr,f0,0);

  pthread_create(&ID[1],&attr,f1,0);


  return 0 ;
}
