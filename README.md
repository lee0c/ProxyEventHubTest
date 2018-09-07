# Event Hub Proxy Test

This will send 10 test messages to a specified Event Hub instance through a proxy.
After sending each message it will wait for user input on the keyboard.

This sample uses the temporary [proxy support snapshot for Event 
Hubs](https://github.com/Azure/azure-event-hubs-java/tree/websocket.with.1_0/snapshots/com/microsoft/azure/azure-eventhubs/1.1.0-SNAPSHOT).

## Running the sample  

In order to run this, set 3 environment variables:

* **EVENT_HUB_CONNECTION_STRING** should be in the format of 
`Endpoint=----NAMESPACE_ENDPOINT------;EntityPath=----EVENTHUB_NAME----;SharedAccessKeyName=----KEY_NAME----;SharedAccessKey=----KEY_VALUE----`.
* **PROXY_HOSTNAME** and **PROXY_PORT** should be the address and port of the proxy
you are using.