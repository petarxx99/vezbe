#include"IncludeFajl.h"


typedef struct {
    char *characters;
    int numberOfCharacters;
    int totalCharactersAllocated;
    int howManyNewCharactersToAllocate;
}StringBuilder;

void StringBuilder_init(StringBuilder *stringBuilder){
    stringBuilder->characters = NULL;
    stringBuilder->numberOfCharacters = 0;
    stringBuilder->totalCharactersAllocated = 0;
    const int DEFAULT_VALUE_HOW_MANY_NEW_CHARACTERS_TO_ALLOCATE = 5;
    stringBuilder->howManyNewCharactersToAllocate = DEFAULT_VALUE_HOW_MANY_NEW_CHARACTERS_TO_ALLOCATE;
}

StringBuilder* StringBuilder_new(){
    StringBuilder *stringBuilder = (StringBuilder*) malloc(sizeof(StringBuilder));
    StringBuilder_init(stringBuilder);

    return stringBuilder;
}

StringBuilder* StringBuilder_new_init(char *initialString, int STRING_HAS_LESS_THAN_THIS_MANY_CHARACTERS){
    if(initialString == NULL){
        return StringBuilder_new();
    }

    int lengthOfInitString = strnlen(initialString, STRING_HAS_LESS_THAN_THIS_MANY_CHARACTERS);
    if(lengthOfInitString == STRING_HAS_LESS_THAN_THIS_MANY_CHARACTERS){
        printf("String has too many characters. StringBuilder_new_init function failed.\n", stderr);
        return NULL;
    }

    StringBuilder *stringBuilder = StringBuilder_new();
    stringBuilder->characters = (char*) malloc(lengthOfInitString + 1); //+1 for NULL in the end.
    memcpy(stringBuilder->characters, initialString, lengthOfInitString);

    stringBuilder->numberOfCharacters = lengthOfInitString;
    stringBuilder->totalCharactersAllocated = lengthOfInitString;

    return stringBuilder;
}

void StringBuilder_delete(StringBuilder **addressToStringBuilderPointer){
    if(addressToStringBuilderPointer == NULL){
        printf("StringBuilder_delete has received a null pointer. \n", stderr);
        return;
    }
    if(*addressToStringBuilderPointer == NULL){
        printf("StringBuilder_delete is trying to free a NULL pointer. \n", stderr);
        return;
    }

    StringBuilder *stringBuilderToDelete = *addressToStringBuilderPointer;
    if(stringBuilderToDelete->characters != NULL){
        free(stringBuilderToDelete->characters);
    }
    free(stringBuilderToDelete);
    *addressToStringBuilderPointer = NULL;
}


void StringBuilder_reset(StringBuilder *stringBuilder){
    if(stringBuilder == NULL){
        printf("StringBuilder_delete_characters has received a null pointer. \n");
        return;
    }

    if(stringBuilder->characters != NULL){
        free(stringBuilder->characters);
        stringBuilder->characters = NULL;
    }

    StringBuilder_init(stringBuilder);
}


void StringBuilder_appendChar(StringBuilder *stringBuilder, char newChar){
    if(stringBuilder == NULL){
        printf("appendToStringBuilder has received a NULL pointer.\n", stderr);
        return;
    }

    if(stringBuilder->characters == NULL){
        stringBuilder->characters = (char*) malloc(stringBuilder->howManyNewCharactersToAllocate + 1); //+1 for NULL in the end.
        stringBuilder->totalCharactersAllocated += stringBuilder->howManyNewCharactersToAllocate;
    }


    if(stringBuilder->numberOfCharacters >= stringBuilder->totalCharactersAllocated){
        stringBuilder->characters = (char*) realloc(stringBuilder->characters,
                                                    stringBuilder->totalCharactersAllocated + stringBuilder->howManyNewCharactersToAllocate +1);//+1 for NULL in the end.
        stringBuilder->totalCharactersAllocated += stringBuilder->howManyNewCharactersToAllocate;
    }

    stringBuilder->characters[stringBuilder->numberOfCharacters] = newChar;
    stringBuilder->numberOfCharacters++;
    stringBuilder->characters[stringBuilder->numberOfCharacters] = NULL; //The last character in string is NULL.

}

StringBuilder* StringBuilder_copy(StringBuilder *stringBuilder){
    if(stringBuilder == NULL){
        printf("StringBuilder_copy has received a null pointer.\n", stderr);
        return NULL;
    }

    StringBuilder *newStringBuilder = StringBuilder_new();
    memcpy(newStringBuilder, stringBuilder, sizeof(StringBuilder));

    if(stringBuilder->characters != NULL){
        newStringBuilder->characters = (char*) malloc(sizeof(char) * (1 + stringBuilder->numberOfCharacters)); //1+ for NULL character in the end.
        memcpy(newStringBuilder->characters, stringBuilder->characters, stringBuilder->numberOfCharacters);
        newStringBuilder->characters[stringBuilder->numberOfCharacters] = NULL; //Last char must be NULL.
    }

    return newStringBuilder;
}

