
#include"Animal.h"
#include"ErrorHandling.h"

// Virtual methods
void Animal_delete(Animal **addressOfAnimalPointer){
    if(isDoublePointerNull(addressOfAnimalPointer, "Animal_delete")) return;
    const char *animalName = (*addressOfAnimalPointer)->name;

    free(*addressOfAnimalPointer);
    *addressOfAnimalPointer = NULL;
    printf("Animal %s has been deleted.\n", animalName);
}

void Animal_doSomething(Animal *_this){
    if(isNullPointer(_this, "Animal_doSomething")) return;

    printf("Animal %s of age %d does something. \n", _this->name, _this->age);
}

AnimalVTable ANIMAL_VTABLE_GLOBAL = {Animal_delete, Animal_doSomething};


// non virtual methods
bool Animal_isOlderThan(Animal *_this, int ageToCompareWith){
    if(isNullPointer(_this, "Animal_isOlderThan")) return false;

    return _this->age > ageToCompareWith;
}

bool Animal_isOlderThanAnAnimal(Animal *_this, Animal *animalToCompare){
    if(isNullPointer(animalToCompare, "Animal_isOlderThanAnimal, the second animal argument.\n")) return false;
    return (Animal_isOlderThan(_this, animalToCompare->age));
}

void Animal_someNonVirtualProcedure(Animal *_this){
    printf("This is a non virtual method of an animal. \n");
}



Animal *Animal_new(const char *name, int age){
    Animal *animal = (Animal*) malloc(sizeof(Animal));

    animal->age = age;
    animal->name = name;
    animal->vTable = &ANIMAL_VTABLE_GLOBAL;

    return animal;
}
