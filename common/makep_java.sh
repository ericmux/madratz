#!/bin/bash

files=""
for proto in  *.proto
do
	files+=" "$proto
done

if [ -e ../server/protos/src ]
then
	chmod -R u+w ../server/protos/src
	rm -rf ../server/protos/src
fi
mkdir ../server/protos/src

protoc --java_out=../server/protos/src $files
chmod -R -w ../server/protos/src/*
