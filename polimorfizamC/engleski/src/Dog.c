
#include"Dog.h"
#include"ErrorHandling.h"


// Methods for CanMakeNoise interface
void Dog_startMakingNoise(Dog *_this, int decibels){
    if(isNullPointer(_this, "Dog_startMakingNoise")) return;

    printf("Dog %s of owner %s is making noise worth %d decibels. \n", _this->name, _this->owner, decibels);
}


void Dog_stopMakingNoise(Dog *_this){
    if(isNullPointer(_this, "Dog_stopMakingNoise")) return;

    printf("Dog %s of owner %s has stopped making noise. \n", _this->name, _this->owner);
}

void Dog_canMakeNoiseDestroyed(Dog *_this, void *deletedInterface){
    if(isNullPointer(&_this, "Dog_canMakeNoiseDestroyed")) return;

    printf("Dog %s has been notified that CanMakeNoise on address %lu that it has implemented has been deleted.\n",
           _this->name,
           deletedInterface);
}

CanMakeNoise* Dog_createCanMakeNoise(Dog *_this){
    if(isNullPointer(_this, "Dog_createCanMakeNoise")) return NULL;

    CanMakeNoise *canMakeNoise = (CanMakeNoise*) malloc(sizeof(CanMakeNoise));
    canMakeNoise->startMakingNoise = Dog_startMakingNoise;
    canMakeNoise->stopMakingNoise = Dog_stopMakingNoise;
    canMakeNoise->receiveMessageThatInterfaceIsDeleted = Dog_canMakeNoiseDestroyed;

    canMakeNoise->structThatImplementsInterface = _this;

    return canMakeNoise;
}


// Virtual methods
void Dog_delete(Dog **addressToAPointerOfDogToDelete){
    if(isDoublePointerNull(addressToAPointerOfDogToDelete, "Dog_delete")) return;
    const char* dogName = (*addressToAPointerOfDogToDelete)->name;

    free(*addressToAPointerOfDogToDelete);
    *addressToAPointerOfDogToDelete = NULL;

    printf("Dog %s has been deleted. \n", dogName);
}

void Dog_doSomething(Dog *_this){
    if(isNullPointer(_this, "Dog_doSomething")) return;

    printf("A %s %s which is %d years old is doing something. \n", _this->breed, _this->name, _this->age);
}

void Dog_greetOwner(Dog *_this){
    if(isNullPointer(_this, "Dog_greetOwner")) return;

    printf("Dog %s greets his owner %s. \n", _this->name, _this->owner);
}

const DogVTable DOG_VTABLE_GLOBAL = {Dog_delete, Dog_doSomething, Dog_createCanMakeNoise, Dog_greetOwner};


Dog* Dog_new(const char *name, int age, const char *owner, const char *breed){
    Dog *newDog = (Dog*) malloc(sizeof(Dog));

    newDog->name = name;
    newDog->age = age;
    newDog->owner = owner;
    newDog->breed = breed;
    newDog->vTable = &DOG_VTABLE_GLOBAL;
    newDog->createCanMakeNoise = Dog_createCanMakeNoise;

    return newDog;
}
