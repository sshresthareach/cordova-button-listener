/*
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
*/

package com.reach;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;


public class ButtonListener extends CordovaPlugin implements OnKeyListener {

	private static final String ButtonListener_LOG= "ButtonListener";	
	private CallbackContext listenerCallbackContext;



	/**
	* 	Constructor of the ButtonListener class
	* 
	* 	@date		10/04/2019
	* 	@version	0.0.1
	* 	@author	Shubha Shrestha(sshresthareach@gmail.com) 
	*/
	public ButtonListener(){
		listenerCallbackContext = null;
	}


	/**
	* 	Method which executes the Javascript request
	*
	*	@param		action: String object with the action to execute
	*	@param		args: JSONArray object with the arguments of the request
	*	@param		callbackContext: CallbackContext object for call back into Javascript
	*
	*	@return		"boolean" which indicates if the action is valid (true) or not (false)
	* 
	* 	@date		10/12/2016
	* 	@version	0.0.3
	* 	@author	ManuelDeveloper(manueldeveloper@gmail.com) 
	*/
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		// Check the action
		if( action.equals("start") ){

			// Check if the plugin is listening button events
			if( this.listenerCallbackContext != null ){

				callbackContext.error("Button listener already running");
				return true;	
			}

			// Get the reference to the callbacks and start the listening process
			this.listenerCallbackContext = callbackContext;
			this.webView.getView().setOnKeyListener(this);

			// Don't return any result now
			PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
			pluginResult.setKeepCallback(true);
			this.listenerCallbackContext.sendPluginResult(pluginResult);
			return true;
		}
		else if( action.equals("stop") ){

			// Erase the callbacks reference and stop the listening process
			sendSignal(new JSONObject(), false); // release status callback in Javascript side
			this.listenerCallbackContext= null;
			this.webView.getView().setOnKeyListener(null);
			callbackContext.success();
			return true;			
		}

		return false;
	}

	
	/**
	* 	Overwritten method for Android application lifecycle. It stops the key events listening process
	*/
	public void onDestroy(){

		// Stop the listening process
		this.webView.getView().setOnKeyListener(null);
	}


	/**
	* 	Overwritten method for Android application lifecycle. It stops the key events listening process
	*/
	public void onReset(){
		// Stop the listening process
		this.webView.getView().setOnKeyListener(null);
	}

	/**
	* 	Overwritten method to receive the Android key events
	*
	*	@param		view: View object who emit the signal (CordovaWebView)
	*	@param		keyCode: int with the identifier of the pressed key
	*	@param		keyEvent: KeyEvent object with the information of the event
	*
	*	@return		"boolean" which indicates if the listener has consumed the event (true) or not (false) [Always false to allow that the event spreading]
	*/
	public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
		JSONObject info = new JSONObject();
        try{
            info.put("name", "onKey");
            info.put("keyCode", keyCode);
            info.put("action", keyEvent.getAction());
            info.put("deviceId", keyEvent.getDeviceId());
            info.put("displayLabel", keyEvent.getDisplayLabel());
            info.put("eventTime", keyEvent.getEventTime());
            info.put("flags", keyEvent.getFlags());
            info.put("repeatCount", keyEvent.getRepeatCount());
            info.put("longPress", keyEvent.isLongPress());
            info.put("string", keyEvent.toString());            
            sendSignal(info, true);
            return true;
        }
        catch(JSONException ex){
            Log.e(ButtonListener_LOG, ex.getMessage());
        }

		return false;
	}

	// public boolean onKeyDown(int keyCode, KeyEvent event){
	// 	JSONObject info = new JSONObject();
	// 	try{
	// 		info.put("signal", "Something");
	// 		info.put("keyCode", keyCode);
	// 		sendSignal(info, true);
	// 		return true;
	// 	}
	// 	catch(JSONException ex){
	// 		Log.e(ButtonListener_LOG, ex.getMessage());
	// 	}
	// 	return false;
	// }


	/**
	* 	Method which sends back a new PluginResult to Javascript
	*
	*	@param		info: JSONObject object with the information to send back
	*	@param		keepCallback: boolean which indicates if there will be more results
	*/
	private void sendSignal(JSONObject info, boolean keepCallback)
	{
		if( this.listenerCallbackContext != null ){
			PluginResult result= new PluginResult(PluginResult.Status.OK, info);
			result.setKeepCallback(keepCallback);
			this.listenerCallbackContext.sendPluginResult(result);
		}
	}
}