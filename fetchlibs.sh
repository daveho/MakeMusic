#! /bin/bash

jars="http://central.maven.org/maven2/org/reflections/reflections/0.9.9/reflections-0.9.9.jar
	http://central.maven.org/maven2/com/google/guava/guava/15.0/guava-15.0.jar
	http://central.maven.org/maven2/org/javassist/javassist/3.18.2-GA/javassist-3.18.2-GA.jar"

for j in $jars; do
	if [ ! -e "lib/$(basename $j)" ]; then
		echo "Downloading $j..."
		curl  $j > lib/$(basename $j)
		echo "done"
	fi
done
