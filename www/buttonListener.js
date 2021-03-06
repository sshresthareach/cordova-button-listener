/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

/**
* 	This class lets handle the events emitted by the buttons of the device
*/
var cordova = require('cordova'),
    exec = require('cordova/exec');

/**
 * Button Listener
 * @class ButtonListener
 */
var ButtonListener = function () {

    /**
     * List of eventHandlers listening
     * @member {array} eventHandlers
     * @memberOf ButtonListener
     * @instance
     */
    this.eventHandlers = [];

    /**
     * @member {array} handlers
     * @memberOf ButtonListener
     * @instance
     */
    this.listening = false;
};

/**
 * Starts listener
 * @memberof ButtonListener
 */
ButtonListener.prototype.start = function () {
    exec(ButtonListener.buttonListener.bind(this), ButtonListener.errorListener.bind(this), "ButtonListener", "start", []);
    this.listening = true;
};

/**
 * Stops listener
 * @memberof ButtonListener
 */
ButtonListener.prototype.stop = function () {
    exec(null, null, "ButtonListener", "stop", []);
    this.listening = false;
};

/**
 * Reset listeners
 * @memberof ButtonListener
 */
ButtonListener.prototype.reset = function () {
    console.log('CV: Resetting listener');
    exec(ButtonListener.buttonListener.bind(this), ButtonListener.errorListener.bind(this), "ButtonListener", "start", []);
};

/**
 * adds a listener for a keyCode
 * @param {number} keyCode
 * @param {function} callback
 */
ButtonListener.prototype.addListener = function (keyCode, callback) {
    if (typeof keyCode !== "number") {
        throw new Error('KeyCode has to be a number.');
    }

    const eventHandler = {
        keyCode: keyCode,
        callback: callback
    }

    this.eventHandlers.push(eventHandler);

    if (this.listening === false) {
        this.start();
    }
}

/**
 * Removes a button listener for a keyCode
 * @param {number} keyCode
 */
ButtonListener.prototype.removeListener = function (keyCode) {
    if (typeof keyCode !== "number") {
        throw new Error('KeyCode has to be a number.');
    }

    var keyIndex = searchIndexByKeyCode(keyCode, this.eventHandlers);

    if (keyIndex === -1) {
        throw new Error('KeyCode not found.');
    }

    this.eventHandlers.splice(keyIndex, 1);

    if (this.eventHandlers.length === 0) {
        this.stop();
    }
}

/**
 * Search index of the handler
 * @param {number} keyCode 
 * @param {Array} eventHandlers 
 */
function searchIndexByKeyCode(keyCode, eventHandlers) {
    for (var i = 0; i < eventHandlers.length; i++) {
        if (eventHandlers[i].keyCode === keyCode) {
            return i;
        }
    }

    return -1;
}

/**
* 	Callback used when the user presses the volume button of the device
*
*	@param {Object} info	keys: name
*/
ButtonListener.prototype.buttonListener = function (info) {
    if (info) {
        // cordova.fireWindowEvent("buttonlistener", info);
        var keyIndex = searchIndexByKeyCode(info.keyCode, this.eventHandlers);
        this.eventHandlers[keyIndex].callback(info);
    }
};

/**
* 	Error callback for listener start
*/
ButtonListener.prototype.errorListener = function (e) {
    console.log("CV: Error initializing ButtonListener: " + e);
};

var ButtonListener = new ButtonListener();

module.exports = ButtonListener;
