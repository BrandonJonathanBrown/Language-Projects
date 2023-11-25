#!/bin/bash

echo "Please enter the name for folder to be encrypted:"
read _name
if zip -er "$_name".zip *; then
   echo $? "Done!"
fi
