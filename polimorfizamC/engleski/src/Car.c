



#include"myIncludeFile.h"
#include"CanMakeNoise.h"
#include"Car.h"

CanMakeNoise* Car_createCanMakeNoise(Car *_this);

Car* Car_new(const char *name, int productionYear){
    Car *newCar = (Car*) malloc(sizeof(Car));

    newCar->name = name;
    newCar->productionYear = productionYear;
    newCar->speed = 0;
    newCar->createCanMakeNoise = Car_createCanMakeNoise;

    return newCar;
}



void Car_delete(Car **addressOfCarPointer){
    if(isDoublePointerNull(addressOfCarPointer, "Car_delete")) return;

    free(*addressOfCarPointer);
    *addressOfCarPointer = NULL;
}

void Car_accelerate(Car *_this, int changeInSpeed){
    if(isNullPointer(_this, "Car_accelerate")) return;

    if(_this->speed + changeInSpeed > 0){
        _this->speed += changeInSpeed;
    }
}

void Car_startMakingNoise(Car *_this, int decibels){
    if(isNullPointer(_this, "Car_startMakingNoise")) return;

    printf("Car %s is making noise worth of %d decibels. \n", _this->name, decibels);
}


void Car_stopMakingNoise(Car *_this){
    if(isNullPointer(_this, "Car_stopMakingNoise")) return;

    printf("Car %s has stopped making noise. \n", _this->name);
}


void Car_receiveMessageThatCanMakeNoiseIsDeleted(Car *_this, void *interface){
    if(isNullPointer(_this, "Car_receiveMessageThatCanMakeNoiseIsDeleted")) return;

    printf("Car %s has been notified that CanMakeNoise on the address %lu that it has implemented has been deleted. \n", _this->name, interface);
}

CanMakeNoise* Car_createCanMakeNoise(Car *_this){
    if(isNullPointer(_this, "Car_createCanMakeNoise")){
        return;
    }

    CanMakeNoise *canMakeNoise = (CanMakeNoise*) malloc(sizeof(CanMakeNoise));

    canMakeNoise->structThatImplementsInterface = _this;
    canMakeNoise->receiveMessageThatInterfaceIsDeleted = Car_receiveMessageThatCanMakeNoiseIsDeleted;
    canMakeNoise->startMakingNoise = Car_startMakingNoise;
    canMakeNoise->stopMakingNoise = Car_stopMakingNoise;

    return canMakeNoise;
}
