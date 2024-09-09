# C++ Projects
This directory will contain all the C++ Projects.

## Directory Structure
The main directory will contain the individual project folders. I will try to include README files in the folders itself describing the project.

There will be for now a single Makefile to compile, run and clean all the projects. Might modify that later.

And common files or generated files will be kept outside the project directories. Working on how to solve this issue.

High-level view of directory structure:
```
NITR-DSAD/
|
|----C++/
|    |------files/                 # common files or generated files
|    |
|    |------<Project-Directory>/   # project folder
|    |       |----exe/              # stores executables
|    |       |----obj/              # stores compiled object files
|    |       |----src/              # stores source code files
|    |       |----headers/          # stores source code files
|    |
|    |----Makefile               # common makefile for projects
```

## Usage Instructions
Follow the instructions below to run a project in this folder. For now the only one created is Hashing project contains the full project now. Lets use that as our example.

1. **Compile File in a Project** 
``` $ make dir=<project-name> exe=<executable-name>```

2. **Run a File in a Project**
``` $ make dir=<project-name> exe=<executable-name> run```

3. **Clean the project**
``` $ make dir=<project-name> clean```

4. **Automate the execution and cleaning process fully** ```$ ./execute.sh``` OR ```C:> ./execute.bat```