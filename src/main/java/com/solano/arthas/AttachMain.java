package com.solano.arthas;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author github.com/solano33
 * @date 2024/8/19 00:27
 */
public class AttachMain {

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        System.out.println("Found existing java process, please choose one and input the serial number of the process, eg : 1. Then hit ENTER.");
        ProcessBuilder processBuilder = new ProcessBuilder().command("jps");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder sb = new StringBuilder();
        Map<Integer, String> processMap = new HashMap<>();
        try {
            String line;
            int i = 1;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append("[").append(i).append("] ");
                sb.append(line);
                sb.append("\n");
                String[] split = line.split("\\s+");
                if (split.length == 0) {
                    continue;
                }
                String processId = split[0];
                processMap.put(i++, processId);
            }
        } finally {
            bufferedReader.close();
        }
        System.out.println(sb);

        Scanner scanner = new Scanner(System.in);
        String processId = null;
        while (scanner.hasNext()) {
            int index = scanner.nextInt();
            processId = processMap.get(index);
            if (processId != null) {
                break;
            }
            System.out.println("Please select an available pid.");
        }
        assert processId != null;
        VirtualMachine vm = VirtualMachine.attach(processId);
        vm.loadAgent("/Users/solano/Documents/coding/github/easy-arthas/target/easy-arthas-1.0-SNAPSHOT-jar-with-dependencies.jar");
    }
}























