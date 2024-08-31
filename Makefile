MAINPATH = $(dir)/src
OBJPATH = $(dir)/obj/output.o
EXEPATH = $(dir)/exe/output.exe

# $(EXEPATH): $(OBJPATH)
# 	g++ $(OBJPATH) -o $(EXEPATH)

# $(OBJPATH): $(MAINPATH)
# 	g++ -c $(MAINPATH) -o $(OBJPATH)



# $(EXEPATH): $(OBJPATH)
# 	g++ $(OBJPATH) -o $(EXEPATH)

$(EXEPATH): $(patsubst %, $(MAINPATH)/%, $(notdir $(wildcard $(MAINPATH)/*.cpp)))
	g++ $^ -o $@

run:
	./$(EXEPATH)

clean:
	del $(dir)\\exe\\*.exe .\\files\\*.txt



# Usage information
help:
	@echo "Usage: make DIR=<directory_name>"
	@echo "Compiles all .c files in the specified directory into a single object file."

#target: dependencies
#	action