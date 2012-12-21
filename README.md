XLIFF Translation Utils
=======================

**What is?**

XLIFF Translation utils is a Java library that allows one to read and write
[XLIFF](http://docs.oasis-open.org/xliff/xliff-core/xliff-core.html) files for translation purposes only and with a simple schema.

**How to use it?**

1.   Include it on your project. Download the Jar
([here](http://link_here.please)), or use maven:
	
	    <dependency>
	      <groupId>com.lyncode</groupId>
	      <artifactId>xliff</artifactId>
	      <version>1.0.0</version>
	    </dependency>
	

2.  Create a XLIFF file ([example
here](https://github.com/lyncode/xliff-translate/blob/master/sample/example1.xliff)).

3.  Code it!

**Read**
	
	    InputStream is = new FileInputStream("<path-to-xliff>");
	    XLIFF x = XliffUtils.read(is);
 	    System.out.println(x.getTarget("Search this site"));
	
**Write**

	    XLIFF out = ...
	    OutputStream os = new FileOutputStream("<path-to-xliff>");
	    XliffUtils.write(out, os);

- - -

### License

Copyright 2012 **Lyncode**

Licensed under the Apache License, Version 2.0 (the "License");  you may not use
this file except in compliance with the License. You may obtain a copy of the
License at

        http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
