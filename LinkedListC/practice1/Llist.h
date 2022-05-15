#include<stdbool.h>


typedef struct LinkedListStruct{
  void* address;
  struct LinkedListStruct* next;
  
} LinkedList;

LinkedList* addToLinkedList(void* addressOfVariable, LinkedList* currentHead);
LinkedList* addToAPlaceInLinkedList(void* address, int place, LinkedList* currentHead);
bool removeFromLlist(LinkedList* toRemove, LinkedList** head);
bool deleteLlist(LinkedList* head);
int sizeOfLL(LinkedList* head);
LinkedList* removeIndexFromLlist(int index, LinkedList** head);
