#ifndef DOG_H
#define DOG_H

#include"Animal.h"
#include"myIncludeFile.h"
#include"ErrorHandling.h"
#include"CanMakeNoise.h"

typedef struct {

    void (*destructor)(void **pointerToAnAnimal);
    void (*doSomething)(void *_this);

    CanMakeNoise* (*createCanMakeNoise)(void *_this);
    void (*greetOwner)(void *_this);

}DogVTable;


typedef struct DogStruct{

    DogVTable *vTable;
    const char *name;
    int age;

    const char *owner;
    const char *breed;

    CanMakeNoise* (*createCanMakeNoise)(struct DogStruct *_this);
}Dog;

// non virtual methods
Dog* Dog_new(const char *name, int age, const char *owner, const char *breed);

// methods that are overriden from CanMakeNoise interface
void Dog_startMakingNoise(Dog *_this, int decibels);
void Dog_stopMakingNoise(Dog *_this);
CanMakeNoise* Dog_createCanMakeNoise(Dog *_this);

// virtual methods
void Dog_delete(Dog **addressToAPointerOfDogToDelete);
void Dog_doSomething(Dog *_this);
void Dog_greetOwner(Dog *_this);

#endif // DOG_H


