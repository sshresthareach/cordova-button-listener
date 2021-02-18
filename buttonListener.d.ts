// Type definitions for cordova.plugins.buttonListener

/// <reference types="cordova"/>

interface ButtonListener {
    start(): void;
    stop(): void;
    reset(): void;
    addListener(
        keyCode: number,
        callback: (event: Event) => void
    ): void;
    removeListener(keyCode: number): void;
    buttonListener(info: {keycode: number}): void;
    errorListener(error: string): void;
}

interface CordovaPlugins {
    buttonListener: ButtonListener;
}
