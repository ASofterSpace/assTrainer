#!/bin/bash

echo "Re-building with target Java 7 (such that the compiled .class files will be compatible with as many JVMs as possible)..."

cd src

# build build build!
javac -encoding utf8 -d ../bin -bootclasspath ../other/java7_rt.jar -source 1.7 -target 1.7 @sourcefiles.list

cd ..



echo "Creating the release file assTrainer.zip..."

mkdir release

cd release

mkdir assTrainer

# copy the main files
cp -R ../bin assTrainer
cp ../UNLICENSE assTrainer
cp ../README.md assTrainer
cp ../run.sh assTrainer
cp ../run.bat assTrainer

# convert \n to \r\n for the Windows files!
cd assTrainer
awk 1 ORS='\r\n' run.bat > rn
mv rn run.bat
cd ..

# create a version tag right in the zip file
cd assTrainer
version=$(./run.sh --version_for_zip)
echo "$version" > "$version"
cd ..

# zip it all up
zip -rq assTrainer.zip assTrainer

mv assTrainer.zip ..

cd ..
rm -rf release

echo "The file assTrainer.zip has been created in $(pwd)"
