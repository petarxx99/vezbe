#include <stdio.h>
#include <stdlib.h>
#include"myIncludeFile.h"
#include"Car.h"
#include"CanMakeNoise.h"
#include"Animal.h"
#include"Dog.h"

void testAnimal();
void testInterfaceCanMakeNoise();
void testAnimalDogUpcasting();

int main(void)
{
    testAnimal();
    testInterfaceCanMakeNoise();
    testAnimalDogUpcasting();
    return 0;
}

void testAnimalDogUpcasting(){
    Animal *dogUpcastedToAnimal = Dog_new("Rocky", 3, "Kyle", "retriever");

    if(Animal_isOlderThan(dogUpcastedToAnimal, 2)){
        printf("%s is older than 2.\n", dogUpcastedToAnimal->name);
    } else {
        printf("%s is not older than 2. \n", dogUpcastedToAnimal->name);
    }
    dogUpcastedToAnimal->vTable->doSomething(dogUpcastedToAnimal);
    printf("\n\n");

    Animal *animal = Animal_new("Zucko", 5);
    printf("%s is %d years old. \n", animal->name, animal->age);
    animal->vTable->doSomething(animal);

    Dog *dog = Dog_new("asd", 20, "John", "Saf");
    dog->vTable->destructor(&dog);
    dogUpcastedToAnimal->vTable->destructor(&dogUpcastedToAnimal);
    animal->vTable->destructor(&animal);
}

void testInterfaceCanMakeNoise(){
    Car *car = Car_new("Sandero", 2010);
    CanMakeNoise *noiseMaker1 = car->createCanMakeNoise(car);
    noiseMaker1->startMakingNoise(noiseMaker1->structThatImplementsInterface, 50);
    noiseMaker1->stopMakingNoise(noiseMaker1->structThatImplementsInterface);
    BaseInterface_delete(&noiseMaker1,
                        noiseMaker1->structThatImplementsInterface,
                        noiseMaker1->receiveMessageThatInterfaceIsDeleted);
    Car_delete(&car);

    printf("\n\n");

    Dog *dog = Dog_new("Zucko", 4, "John", "retriever");
    CanMakeNoise *noiseMaker2 = dog->createCanMakeNoise(dog);
    noiseMaker2->startMakingNoise(noiseMaker2->structThatImplementsInterface, 30);
    noiseMaker2->stopMakingNoise(noiseMaker2->structThatImplementsInterface);
    BaseInterface_delete(&noiseMaker2, noiseMaker2->structThatImplementsInterface, noiseMaker2->receiveMessageThatInterfaceIsDeleted);
    dog->vTable->destructor(&dog);

}



void testAnimal(){
    Animal *animal = Animal_new("Zucko", 20);
    const int AGE_TO_COMPARE = 19;
    if(Animal_isOlderThan(animal, AGE_TO_COMPARE)){
        printf("Animal is older than %d. \n", AGE_TO_COMPARE);
    }
    animal->vTable->destructor(&animal);

}

