# Event Hub Proxy Test

This will send 10 test messages to a specified Event Hub instance through a proxy.
After sending each message it will wait for user input on the keyboard.

This sample uses 
[proxy support for Azure Event Hubs](https://github.com/Azure/azure-event-hubs-java/commit/a85a662d233408e683cbc641dd9100bbacbaf04d)

## Running the sample  

In order to run this, set 3 environment variables:

* **EVENT_HUB_CONNECTION_STRING** should be in the format of 
`Endpoint=----NAMESPACE_ENDPOINT------;EntityPath=----EVENTHUB_NAME----;SharedAccessKeyName=----KEY_NAME----;SharedAccessKey=----KEY_VALUE----`.
* **PROXY_HOSTNAME** and **PROXY_PORT** should be the address and port of the proxy
you are using.