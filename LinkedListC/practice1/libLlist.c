#include<stdio.h>
#include<stdlib.h>
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



LinkedList* addToLinkedList(void* addressOfVariable, LinkedList* currentHead){
  LinkedList* newHead = (LinkedList*)malloc(sizeof(LinkedList));
  newHead->address = addressOfVariable;
  newHead->next = currentHead;
  return newHead;
}

LinkedList* addToAPlaceInLinkedList(void* address, int place, LinkedList* currentHead){
  LinkedList* tmp = currentHead;
  LinkedList* previous = NULL;
  for(int i=0; i<place; i++){
    if(tmp == NULL){
      return NULL;
    }
    previous = tmp;
    tmp = tmp->next;
  }
  
  if(previous == NULL){
    LinkedList* newHead = (LinkedList*) malloc(sizeof(LinkedList));
    newHead->address = address;
    newHead->next = currentHead;
    currentHead = newHead;
  }
  
  if(tmp == NULL){
    return NULL;
  }
  
  LinkedList* newHead = (LinkedList*) malloc(sizeof(LinkedList));
  newHead->address = address;
  
  previous->next = tmp->next;
  
 return currentHead;
}


bool removeFromLlist(LinkedList* toRemove, LinkedList** head){
  
  LinkedList* previous = NULL;
  LinkedList* tmp = *head;
  
  while(tmp != NULL){
    if(tmp == toRemove){
       break;
    }
    
    previous = tmp;
    tmp = tmp->next;
  }
  
  if(tmp == NULL) return false;
  
  if(previous == NULL) {
    *head = (*head)->next;
    return true;
  }
  
  previous->next = tmp->next;
  
  return true;
}

LinkedList* removeIndexFromLlist(int index, LinkedList** head){
  LinkedList* tmp = *head;
  LinkedList* previous = NULL;
  
  if(index == 0){
    *head = (*head)->next;
    return tmp;
  }
  
  for(int i=0; i<index; i++){
    if(tmp==NULL){
      return NULL;
    }
    previous = tmp;
    tmp = tmp->next;
  }
  
  previous->next = tmp->next;
  return tmp;
}



bool deleteLlist(LinkedList* head){

  if(head == NULL) 
    return false;
  
  LinkedList* tmp = NULL;
  
  while(head != NULL){
    tmp = head;
    head = head->next;
    free(tmp);
  }

  return true;
}

int sizeOfLL(LinkedList* head){
  int res = 0;
  while(head != NULL){
    res++;
    head = head->next;
  }
  return res;
}

