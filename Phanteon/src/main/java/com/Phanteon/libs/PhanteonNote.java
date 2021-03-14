/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Phanteon.libs;

import com.Phanteon.ScanLib;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeruginosa
 */
public class PhanteonNote {

    private final static Logger logr = Logger.getLogger(PhanteonNote.class.getName());

    public void WriteNote(ScanLib scanLib, String text, String value) {

        try {
            logr.log(Level.INFO, "WRITENOTE started");
            java.util.Date date = new java.util.Date();

            System.out.println(scanLib.getPhanteonTarget().getPath());
            File note = new File(scanLib.getPhanteonTarget().getPath() + "/Notes/Note" + value);
            if (!note.exists()) {
                note.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(scanLib.getPhanteonTarget().getPath() + "/Notes/" + "Note" + value, true));
            writer.append(text);

            writer.close();
        } catch (Exception ex) {

            logr.log(Level.SEVERE, "WriteNote ERROR !", ex);

        }
    }

    public String WriteTable() {

        String a = ("++++++++++++++++++++\n"
                + "|  \n\r"
                + "++++++++++++++++++++\n"
                + "|  \n\r"
                + "++++++++++++++++++++\n");
        return a;
    }

    public String Read(String read) {
        String output = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(read));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                output = output + line + "\n";

            }
            System.out.println(line);
        } catch (Exception ex) {
            logr.log(Level.SEVERE, "WriteNote read error !", ex);

        }

        return output;

    }

}
