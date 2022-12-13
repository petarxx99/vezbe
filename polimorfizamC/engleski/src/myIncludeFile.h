#ifndef MY_INCLUDE_FILE
#define MY_INCLUDE_FILE


#include <stdio.h>
#include <stdlib.h>
#include<stdbool.h>
#include"ErrorHandling.h"

#define NULL_KARAKTER \0

#define BROJ_BAJTOVA_JEDNOG_KARAKTERA 1

bool isNullPointer(void *pointer, const char *functionName);
bool isDoublePointerNull(void **doublePointer, const char *functionName);

#endif // MY_INCLUDE_FILE
