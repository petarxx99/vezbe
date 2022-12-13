#ifndef DOG_H
#define DOG_H

#include"Animal.h"
#include"myIncludeFile.h"
#include"ErrorHandling.h"
#include"CanMakeNoise.h"

typedef struct {

    void (*destructor)(void **pointerToAnAnimal);
    void (*doSomething)(void *_this);

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


Dog* Dog_new(const char *name, int age, const char *owner, const char *breed);

void Dog_startMakingNoise(Dog *_this, int decibels);
void Dog_stopMakingNoise(Dog *_this);



#endif // DOG_H

#include"CanMakeNoise.h"
