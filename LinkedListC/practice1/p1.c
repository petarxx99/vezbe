#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>



typedef struct CarStruct{
  int hp;
  char* name;
  void* something;  

} Car;

Car* createCar(int hp, char* name){
  Car* newCar = (Car*) malloc(sizeof(Car));
  newCar->hp = hp;
  newCar->name = name;
  newCar->something = NULL;
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


int main(int argv, char** argc){
  Car car1;
  car1.hp = 100;
  car1.name = (char*)"Ime";
  printCar(&car1);
  
  Car* car2 = createCar(100, "Drugi auto");
  printCar(car2);
  deleteCar(car2);

 return 0;
}
