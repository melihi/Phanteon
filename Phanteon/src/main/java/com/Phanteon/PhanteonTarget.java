/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Phanteon;

import java.net.InetAddress;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author aeruginosa
 */
public class PhanteonTarget {

    private final static Logger logr = Logger.getLogger(PhanteonGraphical.class.getName());
    private String DestHost = "TARGET";
    private String DestIp = "TARGET";
    private String CompanyName = "TARGET";
    private String AsnNumber = "";
    private String JustDomain = "TARGET";
    private String targetPath = "";
    private String CIDR = "";

    /**
     * @return the DestHost
     */
    public void setPath() {
        this.targetPath = "PhanteonRESULTS/" + getCompanyName() + "Phanteon/";
        logr.log(Level.INFO, "target path setted");
    }

    public String getPath() {
        return this.targetPath;
    }

    public String getDestHost() {
        return DestHost;
    }

    /**
     * @param DestHost the DestHost to set
     */
    public void setDestHost(String DestHost) {
        this.DestHost = DestHost;
        System.out.println("DestHost setted > " + this.DestHost);
        logr.log(Level.INFO, "dest host setted");
    }

    /**
     * @return the DestIp
     */
    public String getDestIp() {
        return DestIp;
    }

    /**
     * @param DestIp the DestIp to set
     */
    public void setDestIp() {

        try {
            InetAddress address = InetAddress.getByName(new URL(this.DestHost).getHost());
            this.DestIp = address.getHostAddress();
            System.out.println("setDestIp setted > " + this.DestIp);
            logr.log(Level.INFO, "destip setted");
        } catch (Exception ex) {
            logr.log(Level.SEVERE, "destip error", ex);
        }

    }

    /**
     * @return the CompanyName
     */
    public String getCompanyName() {
        return CompanyName;
    }

    /**
     * @param CompanyName the CompanyName to set
     */
    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;

        logr.log(Level.INFO, "companyname setted");
    }

    /**
     * @return the AsnNumber
     */
    public String getAsnNumber() {
        return AsnNumber;
    }

    /**
     * @param AsnNumber the AsnNumber to set
     */
    public void setAsnNumber(String AsnNumber) {
        String temp = AsnNumber.replace(" ", ",").replace("AS", "");
        if (!AsnNumber.contains(temp)) {
            if (temp.startsWith(",")) {
                temp.replace(",", "");
            }
            
            this.AsnNumber = temp;
        }

        logr.log(Level.INFO, "asnnumber setted");
        System.out.println("ASN SETTEDD OOOOO " + temp);
    }

    /**
     * @return the JustDomain
     */
    public String getJustDomain() {
        return JustDomain;
    }

    /**
     * @param JustDomain the JustDomain to set
     */
    public void setJustDomain(String JustDomain) {
        this.JustDomain = JustDomain;
        logr.log(Level.INFO, "justdomain setted");
    }

    /**
     * @return the CIDR
     */
    public String getCIDR() {
        return CIDR;
    }

    /**
     * @param CIDR the CIDR to set
     */
    public void setCIDR(String CIDR) {
        this.CIDR = CIDR;
    }
}
