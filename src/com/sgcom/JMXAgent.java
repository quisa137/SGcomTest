package com.sgcom;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

public class JMXAgent {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.rmi.server.randomIDs", "true");

        System.out.println("Create RMI registry on port 3000");
        LocateRegistry.createRegistry(3000);

        System.out.println("Get the platform's MBean server");
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        System.out.println("Initialize the environment map");
        HashMap<String, Object> env = new HashMap<String, Object>();

        SslRMIClientSocketFactory csf = new SslRMIClientSocketFactory();
        SslRMIServerSocketFactory ssf = new SslRMIServerSocketFactory();
        env.put("jmx.remote.rmi.client.socket.factory", csf);
        env.put("jmx.remote.rmi.server.socket.factory", ssf);
        env.put("jmx.remote.x.password.file", "password.properties");
        env.put("jmx.remote.x.access.file", "access.properties");

        System.out.println("Create an RMI connector server");
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:3000/jmxrmi");
        JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);

        System.out.println("Start the RMI connector server");
        cs.start();
    }
}
