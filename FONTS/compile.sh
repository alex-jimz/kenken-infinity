#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Run the Gradle build
./gradlew build

# Copy the build directory to the desired location
# The -r option is for recursive copy, and -f forces the copy without asking for confirmation
# Redirecting output to /dev/null to suppress it
cp -r ./build ../EXE/build > /dev/null

# Remove the build directory
rm -rf ./build