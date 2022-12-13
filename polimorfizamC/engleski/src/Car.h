#ifndef CAR_H
#define CAR_H

#include"CanMakeNoise.h"

typedef struct CarStruct{
    const char *name;
    int productionYear;
    int speed;

    CanMakeNoise* (*createCanMakeNoise)(struct CarStruct *_this);
}Car;

Car* Car_new(const char *name, int productionYear);

void Car_delete(Car **addressOfCarPointer);

void Car_accelerate(Car *_this, int changeInSpeed);

void Car_startMakingNoise(Car *_this, int decibels);
void Car_stopMakingNoise(Car *_this);
void Car_receiveMessageThatCanMakeNoiseIsDeleted(Car *_this, void *interface);


#endif // CAR_H

#include"CanMakeNoise.h"
