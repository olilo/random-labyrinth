#!/bin/sh
OLDPWD=$PWD
cd $(dirname $0)

ant

cd $OLDPWD
