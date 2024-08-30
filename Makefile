MAINPATH = $(dir)/src/$(prog).cpp
OBJPATH = $(dir)/obj/$(prog).o
EXEPATH = $(dir)/exe/$(prog).exe

$(EXEPATH): $(OBJPATH)
	g++ $(OBJPATH) -o $(EXEPATH)

$(OBJPATH): $(MAINPATH)
	g++ -c $(MAINPATH) -o $(OBJPATH)

run:
	./$(EXEPATH)

clean:
	del $(dir)\\obj\\*.o $(dir)\\exe\\*.exe \\files\\*.txt

#target: dependencies
#	action