package com.solano.arthas;

import com.solano.arthas.command.MemoryCommand;

import java.lang.instrument.Instrumentation;

/**
 * @author github.com/solano33
 * @date 2024/8/19 23:28
 */
public class AttachActions {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("AttachActions#premain执行了...");
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        MemoryCommand.printMemory();
    }

}
