# Black Rook Math

Copyright (c) 2019 Black Rook Software. All rights reserved.  
[https://github.com/BlackRookSoftware/Math](https://github.com/BlackRookSoftware/Math)

### Required Libraries

NONE

### Required Java Modules

[java.base](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/module-summary.html)  

### Introduction

This library contains classes for mathematical functions or geometric calculations.
These classes can be redistributed in whole or in part.

### Why?

This has been done before - several times, even - but this one is MIT licensed and acknowledges
that not all of it (at once) will be useful.  

### Library

Contained in this release is a series of classes that should be used for common math or geometry. 
The javadocs contain basic outlines of each package's contents.

### Compiling with Ant

To compile this library with Apache Ant, type:

	ant compile

To make Maven-compatible JARs of this library (placed in the *build/jar* directory), type:

	ant jar

To make Javadocs (placed in the *build/docs* directory):

	ant javadoc

To compile main and test code and run tests (if any):

	ant test

To make Zip archives of everything (main src/resources, bin, javadocs, placed in the *build/zip* directory):

	ant zip

To compile, JAR, test, and Zip up everything:

	ant release

To clean up everything:

	ant clean
	
### Other

This program and the accompanying materials are made available under the 
terms of the MIT License which accompanies this distribution.

A copy of the MIT License should have been included in this release (LICENSE.txt).
If it was not, please contact us for a copy, or to notify us of a distribution
that has not included it. 

This contains code copied from Black Rook Base, under the terms of the MIT License (docs/LICENSE-BlackRookBase.txt).
