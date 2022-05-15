CC=gcc
CFLAGS=-Wall


libcar.a: car.o
	ar rcs $@ $^
	
		
%.o: %.c 
	$(CC) $(CFLAGS) -c $^ -o $@
	
%.out: %.c %.o 
	$(CC) $(CFLAGS) $^ -o $@	

%.so: %.o
	-fPIC -shared -o $@ $^
	

%.a: %.o 
	ar rcs $@ $^
	

main.out: main.c
	$(CC) $(CFLAGS) -g $^ -o $@ -L. -lcar -lLlist

clean:
	rm *.o *.a main.out
	
	
