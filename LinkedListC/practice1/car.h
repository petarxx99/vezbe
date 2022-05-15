
typedef struct CarStruct{
  int hp;
  char* name;
  void* something;  

} Car;

Car* createCar(int hp, char* name);
bool deleteCar(Car* car);
void printCar(Car* car);
