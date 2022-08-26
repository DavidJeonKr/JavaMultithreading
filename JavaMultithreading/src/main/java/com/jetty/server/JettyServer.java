package com.jetty.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;

public class JettyServer {
    private Server server;

    public void start() throws Exception {
        server = new Server();
        Connector connector = new Connector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[] {connector});
    }
}
