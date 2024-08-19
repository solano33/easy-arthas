package com.solano.arthas.command;

import com.solano.arthas.constant.ArthasConstant;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
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
}
