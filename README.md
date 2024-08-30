# NIT Rourkela-Data Structures and Alogrithm Design
All codes related to Data Structures and Algorithm Design class in NIT Rourkela will be uploaded. I apologize in advance for any delay in uploading anything or wrong information. Let me know if there is anything worth correcting and I will do it as soon as possible.

Enjoy!

## Directory Structure
The main directory will contain the individual project folders. I will try to include README files in the folders itself describing the project.

There will be for now a single Makefile to compile, run and clean all the projects. Might modify that later.

And common files or generated files will be kept outside the project directories. Working on how to solve this issue.

High-level view of directory structure:
```
NITR-DSAD/
|
|----files/                 # common files or generated files
|
|----<Project-Directory>/   # project folder
|    |----exe/              # stores executables
|    |----obj/              # stores compiled object files
|    |----src/              # stores source code files
|
|----Makefile               # common makefile for projects
```

## Dependencies
This project will contain C++ codes for now. Will update here if this changes later on. For running codes in this project, C++ compiler, debugger, header files, libraries, etc. will be needed. So based on your operating system install a suitable compiler. Tons of tutorials online.

## Usage Instructions
Follow the instructions below to run a project in this folder. For now the only one created is Hashing project which only contains one file that generates test data. Lets use that as our example.

1. **Compile File in a Project** 
``` $ make dir=<project-name> prog=<program-name>```

2. **Run a File in a Project**
``` $ make dir=<project-name> prog=<program-name> run```

3. **Clean the project**
``` $ make dir=<project-name> prog=<program-name> clean```
