/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Phanteon.libs;

import com.Phanteon.Console.PhanteonGetWeb;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeruginosa
 */
public class CheckService {

    public boolean proxyCheck(String ipString, int port) throws IOException {
        boolean status = true;
        try {
            PhanteonGetWeb get = new PhanteonGetWeb();
            status = get.ProxyCheck(ipString, port);
        } catch (IOException ex) {
            Logger.getLogger(CheckService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

}
