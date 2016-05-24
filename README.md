![alt text](logo.png)

[![Build Status](https://travis-ci.org/pinussilvestrus/bncl.svg?branch=master)](https://travis-ci.org/pinussilvestrus/bncl) [![Code Coverage](https://img.shields.io/codecov/c/github/pinussilvestrus/bncl/master.svg)](https://codecov.io/github/pinussilvestrus/bncl?branch=master) [![Version](https://img.shields.io/github/release/pinussilvestrus/bncl.svg)](https://github.com/pinussilvestrus/bncl/releases)

Software by [Niklas Kiefer](https://www.niklaskiefer.de/) ([andserve](https://andserve.net/))

**Table of Contents**

- [Introduction](#introduction)
- [Development](#development)
- [BNCL-structure](#bncl-structure)

## Introduction

**Bncl** (binkel) ist a natural like creation language for bpmn-process-models, based on java and bpmn 2.0 xml
Powered by Camunda's [BPMN Model API](https://github.com/camunda/camunda-bpmn-model) .
To execute the CLI and convert a bncl statement to bpmn, download the current [release-version](https://github.com/pinussilvestrus/bncl/releases).

```shell
$ java -jar bncl-[versionNr.].jar
```

**example bncl-statement:**

> lets create a process with startevent signed startEvent1 called startevent1 with usertask signed usertask1 called dosomething with usertask signed usertask2 with parallelgateway signed gateway1 with parallelgateway signed gateway2 with sequenceflow comesfrom startevent1 goesto gateway1 with sequenceflow comesfrom gateway1 goesto usertask1 with sequenceflow comesfrom gateway1 goesto usertask2 with sequenceflow comesfrom usertask1 goesto gateway2 with sequenceflow comesfrom usertask2 goesto gateway2 with endevent signed endevent1 called terminated with sequenceflow comesfrom gateway2 goesto endevent1


If it was successful, a .xml and .bpmn file is generated in same folder. Open one file in your favorite BPMN-Editor, like
 - [Camunda Modeler](https://camunda.org/bpmn/tool/)
 - [Signavio](http://www.signavio.com/)
 - [BPMN.io](https://bpmn.io/)
 - [Yaoqiang Modeler](https://sourceforge.net/projects/bpmn/)

## Development

Bncl is a Gradle-Project!

**build a jar**
```shell
$ ./gradlew build
```

**run tests**
```shell
$ ./gradlew check
```

Feel free to contribute. Just create an issue or create a pull request.


## BNCL-structure

(12.05.2016) - STILL UNDER DEVELOPMENT

everything in bncl is case insensitive, but it is always a good idea to write it in small letters.

    lets create a process

  is a keyword group that signs the beginning of a bncl-statement

	with

is a keyword that signs the beginning of a process element

    sequenceworkflow

signs a sequence workflow between process elements. **comesfrom** with given id signs the fromElement, **goesto** signes the toElement.

**normal events:**
 - startevent
 - endevent
 - catchevent (Intermediate)
 - throevent (Intermediate)

**special events:**
 - messagestartevent
 - messageendevent
 - messagethrowevent
 - messagecatchevent

**tasks:**
 - usertask
 - sendtask (Message)
 - receivetask (Message)
 - scripttask
 - manualtask
 - businessruletask
 - servicetask

**gateways:**
 - parallelgateway

**attributes:**
 - called = name
 - signed (required) = id
