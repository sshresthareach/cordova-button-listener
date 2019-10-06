<!---
	Licensed to the Apache Software Foundation (ASF) under one
	or more contributor license agreements.  See the NOTICE file
	distributed with this work for additional information
	regarding copyright ownership.  The ASF licenses this file
	to you under the Apache License, Version 2.0 (the
	"License"); you may not use this file except in compliance
	with the License.  You may obtain a copy of the License at

	  http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing,
	software distributed under the License is distributed on an
	"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
	KIND, either express or implied.  See the License for the
	specific language governing permissions and limitations
	under the License.
-->

button-listener
=======================

Cordova plugin which notifies when the user presses buttons of the device. For that, it adds the following `window` event:

* buttonslistener

## Installation

``` bash
cordova plugin add https://github.com/sshresthareach/cordova-button-listener.git
```

## buttonlistener

This event fires when the user presses the button of the device.

- __name__: The button that the user has pressed _(String)_

Applications have to use `window.addEventListener`to attach this event listener once the `deviceready`event fires.

### Supported Platforms

- Android

### Example
``` js
	window.addEventListener("buttonlistener", onButtonListener, false);
	
	function onButtonListener(info){
		console.log("Button pressed: " + info.name);
	}
```
