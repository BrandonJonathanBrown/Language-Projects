#!/bin/bash

#Author Brandon Jonathan Brown 

echo "Please enter the name for the folder to be encrypted: "
read folder_name

if [[ -z "$folder_name" ]]; then
    echo "Error: No folder name provided. Exiting."
    exit 1
fi

if [ ! -d "$folder_name" ]; then
    echo "Error: The folder '$folder_name' does not exist. Exiting."
    exit 1
fi

if ! command -v zip &> /dev/null; then
    echo "Error: zip command is not available. Please install zip and try again."
    exit 1
fi

if zip -er "${folder_name}.zip" "$folder_name"; then
    # If zip command succeeds, print success message
    echo "Encryption successful. Created '${folder_name}.zip'."
else
    zip_status=$?
    echo "Error: Failed to encrypt folder '$folder_name'. zip exited with status $zip_status."
    exit $zip_status
fi
