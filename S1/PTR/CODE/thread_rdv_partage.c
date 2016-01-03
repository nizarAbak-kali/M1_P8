#include<stdlib.h>
#include<stdio.h>
#include<pthread.h>

// la mem partager 
int v ;

void* f(void* _p){
  v++;
}



int main(){
  pthread_t ID;
  pthread_attr_t ATTR ;
  pthread_attr_init(&ATTR);

  v = 0;
  
  pthread_create(&ID, &ATTR, f, 0);
  
  
  if(1){
    pthread_join(ID,0);
    printf("v %d\n",v);
  } 
  else{
    printf("v %d\n",v);
    pthread_join(ID,0);
  }

  return EXIT_SUCCESS;
}
