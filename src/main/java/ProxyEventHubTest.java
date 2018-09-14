import com.microsoft.azure.eventhubs.*;
import com.microsoft.azure.eventhubs.EventHubClient;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyEventHubTest
{
    private static final String PROXY_HOSTNAME_NAME = "PROXY_HOSTNAME";
    private static final String PROXY_PORT_NAME = "PROXY_PORT";
    private static final String EVENT_HUB_CONNECTION_STRING_NAME = "EVENT_HUB_CONNECTION_STRING";

    private static String CONNECTION_STRING = System.getenv(EVENT_HUB_CONNECTION_STRING_NAME);

    public static void main (String[] args) throws EventHubException, IOException {
        EventHubClient.setProxyHostName(System.getenv(PROXY_HOSTNAME_NAME));
        EventHubClient.setProxyHostPort(Integer.parseInt(System.getenv(PROXY_PORT_NAME)));

        final ConnectionStringBuilder connStr = new ConnectionStringBuilder(CONNECTION_STRING);
        connStr.setTransportType(TransportType.AMQP_WEB_SOCKETS);

        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        final EventHubClient ehClient = EventHubClient.createSync(connStr.toString(), executorService);
        PartitionSender sender = ehClient.createPartitionSenderSync("0");

        for (int i = 0; i < 10; i++)
        {
            sender.sendSync(EventData.create( ("test message " + Integer.toString(i)).getBytes()));
            System.out.println("Sent test message " + Integer.toString(i));
            System.in.read();
        }

        ehClient.closeSync();
        executorService.shutdown();
    }
}
