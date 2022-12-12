
#ifndef VECTOR_STRING_H
#define VECTOR_STRING_H

#include"StringBuilder.h"

typedef struct {
    StringBuilder **arrayOfStringBuilders;
    int numberOfArrays;
    int numberOfTotalAllocatedArrays;
    int howManyNewArraysToAllocate;
} VectorString;

VectorString* VectorString_new();

void VectorString_delete(VectorString **addressToVectorStringPointer);

void VectorString_addString(VectorString* vectorString, StringBuilder *stringBuilder);

char* VectorString_findTheLongestString(VectorString *vectorString);

void VectorString_printAll(VectorString *vectorString);


VectorString* getLongestWordsInEachLine(char *text);


#endif // VECTOR_STRING_H

