The Main class with the main method is in the glavnipaket directory.
I solved this problem in several ways, so I made several classes that calculate the longest growing array in different ways.
In the main class I instantiated several of them just as an illustration, but I only pass one of them to the NajveciRastuciNizApp constructor. That constructor accepts an interface NajveciRastuciNizInterfejs which all the classes that can find the longest growing array implement. I found this design pattern of dependency injection and inversion of control to be useful in this case, as I have developed several different approaches to solving this problem. This way, I can easily swap and test different solutions.
Classes where I used English language instead of my native langauge are in the "engleski" directory (the word engleski means English in my native language).

