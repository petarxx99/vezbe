
#include"myIncludeFile.h"

bool isNullPointer(void *pointer, const char *functionName){
    if(pointer == NULL){
        printf("%s has received a NULL pointer. \n", stderr);
        return true;
    }

    return false;
}

bool isDoublePointerNull(void **doublePointer, const char *functionName){
    if(!isNullPointer(doublePointer, functionName)){
        if(*doublePointer == NULL){
            printf("%s has received a double pointer which is pointing to a NULL pointer. \n", stderr);
            return true;
        }

        return false;
    }

    return false;
}
