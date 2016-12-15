package com.sgcom;

import java.io.File;
import java.io.PrintStream;
import java.lang.management.MemoryUsage;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;

public class JMXtoJSON {
    public static void main(String[] args) {
        JMXtoJSON jj = new JMXtoJSON();
        System.out.println(jj.getMonitoringData());
    }

    public MBeanServerConnection connect() throws java.io.IOException {
        String loc = "service:jmx:rmi:///jndi/rmi://localhost:9010/jmxrmi";
        JMXServiceURL url = new JMXServiceURL(loc);
        JMXConnector jmxc = javax.management.remote.JMXConnectorFactory.connect(url, null);

        return jmxc.getMBeanServerConnection();
    }

    public String getMonitoringData() {
        try {
            MBeanServerConnection mbs = connect();
            Hashtable<String, Object> root = new Hashtable<String, Object>();
            Hashtable<String, Object> cpuinfo = new Hashtable<String, Object>();
            Hashtable<String, Object> runtime = new Hashtable<String, Object>();
            Hashtable<String, Object> heapUsage = new Hashtable<String, Object>();
            Hashtable<String, Object> nonHeapUsage = new Hashtable<String, Object>();
            Hashtable<String, Object> memoryUsage = new Hashtable<String, Object>();

            ObjectName stdMBeanName = new ObjectName("java.lang:type=OperatingSystem");
            cpuinfo.put("arch", mbs.getAttribute(stdMBeanName, "Arch"));
            cpuinfo.put("avail_processors", mbs.getAttribute(stdMBeanName, "AvailableProcessors"));
            cpuinfo.put("os_name", mbs.getAttribute(stdMBeanName, "Name"));
            cpuinfo.put("os_version", mbs.getAttribute(stdMBeanName, "Version"));
            cpuinfo.put("process_cpuload",
                    (int) (((Double) mbs.getAttribute(stdMBeanName, "ProcessCpuLoad")).doubleValue() * 1000.0D)
                            / 10.0D);
            cpuinfo.put("system_cpuload",
                    (int) (((Double) mbs.getAttribute(stdMBeanName, "SystemCpuLoad")).doubleValue() * 1000.0D) / 10.0D);
            root.put("os", cpuinfo);

            stdMBeanName = new ObjectName("java.lang:type=Runtime");
            runtime.put("vm_name", mbs.getAttribute(stdMBeanName, "Name"));
            runtime.put("vm_spec_name", mbs.getAttribute(stdMBeanName, "SpecName"));
            runtime.put("vm_version", mbs.getAttribute(stdMBeanName, "VmVersion"));
            root.put("vm", runtime);

            stdMBeanName = new ObjectName("java.lang:type=Memory");

            MemoryUsage musage_ = MemoryUsage.from((CompositeData) mbs.getAttribute(stdMBeanName, "HeapMemoryUsage"));
            heapUsage.put("init", Long.valueOf(musage_.getInit()));
            heapUsage.put("max", Long.valueOf(musage_.getMax()));
            heapUsage.put("used", Long.valueOf(musage_.getUsed()));
            heapUsage.put("committed", Long.valueOf(musage_.getCommitted()));
            memoryUsage.put("heapUsage", heapUsage);

            musage_ = MemoryUsage.from((CompositeData) mbs.getAttribute(stdMBeanName, "NonHeapMemoryUsage"));
            nonHeapUsage.put("init", Long.valueOf(musage_.getInit()));
            nonHeapUsage.put("max", Long.valueOf(musage_.getMax()));
            nonHeapUsage.put("used", Long.valueOf(musage_.getUsed()));
            nonHeapUsage.put("committed", Long.valueOf(musage_.getCommitted()));
            memoryUsage.put("nonHeapUsage", nonHeapUsage);

            stdMBeanName = new ObjectName("java.lang:type=MemoryPool,*");
            Set pools_ = mbs.queryNames(null, stdMBeanName);
            Iterator itr_ = pools_.iterator();

            Hashtable<String, Object> pools = new Hashtable<String, Object>();

            while (itr_.hasNext()) {
                Object obj_ = itr_.next();
                ObjectName objName_ = (ObjectName) obj_;
                Hashtable<String, Object> poolInfo = new Hashtable<String, Object>();
                Hashtable<String, Object> peakUsage = new Hashtable<String, Object>();
                Hashtable<String, Object> currentUsage = new Hashtable<String, Object>();

                poolInfo.put("type", mbs.getAttribute(objName_, "Type"));

                musage_ = MemoryUsage.from((CompositeData) mbs.getAttribute(objName_, "PeakUsage"));
                peakUsage.put("init", Long.valueOf(musage_.getInit()));
                peakUsage.put("max", Long.valueOf(musage_.getMax()));
                peakUsage.put("used", Long.valueOf(musage_.getUsed()));
                peakUsage.put("committed", Long.valueOf(musage_.getCommitted()));
                poolInfo.put("peakUsage", peakUsage);

                musage_ = MemoryUsage.from((CompositeData) mbs.getAttribute(objName_, "Usage"));
                currentUsage.put("init", Long.valueOf(musage_.getInit()));
                currentUsage.put("max", Long.valueOf(musage_.getMax()));
                currentUsage.put("used", Long.valueOf(musage_.getUsed()));
                currentUsage.put("committed", Long.valueOf(musage_.getCommitted()));
                poolInfo.put("usage", currentUsage);

                pools.put((String) mbs.getAttribute(objName_, "Name"), poolInfo);
            }
            memoryUsage.put("pools", pools);
            root.put("memoryUsage", memoryUsage);

            File[] roots = File.listRoots();
            Hashtable<String, Object> diskUsage = new Hashtable<String, Object>();

            File[] arrayOfFile1;
            int j = (arrayOfFile1 = roots).length;
            for (int i = 0; i < j; i++) {
                File r = arrayOfFile1[i];
                Hashtable<String, Object> diskUsage2 = new Hashtable<String, Object>();
                diskUsage2.put("total_space", Long.valueOf(r.getTotalSpace()));
                diskUsage2.put("free_space", Long.valueOf(r.getFreeSpace()));
                diskUsage2.put("usable_space", Long.valueOf(r.getUsableSpace()));
                diskUsage.put(r.getAbsolutePath(), diskUsage2);
            }
            root.put("diskUsage", diskUsage);

            return parseJSONString(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String parseJSONString(Hashtable<String, Object> table) {
        Enumeration<String> keys = table.keys();
        String key = "";
        Object value = null;
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        while (keys.hasMoreElements()) {
            key = (String) keys.nextElement();
            sb.append("\"").append(key).append("\":");
            value = table.get(key);

            if ((value instanceof Hashtable)) {
                sb.append(parseJSONString((Hashtable) value));
            } else if ((value instanceof String)) {
                sb.append("\"").append(value).append("\"");
            } else if (((value instanceof Long)) || ((value instanceof Integer)) || ((value instanceof Float))
                    || ((value instanceof Double))) {
                sb.append(value);
            }

            if (keys.hasMoreElements()) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
