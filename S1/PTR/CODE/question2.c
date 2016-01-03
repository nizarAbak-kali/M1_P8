#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<semaphore.h>

sem_t titi;
sem_t titi2;

int nb_toto1=0;
int nb_toto2=0;


void* f0(void* t){
  while(1){
    sem_wait(&titi);
    nb_toto1+=1;
    sem_post(&titi2);
  }

}

void* f1(void* t){
  while(1){
    sem_wait(&titi);
    sem_wait(&titi);

    nb_toto2 += 1;
    sem_post(&titi2);
    sem_post(&titi2);

  }

}


int main(){
  pthread_t ID[2];
  pthread_attr_t ATTr;

  pthread_attr_init(&ATTr);
  
  sem_init(&titi,0,0);
  sem_init(&titi2,0,0);

  pthread_create(&ID[0],&ATTr,f0,0);
  pthread_create(&ID[1],&ATTr,f1,0);
 

   sleep(1);
   printf("t1 : %3d , t2 : %3d  rapport: %f \n",nb_toto1,nb_toto2,(float)nb_toto2/nb_toto1);
  fflush(stdout);
  sleep(1);
  return 0; 
}
