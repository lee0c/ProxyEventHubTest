import com.microsoft.azure.eventhubs.*;
import com.microsoft.azure.eventhubs.EventHubClient;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyEventHubTest
{
    private static final String EVENT_HUB_CONNECTION_STRING_ENV_VAR = "EVENT_HUB_CONNECTION_STRING";
    private static final String PROXY_HOSTNAME_ENV_VAR = "PROXY_HOSTNAME";
    private static final String PROXY_PORT_ENV_VAR = "PROXY_PORT";

    private static String CONNECTION_STRING = System.getenv(EVENT_HUB_CONNECTION_STRING_ENV_VAR);
    private static String PROXY_HOSTNAME = System.getenv(PROXY_HOSTNAME_ENV_VAR);
    private static int PROXY_PORT = Integer.valueOf(System.getenv(PROXY_PORT_ENV_VAR));

    public static void main (String[] args) throws EventHubException, IOException {
        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                LinkedList<Proxy> proxies = new LinkedList<>();
                proxies.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOSTNAME, PROXY_PORT)));
                return proxies;
            }
            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                // no-op
            }
        });

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
