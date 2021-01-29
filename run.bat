@echo off

cd /D %~dp0

start "assTrainer" javaw -classpath "%~dp0\bin" -Xms16m -Xmx1024m com.asofterspace.assTrainer.AssTrainer %*

exit