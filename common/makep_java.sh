#!/bin/bash

files=""
for proto in  *.proto
do
	files+=" "$proto
done

protoc --java_out=../server/physics/src $files