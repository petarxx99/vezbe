
#ifndef STRING_BUILDER_H
#define STRING_BUILDER_H


typedef struct {
    char *characters;
    int numberOfCharacters;
    int totalCharactersAllocated;
    int howManyNewCharactersToAllocate;
}StringBuilder;

void StringBuilder_init(StringBuilder *stringBuilder);

StringBuilder* StringBuilder_new();

StringBuilder* StringBuilder_new_init(char *initialString, int STRING_HAS_LESS_THAN_THIS_MANY_CHARACTERS);

void StringBuilder_delete(StringBuilder **addressToStringBuilderPointer);

void StringBuilder_reset(StringBuilder *stringBuilder);

void StringBuilder_appendChar(StringBuilder *stringBuilder, char newChar);

StringBuilder* StringBuilder_copy(StringBuilder *stringBuilder);




#endif // STRING_BUILDER_H
