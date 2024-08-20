package com.solano.arthas.command;

import java.lang.instrument.Instrumentation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author github.com/solano33
 * @date 2024/8/20 22:08
 */
public class ClassCommand {

    public static void printAllClassLoader(Instrumentation inst) {
        Set<ClassLoader> classLoaders = new HashSet<>();
        // 目标进程被加载的所有类
        Class[] allLoadedClasses = inst.getAllLoadedClasses();
        for (Class clazz : allLoadedClasses) {
            ClassLoader classLoader = clazz.getClassLoader();
            classLoaders.add(classLoader);
        }

        String distinctClassLoaders = classLoaders.stream().map(e -> {
            if (e == null) {
                return "BootStrapClassLoader";
            } else {
                return e.getClass().getName();
            }
        }).distinct().collect(Collectors.joining(", "));
        System.out.println("All classLoader: ");
        System.out.println(distinctClassLoaders);
    }
}
