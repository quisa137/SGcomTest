package com.sgcom;

import java.lang.management.MemoryUsage;
import java.util.Iterator;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;

public class JMXClient {
    public static void main(String[] args) {
        try {
            String loc = "service:jmx:rmi:///jndi/rmi://localhost:9010/jmxrmi";
            JMXServiceURL url = new JMXServiceURL(loc);
            JMXConnector jmxc = javax.management.remote.JMXConnectorFactory.connect(url, null);

            MBeanServerConnection mbs_ = jmxc.getMBeanServerConnection();
            String[] domains = mbs_.getDomains();
            String[] arrayOfString1;
            int j = (arrayOfString1 = domains).length;
            for (int i = 0; i < j; i++) {
                String d = arrayOfString1[i];
                System.out.println(d);
            }
            System.out.println("\n");

            ObjectName stdMBeanName = new ObjectName("java.lang:type=OperatingSystem");

            System.out.println("System Arch = " + mbs_.getAttribute(stdMBeanName, "Arch"));
            System.out.println("Number of Processors = " + mbs_.getAttribute(stdMBeanName, "AvailableProcessors"));
            System.out.println("OS Name = " + mbs_.getAttribute(stdMBeanName, "Name"));
            System.out.println("OS Version = " + mbs_.getAttribute(stdMBeanName, "Version"));
            AttributeList list = mbs_.getAttributes(stdMBeanName, new String[] { "ProcessCpuLoad", "SystemCpuLoad" });
            if (!list.isEmpty()) {
                Attribute attr = (Attribute) list.get(0);
                Double val = (Double) attr.getValue();

                System.out.println("JVM CPU Load : " + (int) (val.doubleValue() * 1000.0D) / 10.0D);

                attr = (Attribute) list.get(1);
                val = (Double) attr.getValue();
                System.out.println("Whole CPU Load : " + (int) (val.doubleValue() * 1000.0D) / 10.0D);
            }

            stdMBeanName = new ObjectName("java.lang:type=Runtime");

            System.out.println("Name = " + mbs_.getAttribute(stdMBeanName, "Name"));
            System.out.println("Spec Name = " + mbs_.getAttribute(stdMBeanName, "SpecName"));
            System.out.println("VM Version = " + mbs_.getAttribute(stdMBeanName, "VmVersion"));

            stdMBeanName = new ObjectName("java.lang:type=Memory");

            MemoryUsage musage_ = MemoryUsage.from((CompositeData) mbs_.getAttribute(stdMBeanName, "HeapMemoryUsage"));
            System.out.println("------------------------------------------------------------------------------------");
            System.out.println("MemoryMBean 정보 출력 : ");
            System.out.println("> HeapMemoryUsage-init = " + (float) musage_.getInit() / 1024.0F + " kbytes");
            System.out.println("> HeapMemoryUsage-max = " + musage_.getMax());
            System.out.println("> HeapMemoryUsage-used = " + (float) musage_.getUsed() / 1024.0F + " kbytes");
            System.out.println("> HeapMemoryUsage-committed = " + (float) musage_.getCommitted() / 1024.0F + " kbytes");

            musage_ = MemoryUsage.from((CompositeData) mbs_.getAttribute(stdMBeanName, "NonHeapMemoryUsage"));
            System.out.println("\n> NonHeapMemoryUsage-init = " + (float) musage_.getInit() / 1024.0F + " kbytes");
            System.out.println("> NonHeapMemoryUsage-max = " + musage_.getMax());
            System.out.println("> NonHeapMemoryUsage-used = " + (float) musage_.getUsed() / 1024.0F + " kbytes");
            System.out.println(
                    "> NonHeapMemoryUsage-committed = " + (float) musage_.getCommitted() / 1024.0F + " kbytes");

            stdMBeanName = new ObjectName("java.lang:type=MemoryPool,*");
            Set<?> pools_ = mbs_.queryNames(null, stdMBeanName);

            Iterator<?> itr_ = pools_.iterator();

            while (itr_.hasNext()) {
                Object obj_ = itr_.next();
                ObjectName objName_ = (ObjectName) obj_;

                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(mbs_.getAttribute(objName_, "Name") + "Pool 정보 출력 :");
                System.out.println("Memory Type = " + mbs_.getAttribute(objName_, "Type"));
                System.out.println("Memory Peak Usage:");

                musage_ = MemoryUsage.from((CompositeData) mbs_.getAttribute(objName_, "PeakUsage"));
                System.out.println("> MemoryUsage-init = " + (float) musage_.getInit() / 1024.0F + " kbytes");
                System.out.println("> MemoryUsage-max = " + (float) musage_.getMax() / 1024.0F + " kbytes");
                System.out.println("> MemoryUsage-used = " + (float) musage_.getUsed() / 1024.0F + " kbytes");
                System.out.println("> MemoryUsage-committed = " + (float) musage_.getCommitted() / 1024.0F + " kbytes");
                System.out.println("\nMemory Current Usage:");

                musage_ = MemoryUsage.from((CompositeData) mbs_.getAttribute(objName_, "Usage"));
                System.out.println("> MemoryUsage-init = " + (float) musage_.getInit() / 1024.0F + " kbytes");
                System.out.println("> MemoryUsage-max = " + (float) musage_.getMax() / 1024.0F + " kbytes");
                System.out.println("> MemoryUsage-used = " + (float) musage_.getUsed() / 1024.0F + " kbytes");
                System.out.println("> MemoryUsage-committed = " + (float) musage_.getCommitted() / 1024.0F + " kbytes");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
