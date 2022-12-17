
#include"myIncludeFile.h"

bool isNullPointer(void *pointer, const char *functionName){
    if(pointer == NULL){
        fprintf(stderr, "%s has received a NULL pointer. \n", functionName);
        return true;
    }

    return false;
}

bool isDoublePointerNull(void **doublePointer, const char *functionName){
    if(isNullPointer(doublePointer, functionName)) return true;

    if(*doublePointer == NULL){
            fprintf(stderr, "%s has received a double pointer which is pointing to a NULL pointer. \n", functionName);
            return true;
    }

    return false;
}
