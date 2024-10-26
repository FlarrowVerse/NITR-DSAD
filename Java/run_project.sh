#!/bin/bash

# Check if project name is provided
if [ -z "$1" ]; then
  echo "Please provide a project name (e.g., project1 or project2)"
  exit 1
fi

# Map the project name to its directory path
case "$1" in
  MusicPlaylistManager)
    PROJECT_PATH="MusicPlaylistManager"
    ;;
  BrowserHistoryManager)
    PROJECT_PATH="BrowserHistoryManager"
    ;;
  *)
    echo "Unknown project name: $1"
    exit 1
    ;;
esac

# Navigate to the project directory and run the commands
cd "$PROJECT_PATH" || exit
mvn clean install -U
mvn exec:java -pl driver
