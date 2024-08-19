package com.solano.arthas;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author github.com/solano33
 * @date 2024/8/19 00:27
 */
public class AttachMain {

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        ProcessBuilder processBuilder = new ProcessBuilder().command("jps");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } finally {
            bufferedReader.close();
        }
        System.out.println(sb);

        Scanner scanner = new Scanner(System.in);
        String processId = scanner.nextLine();

        VirtualMachine vm = VirtualMachine.attach(processId);
        vm.loadAgent("/Users/solano/Documents/coding/github/easy-arthas/target/easy-arthas-1.0-SNAPSHOT-jar-with-dependencies.jar");
    }
}























