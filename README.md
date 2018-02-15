# Papart Application example

This example shows how to create Papart/Processing application compiled with Maven. 

Run the example:
``` bash
cd example
mvn compile
mvn exec:java -Dexec.mainClass="tech.lity.rea.exemple.SeeThrough"
``` 


## Requirements. 

* You need to the the environment varible `SKETCHBOOK` to your sketchbook folder, so that PapARt can load the configuration files and markers. E.g. on linux / OSX: 
``` bash
export SKETCHBOOK=$HOME/sketchbook 
```
In windows you can find many guides: [guide1](https://www.computerhope.com/issues/ch000549.htm). 
* You need to install OpenNI/librealsense/libfreenect if you use a depth camera. 


## Deploy on a distant machine 

#### Maven deploy 

Run the distant example:
``` bash
cd exampleDeploy
mvn compile
``` 

The launch is quite slow for now, any help for that would be great. 

#### Netbeans deploy

Load the projet `exampleDeployNetbeans` in Netbeans and [deploy on network](exampleDeployNetbeans). 
