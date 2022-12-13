#ifndef ANIMAL_H
#define ANIMAL_H

#include"myIncludeFile.h"

typedef struct {

    void (*destructor)(void **pointerToAnAnimal);
    void (*doSomething)(void *_this);

}AnimalVTable;

typedef struct {
    AnimalVTable *vTable;
    const char *name;
    int age;
}Animal;

Animal* Animal_new(const char *name, int age);

bool Animal_isOlderThan(Animal *_this, int ageToCompareWith);
bool Animal_isOlderThanAnAnimal(Animal *_this, Animal *animalToCompare);

void Animal_someNonVirtualProcedure(Animal *_this);

#endif // ANIMAL_H
