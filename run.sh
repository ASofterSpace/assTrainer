#!/bin/bash

cd `dirname "$0"`

java -classpath "`dirname "$0"`/bin" -Xms1m -Xmx1024m com.asofterspace.assTrainer.AssTrainer "$@" &
