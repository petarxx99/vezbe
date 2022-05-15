#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>

typedef struct CarStruct{
  int hp;
  char* name;
  void* something;  

} Car;

Car* createCar(int hp, char* name);
bool deleteCar(Car* car);
void printCar(Car* car);


Car* createCar(int hp, char* name){
  Car* newCar = (Car*) malloc(sizeof(Car));
  newCar->hp = hp;
  newCar->name = name;
  newCar->something = NULL;
  return newCar;
}

bool deleteCar(Car* car){
  if(car == NULL){
     return false;
  } 
  
  free(car);
  return true;
}

void printCar(Car* car){
  printf("name = %s, hp = %d \n", car->name, car->hp);
}

