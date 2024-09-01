# directory structure
SRC_PATH = $(dir)/src
HEADER_PATH = $(dir)/headers
OBJ_PATH = $(dir)/obj
EXE_PATH = $(dir)/exe

# flags
FLAGS = -I$(HEADER_PATH)

# list of source files in given project
SRCS = $(wildcard $(SRC_PATH)/*.cpp)

# list of object files in given project
OBJS = $(patsubst $(SRC_PATH)/%.cpp, $(OBJ_PATH)/%.o, $(SRCS))

# final executable
TARGET = $(EXE_PATH)/$(exe).exe

# default target
all: $(TARGET)

# link all objs to create target
$(TARGET): $(OBJS)
	g++ $(FLAGS) -o $@ $^

# compile all source files to create their corresponding object files
$(OBJ_PATH)/%.o: $(SRC_PATH)/%.cpp | $(OBJ_PATH)
	g++ $(FLAGS) -c $< -o $@

# ensure obj directory exists before compilation
$(OBJ_PATH):
	mkdir -p $(OBJ_PATH)

run:
	./$(TARGET)

clean:
	del $(dir)\\obj\\*.o $(dir)\\exe\\*.exe .\\files\\*.txt



# Usage information
help:
	@echo "Usage: make dir=<directory_name> exe=<executable_name>"
	@echo "Compiles all .cpp files in the specified directory into their object files and into a single executable."
	@echo "Usage: make dir=<directory_name> exe=<executable_name> run"
	@echo "Runs the exe file created"
	@echo "Usage: make dir=<directory_name> clean"
	@echo "Removes all the obj, exe and files files"

#target: dependencies
#	action