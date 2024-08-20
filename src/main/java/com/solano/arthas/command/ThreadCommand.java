package com.solano.arthas.command;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author github.com/solano33
 * @date 2024/8/20 21:46
 */
public class ThreadCommand {

    public static void printThreadInfo() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(threadMXBean.isObjectMonitorUsageSupported(),
                threadMXBean.isSynchronizerUsageSupported());

        for (ThreadInfo threadInfo : threadInfos) {
            // 打印线程信息
            StringBuilder sb = new StringBuilder();
            sb.append("threadName: ")
                    .append(threadInfo.getThreadName())
                    .append(" threadId: ")
                    .append(threadInfo.getThreadId())
                    .append(" threadState: ")
                    .append(threadInfo.getThreadState());
            System.out.println(sb);

            // 打印栈信息
            StackTraceElement[] stackTrace = threadInfo.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                System.out.println(stackTraceElement);
            }
        }
    }
}
