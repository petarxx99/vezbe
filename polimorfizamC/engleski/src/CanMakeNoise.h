#ifndef CAN_MAKE_NOISE_H
#define CAN_MAKE_NOISE_H

#include"myIncludeFile.h"

typedef struct {

    void *structThatImplementsInterface;

    void (*receiveMessageThatInterfaceIsDeleted)(void *structThatImplementsInterface, void *thisInterface);
    void (*startMakingNoise)(void *structThatImplementsInterface, int decibels);
    void (*stopMakingNoise)(void *structThatImplementsInterface);

}CanMakeNoise;


#endif // CAN_MAKE_NOISE_H

