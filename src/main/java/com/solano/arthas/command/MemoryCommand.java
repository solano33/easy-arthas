package com.solano.arthas.command;

import com.solano.arthas.constant.ArthasConstant;
import com.sun.management.HotSpotDiagnosticMXBean;

import java.io.IOException;
import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author github.com/solano33
 * @date 2024/8/19 00:27
 */
public class MemoryCommand {

    public static void printMemory() {
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();

        System.out.println("堆内存：");
        // 堆内存
        getMemoryInfo(memoryPoolMXBeans, MemoryType.HEAP);

        // 非堆内存
        System.out.println("非堆内存：");
        getMemoryInfo(memoryPoolMXBeans, MemoryType.NON_HEAP);

        System.out.println("直接内存：");
        getDirectMemoryInfo();
    }

    public static void heapDump() {
        SimpleDateFormat sdf = new SimpleDateFormat(ArthasConstant.DEFAULT_DATE_FORMAT);
        HotSpotDiagnosticMXBean platformMXBean = ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);
        try {
            platformMXBean.dumpHeap(sdf.format(new Date()) + ".hropf", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getMemoryInfo(List<MemoryPoolMXBean> memoryPoolMXBeans, MemoryType type) {
        memoryPoolMXBeans.stream().filter(e -> e.getType() == type)
                .forEach(e -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("name:")
                            .append(e.getName())
                            .append(" used:")
                            .append(e.getUsage().getUsed() / ArthasConstant._1MB)
                            .append("MB")

                            .append(" committed:")
                            .append(e.getUsage().getCommitted() / ArthasConstant._1MB)
                            .append("MB")

                            .append(" max:")
                            .append(e.getUsage().getMax() / ArthasConstant._1MB)
                            .append("MB");
                    System.out.println(sb);
                });
    }

    private static void getDirectMemoryInfo() {

        try {
            Class clazz = Class.forName("java.lang.management.BufferPoolMXBean");
            List<BufferPoolMXBean> bufferPoolMXBean = ManagementFactory.getPlatformMXBeans(clazz);
            bufferPoolMXBean.forEach(e -> {
                StringBuilder sb = new StringBuilder();
                sb.append("name:")
                        .append(e.getName())
                        .append(" used:")
                        .append(e.getMemoryUsed() / ArthasConstant._1MB)
                        .append("MB")

                        .append(" capacity:")
                        .append(e.getTotalCapacity() / ArthasConstant._1MB)
                        .append("MB");
                System.out.println(sb);
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
