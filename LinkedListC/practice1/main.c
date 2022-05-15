#include<stdio.h>  // < znaci da se header fajl nalazi u /usr ili tamo negde 
#include"Llist.h"
#include"car.h"  // nema uglastih zagrada jer header fajl se nalazi na istom path kao i main.c

#define NUM_OF_CARS 4
 
int main(int argv, char** argc){
  Car* car1 = createCar(120, "prvi auto");
  LinkedList* LLCars = addToLinkedList((void*)car1, NULL);
 
   
  Car* cars[NUM_OF_CARS];
  
  for(int i=0; i<NUM_OF_CARS; i++){
    cars[i] = createCar(40*i, "i");
    LLCars = addToLinkedList((void*)cars[i], LLCars);
  }
  
  printf("size of ll = %d \n", sizeOfLL(LLCars));
  
  removeIndexFromLlist(2, &LLCars);
  printf("size of ll = %d \n", sizeOfLL(LLCars));
  
  removeIndexFromLlist(3, &LLCars);
  printf("size of ll = %d \n", sizeOfLL(LLCars));
  
  removeIndexFromLlist(1, &LLCars);
  printf("size of ll = %d \n", sizeOfLL(LLCars));
  
  deleteLlist(LLCars);
  
  
  deleteCar(car1);
  for(int i=0; i<NUM_OF_CARS; i++){
    deleteCar(cars[i]);
  }
  return 0;
}
