#!/bin/bash

if [ -e ../server/thrift/gen ]
then
  chmod -R u+w ../server/thrift/gen
  rm -rf ../server/thrift/gen
fi
mkdir ../server/thrift/gen

for thrift in  *.thrift
do
  thrift --gen java -out ../server/thrift/gen $thrift
done

chmod -R -w ../server/thrift/gen/*
