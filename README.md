![alt text](logo.png)

![Build Status](https://github.com/pinussilvestrus/bncl/workflows/ci/badge.svg) [![codecov](https://codecov.io/gh/pinussilvestrus/bncl/branch/develop/graph/badge.svg)](https://codecov.io/gh/pinussilvestrus/bncl)
[![Version](https://img.shields.io/github/release/pinussilvestrus/bncl.svg)](https://github.com/pinussilvestrus/bncl/releases)

Software by [Niklas Kiefer](https://www.niklaskiefer.de/), [MIT License](https://github.com/pinussilvestrus/bncl/blob/develop/LICENSE)

**Table of Contents**

- [Introduction](#introduction)
- [Auto-Layout](#autolayout)
- [Demo](#demo)
- [Development](#development)
- [BNCL-structure](#bncl-structure)

## Introduction

**Bncl** (binkel) ist a natural like creation language for bpmn-process-models, based on java and bpmn 2.0 xml
Powered by Camunda's [BPMN Model API](https://github.com/camunda/camunda-bpmn-model) .
To execute the CLI and convert a bncl statement to bpmn, download the current [release-version](https://github.com/pinussilvestrus/bncl/releases).

```shell
$ java -jar bncl-[versionNr.].jar
```


### For more information, see the [wiki](https://github.com/pinussilvestrus/bncl/wiki)!

**example bncl-statement:**

> lets create a process with startevent signed startEvent1 called message incoming with usertask signed usertask1 called do something with usertask signed usertask2 with parallelgateway signed gateway1 with parallelgateway signed gateway2 with sequenceflow comesfrom startevent1 goesto gateway1 with sequenceflow comesfrom gateway1 goesto usertask1 with sequenceflow comesfrom gateway1 goesto usertask2 with sequenceflow comesfrom usertask1 goesto gateway2 with sequenceflow comesfrom usertask2 goesto gateway2 with endevent signed endevent1 called terminated with sequenceflow comesfrom gateway2 goesto endevent1


If it was successful, a .xml and .bpmn file is generated in same folder. Open one file in your favorite BPMN-Editor, like
 - [Camunda Modeler](https://camunda.org/bpmn/tool/)
 - [Signavio](http://www.signavio.com/)
 - [BPMN.io](https://bpmn.io/)
 - [Yaoqiang Modeler](https://sourceforge.net/projects/bpmn/)

**Note:** The Bncl-to-BPMN-converter only generates a valid xml-document in the [BPMN-2.0-Schema](http://activiti.org/userguide/index.html#whatIsBpmn).
Not all editors supports a bpmn-file without rendering information (e.g.

```xml
<bpmndi:BPMNDiagram id="BPMNDiagram_1">
    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">
      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">
        <dc:Bounds x="173" y="102" width="36" height="36" />
      </bpmndi:BPMNShape>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
```

## Auto-layout

There is also a possibility to add these di-elements to the generated bpmn. Either you use the [demo-webpage](#demo) or the [bncl-autolayout repository](https://github.com/pinussilvestrus/bncl-autolayout).

## Demo

There is also a web demo available: http://bncl.de. On the web demo, the bncl-parser also autolayouts the created bpmn-string so it can used in several BPMN modeler. Please feel free to try it out!

Or try it out locally:
```sh
$ sh demo_server_startup.sh
```

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

everything in bncl is case insensitive, but it is always a good idea to write it in small letters.

    lets create a process

  is a keyword group that signs the beginning of a bncl-statement

	with

is a keyword that signs the beginning of a process element

    sequenceworkflow

signs a sequence workflow between process elements. **comesfrom** with given id signs the fromElement, **goesto** signes the toElement.

See the [wiki](https://github.com/pinussilvestrus/bncl/wiki) for all element types!


## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fpinussilvestrus%2Fbncl.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fpinussilvestrus%2Fbncl?ref=badge_large)

Contains parts ([bpmn-js](https://github.com/bpmn-io/bpmn-js), [bpmnlint](https://github.com/bpmn-io/bpmnlint), [bpmn-js-bpmnlint](https://github.com/bpmn-io/bpmn-js-bpmnlint)) released under the [bpmn.io license](http://bpmn.io/license).