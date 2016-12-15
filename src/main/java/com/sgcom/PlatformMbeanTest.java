package com.sgcom;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;

public class PlatformMbeanTest {
    public static void main(String[] args) throws IOException {
        RuntimeMXBean proxy = ManagementFactory.getRuntimeMXBean();

        String vendor = proxy.getVmVendor();

        List<MemoryPoolMXBean> memoryPools = ManagementFactory.getMemoryPoolMXBeans();
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        for (MemoryPoolMXBean pool : memoryPools) {
            if ((!pool.isCollectionUsageThresholdExceeded()) && (pool.isCollectionUsageThresholdSupported())) {
                MemoryUsage mu = pool.getUsage();

                System.out.println("Init : " + mu.getInit() + ", Used : " + mu.getUsed() + ", Max : " + mu.getMax()
                        + ", Commited" + mu.getCommitted());
            }
        }
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
