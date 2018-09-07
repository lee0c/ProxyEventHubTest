import com.microsoft.azure.eventhubs.*;
import com.microsoft.azure.eventhubs.impl.EventHubClientImpl;

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
        EventHubClientImpl.PROXY_HOST_NAME = System.getenv(PROXY_HOSTNAME_NAME);
        EventHubClientImpl.PROXY_HOST_PORT = Integer.parseInt(System.getenv(PROXY_PORT_NAME));

        final ConnectionStringBuilder connStr = new ConnectionStringBuilder(CONNECTION_STRING);
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
