natural like creation language for bpmn-process-models, based on java and bpmn 2.0 xml
*by Niklas Kiefer*

**example bcnl-statement:**

> lets create a process with startevent signed startEvent1 called startevent1 with endevent signed endevent1 called endevent1 with sequenceflow comesfrom startevent1 goesto endevent1


----------

BNCL-structure (08.05.2016)
---------------------------

everything in bncl is case insensitive, but it is always a good idea to write it in small letters.

    lets create a process

  is a keyword group that signs the beginning of a bncl-statement

	with

is a keyword that signs the beginning of a process element

 **elements:**

 - startevent
 - endevent
 - usertask

**attributes:**

 - called
 - signed (required)

    sequenceworkflow

signs a sequence workflow between process elements. **comesfrom** with given id signs the fromElement, **goesto** signes the toElement.