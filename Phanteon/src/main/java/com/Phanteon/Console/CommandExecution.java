/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Phanteon.Console;

import com.Phanteon.PhanteonGraphical;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author https://github.com/melihi
 */
public class CommandExecution {

    private final ProcessBuilder processBuilder = new ProcessBuilder();
    private Process process;
    private final static Logger logr = Logger.getLogger(CommandExecution.class.getName());

    public boolean getProcessLive() {
        return process.isAlive();
    }

    public void killProcess() {
        process.destroy();
    }

    public String commandExecutor(String commnd) {

        processBuilder.command("bash", "-c", commnd);
        logr.log(Level.INFO, "commandExecutor started");
        StringBuilder output = new StringBuilder();
        try {
            this.process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader
                    = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null || (line = errorReader.readLine()) != null) {
                output.append(line + "\n");
            }
            //  System.out.println(output);
            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
            if (exitCode != 0) {
                output.append("Exited with error code : " + exitCode);
            }
            reader.close();
            logr.log(Level.INFO, "commandExecutor finished");
        } catch (IOException | InterruptedException ex) {
            logr.log(Level.INFO, "commandExecutor error", ex);
        }

        return output.toString();
    }

}
