
#include"stdio.h"

void BaseInterface_delete(void **addressOfPointerToInterface,
                          void *structThatImplementsInterface,
                          void (*receiveMessageThatInterfaceIsDeleted)(void *structThatImplementsInterface, void *interface)){


       if(addressOfPointerToInterface == NULL){
            printf("BaseInterface_delete has received a NULL pointer. \n", stderr);
            return;
       }

       void *thisInterface = *addressOfPointerToInterface;
       if(thisInterface == NULL){
            printf("BaseInterface_delete: the interface to delete is NULL.\n", stderr);
            return;
       }

       free(thisInterface);
       *addressOfPointerToInterface = NULL;
       receiveMessageThatInterfaceIsDeleted(structThatImplementsInterface, thisInterface);

}

