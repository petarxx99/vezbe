

#include"IncludeFajl.h"
#include"StringBuilder.h"



typedef struct {
    StringBuilder **arrayOfStringBuilders;
    int numberOfArrays;
    int numberOfTotalAllocatedArrays;
    int howManyNewArraysToAllocate;
} VectorString;

VectorString* VectorString_new(){
    VectorString *vectorString = (VectorString*) malloc(sizeof(VectorString));
    vectorString->arrayOfStringBuilders = (StringBuilder**) malloc(sizeof(StringBuilder*));
    vectorString->numberOfArrays = 0;
    vectorString->numberOfTotalAllocatedArrays = 1;
    vectorString->howManyNewArraysToAllocate = 5;
}

void VectorString_delete(VectorString **addressToVectorStringPointer){
    if(addressToVectorStringPointer == NULL){
        printf("VectorString_delete has received a null pointer.\n", stderr);
        return;
    }
    if(*addressToVectorStringPointer == NULL){
        printf("The object that VectorString_delete should delete is NULL.\n", stderr);
        return;
    }

    VectorString *vectorString = *addressToVectorStringPointer;

    for(int i=0; i<vectorString->numberOfArrays; i++){
        if(vectorString->arrayOfStringBuilders[i] != NULL){
            StringBuilder_delete(vectorString->arrayOfStringBuilders + i);
        }
    }

    free(vectorString->arrayOfStringBuilders);
    free(vectorString);
    *addressToVectorStringPointer = NULL;
}

void VectorString_addString(VectorString* vectorString, StringBuilder *stringBuilder){
    if(vectorString == NULL){
        printf("VectorString coming to addCharPtr is NULL.\n", stderr);
        return;
    }

    if(vectorString->numberOfTotalAllocatedArrays <= vectorString->numberOfArrays){
        const int NUMBER_OF_ARRAYS_TO_ALLOCATE = vectorString->numberOfArrays + vectorString->howManyNewArraysToAllocate;

        vectorString->arrayOfStringBuilders = realloc(vectorString->arrayOfStringBuilders,
                                            sizeof(StringBuilder*) * NUMBER_OF_ARRAYS_TO_ALLOCATE);
        vectorString->numberOfTotalAllocatedArrays += vectorString->howManyNewArraysToAllocate;
    }

    if(stringBuilder != NULL){
        vectorString->arrayOfStringBuilders[vectorString->numberOfArrays] = StringBuilder_copy(stringBuilder);
    } else {
        vectorString->arrayOfStringBuilders[vectorString->numberOfArrays] = NULL;
    }
    vectorString->numberOfArrays++;
}


char* VectorString_findTheLongestString(VectorString *vectorString){
    if(vectorString == NULL) {
        printf("findLongestString function has received a null pointer.", stderr);
        return NULL;
    }

    if(vectorString->numberOfArrays == 0){
        return StringBuilder_new();
    }

    int max=-1;
    int lengthOfMaxString = 0;
    for(int i=0; i<vectorString->numberOfArrays; i++){
        StringBuilder *stringBuilder = vectorString->arrayOfStringBuilders[i];
        if(stringBuilder == NULL) continue;

        if(stringBuilder->numberOfCharacters > lengthOfMaxString){
            max = i;
            lengthOfMaxString = stringBuilder->numberOfCharacters;
        }
    }

    return StringBuilder_copy(vectorString->arrayOfStringBuilders[max]);
}

void VectorString_printAll(VectorString *vectorString){
    if(vectorString == NULL){
        printf("VectorString_printAll has received a null pointer.\n", stderr);
        return;
    }

    for(int i=0; i<vectorString->numberOfArrays; i++){
        StringBuilder *stringBuilder = vectorString->arrayOfStringBuilders[i];
        if(stringBuilder != NULL){
            printf("string number %d: %s\n", i, stringBuilder->characters);
        }
    }
    printf("\n");
}


VectorString* getLongestWordsInEachLine(char *text){
    VectorString *wordsOfALine = VectorString_new();
    VectorString *longestWordsOfLines = VectorString_new();
    StringBuilder *currentWord = StringBuilder_new();

    char previousCharWasALetter = 1;
    char nextCharacter;
    int i=0;
    while((nextCharacter=text[i]) > 0){
        if(isALetter(nextCharacter)){
            StringBuilder_appendChar(currentWord, nextCharacter);
            previousCharWasALetter = 1;
        } else if(previousCharWasALetter){ /* This character isn't a letter, but the previous one was, therefore we have reached the end of a word.*/
            VectorString_addString(wordsOfALine, currentWord);
            StringBuilder_reset(currentWord);
            previousCharWasALetter = 0;
        }

        if(nextCharacter == END_OF_LINE_CHARACTER){
            StringBuilder *longestWordOfThisLine = VectorString_findTheLongestString(wordsOfALine);
            VectorString_addString(longestWordsOfLines, longestWordOfThisLine);
            StringBuilder_delete(&longestWordOfThisLine);
            VectorString_delete(&wordsOfALine);
            wordsOfALine = VectorString_new();
        }
        i++;
    }

    VectorString_delete(&wordsOfALine);
    return longestWordsOfLines;
}

