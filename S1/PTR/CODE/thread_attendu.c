#include<stdio.h>
#include<unistd.h>
#include<pthread.h>


void* funcp(void *_av){
  int v = *(int*) _av;
  pthread_t id = pthread_self();
  printf("thread %d \n", *(int*) &id);
  v++;
  return (*(void**)&v);

}


int main(){

  int A[2];
  A[0] =1;
  A[1] =2;
  printf("envoyee:%d recue %d \n",v,*(int*) &ret);

  return 0 ;
}
