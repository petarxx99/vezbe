
#ifndef BASE_INTERFACE_H
#define BASE_INTERFACE_H

void BaseInterface_delete(void **addressOfPointerToInterface,
                          void *structThatImplementsInterface,
                          void (*receiveMessageThatInterfaceIsDeleted)(void *structThatImplementsInterface, void *interface));

#endif // BASE_INTERFACE_H


