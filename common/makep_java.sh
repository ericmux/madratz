#!/bin/bash

if [ -e ../server/protos/gen ]
then
  chmod -R u+w ../server/protos/gen
  rm -rf ../server/protos/gen
fi
mkdir ../server/protos/gen

for thrift in  *.thrift
do
  thrift --gen java -out ../server/protos/gen $thrift
done

chmod -R -w ../server/protos/gen/*
