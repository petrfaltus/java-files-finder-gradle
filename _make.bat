@echo off

call _clean.bat

call gradle.bat assemble build createExe
