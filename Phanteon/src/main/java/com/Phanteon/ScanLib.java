/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Phanteon;

import com.Phanteon.Console.CommandExecution;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import com.Phanteon.Console.PhanteonGetWeb;

/**
 * @author https://github.com/melihi
 */
public class ScanLib {

    // su statikleri duzelt bu ne amk
    // static String DestHost = "TARGET";
    // static String DestIp = "TARGET";
    // static String CompanyName = "TARGET";
    // static String AsnNumber = "TARGET";
    // static String JustDomain = "TARGET";
    private final static Logger logr = Logger.getLogger(ScanLib.class.getName());
    private Process processFfuf;
    private Process processNmap;
    private Process processAmass;
    private Process processGoSpider;
    private HashMap<String, Integer> ActiveProcess = new HashMap<String, Integer>();
    private PhanteonTarget phanteonTarget;
    private HashMap<String, String> ProcessStatus = new HashMap<String, String>();
    //private final PhanteonGraphical phanteonGraphical = new PhanteonGraphical();
    static CommandExecution execute = new CommandExecution();

    public void setTarget(String setDestHost, String setCompanyName, String setJustDomain, String setAsnNumber) {
        logr.log(Level.INFO, "SCANLIB CONSTRUCTOR started");
        this.phanteonTarget = new PhanteonTarget();
        this.phanteonTarget.setDestHost(setDestHost);
        this.phanteonTarget.setCompanyName(setCompanyName);
        this.phanteonTarget.setJustDomain(setJustDomain);
        this.phanteonTarget.setAsnNumber(setAsnNumber);
        this.phanteonTarget.setDestIp();
        logr.log(Level.INFO, "SCANLIB CONSTRUCTOR finsihed");
    }

    public String[] Whois(PhanteonTarget target) {

        String trace = execute.commandExecutor("traceroute " + target.getDestIp());
        String jumpList[] = trace.split("\n");
        return jumpList;
    }

    public String[] ManuelEnum() {
        String manuel[] = {("https://sitereport.netcraft.com/?url=" + URLEncoder.encode(phanteonTarget.getDestHost(), StandardCharsets.UTF_8)).trim(),
            ("https://whatcms.org/?s=" + URLEncoder.encode(phanteonTarget.getDestHost(), StandardCharsets.UTF_8)).trim(),
            ("https://w3techs.com/sites/info/" + URLEncoder.encode(phanteonTarget.getJustDomain(), StandardCharsets.UTF_8)).trim(),
            ("https://dnsdumpster.com/").trim(),
            ("https://duckduckgo.com/?q=site%3A" + phanteonTarget.getJustDomain()),
            ("https://www.bing.com/search?q=site%3A" + phanteonTarget.getJustDomain()).trim(),
            ("https://dnschecker.org/#A/" + URLEncoder.encode(phanteonTarget.getDestHost(), StandardCharsets.UTF_8)).trim(),
            ("https://www.robtex.com/dns-lookup/" + URLEncoder.encode(phanteonTarget.getDestHost(), StandardCharsets.UTF_8)).trim(),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20key&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20passwd&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20ssh&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20ftp&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20token&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20credentials&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20secret&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20security_credentials&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20connectionstring&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20ssh2_auth_password&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20send_keys&type=code"),
            ("https://github.com/search?q=%22" + phanteonTarget.getJustDomain() + "%22%20JDBC&type=code"),
            ("https://github.com/search?q=org%3A%22" + phanteonTarget.getCompanyName() + "%22%20passwd&type=code"),
            ("https://web.archive.org/web/*/" + phanteonTarget.getDestHost()),
            ("https://www.shodan.io/search?query=" + phanteonTarget.getJustDomain()),
            ("https://www.shodan.io/host/" + phanteonTarget.getDestIp()),
            ("https://censys.io/ipv4/" + phanteonTarget.getDestIp()),
            ("https://bgp.he.net/ip/" + phanteonTarget.getDestIp())

        };

        return manuel;
    }

    public String AsnLookUp() {
        String asn = "";

        logr.log(Level.INFO, "ScanLib - > AsnLooKuP  working");
        try {
            PhanteonGetWeb web = new PhanteonGetWeb();
            String a = web.GetContent("http://asnlookup.com/api/lookup?org=" + phanteonTarget.getCompanyName());

            Document doc = Jsoup
                    .connect(("https://spyse.com/target/ip/" + phanteonTarget.getDestIp()
                            .trim()))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0")
                    .timeout(10000).get();
            Elements broadcasts7;
            broadcasts7 = doc.select(".mark_ip_general_as.popover-link-trigger");
            phanteonTarget.setAsnNumber(phanteonTarget.getAsnNumber() + " " + broadcasts7.text());
            asn += a;
            System.out.println("asn >> " + asn);
        } catch (IOException ex) {
            System.out.println("Whois Error");
            logr.log(Level.SEVERE, "ScanLib - > AsnLookUp  error", ex);
        }
        return asn;
    }

    public ArrayList<String> PortScan(int ports) {
        logr.log(Level.INFO, "ScanLib - > PortScan  working");
        String pathNormal = " -oN PhanteonRESULTS/" + phanteonTarget.getCompanyName() + "Phanteon/" + phanteonTarget.getCompanyName() + "NormalNmap";
        String pathXml = " -oX PhanteonRESULTS/" + phanteonTarget.getCompanyName() + "Phanteon/" + phanteonTarget.getCompanyName() + "Nmap" + " " + phanteonTarget.getJustDomain();
        ProcessStatus.put("nmap", "null");
        ProcessBuilder processBuilderNmap = new ProcessBuilder();
        processBuilderNmap.command("bash", "-c", "nmap --top-ports " + ports + " -Pn  -T4 --reason -A " + pathNormal + pathXml);
        String output = "";
        ArrayList<String> services = new ArrayList<>();
        try {
            processNmap = processBuilderNmap.start();
            getActiveProcess().put("nmap", (int) getProcessNmap().pid());
            ProcessStatus.put("nmap", "started");
            int exitCode = getProcessNmap().waitFor();
            System.out.println("\nNMAP Exited with error code : " + exitCode);
            logr.log(Level.INFO, "ScanLib - > PortScan  exited " + exitCode);
            BufferedReader read = new BufferedReader(new FileReader("PhanteonRESULTS/" + phanteonTarget.getCompanyName() + "Phanteon/" + phanteonTarget.getCompanyName() + "Nmap"));
            ArrayList<String> list = new ArrayList<>();
            String input = read.readLine();
            list.add(input);
            String ss = input;
            while (input != null) {
                input = read.readLine();
                ss = ss + input;
            }
            read.close();
            Document doc = Jsoup.parse(ss, "", Parser.xmlParser());
            for (Element e : doc.select("ports")) {
                for (Element ada : doc.select("port")) {
                    services.add(ada.select("port").attr("portid"));
                    services.add(ada.select("service").attr("name"));
                    services.add(ada.select("service").attr("version"));
                    services.add(ada.select("service").attr("product"));
                    services.add(ada.select("state").attr("state"));
                    services.add(ada.select("state").attr("reason"));
                }
            }
            for (int i = 0; i <= services.size() - 1; i++) {
                System.out.println(services.get(i));
            }
            logr.log(Level.INFO, "ScanLib - > PortScan  finished");
        } catch (IOException ex) {
            logr.log(Level.SEVERE, "ScanLib - > PortScan  error" + ex.getLocalizedMessage());
        } catch (InterruptedException ex) {
            logr.log(Level.SEVERE, "ScanLib - > PortScan  error" + ex.getLocalizedMessage());

        }

        ProcessStatus.put("nmap", "finished");
        getActiveProcess().remove("nmap");

        return services;

    }

    public String HiddenDirectory(int thread, String path, String proxy, String extensions) {
        logr.log(Level.INFO, "ScanLib - > HiddenDirectory started");
        ProcessStatus.put("ffuf", "null");
        String ffuz = ("ffuf -u " + phanteonTarget.getDestHost() + "FUZZ -mc all -r  -recursion  -of csv -o PhanteonRESULTS/"
                + phanteonTarget.getCompanyName() + "Phanteon/" + phanteonTarget.getCompanyName()
                + "Ffuf.csv -w " + path + " -e " + extensions + " -t " + thread + "  -ac BOYLEBISEYYOKAMK -s -sf").trim();

        if (proxy.length() >= 10 && proxy.startsWith("http://")) {
            ffuz += " -x " + proxy;

        }
        logr.log(Level.INFO, "ScanLib - > HIDDEN DIRECTORY started " + ffuz);
        ProcessBuilder processBuilderFfuf = new ProcessBuilder();
        processBuilderFfuf.command("bash", "-c", ffuz);

        try {
            processFfuf = processBuilderFfuf.start();
            getActiveProcess().put("ffuf", (int) getProcessFfuf().pid());
            ProcessStatus.put("ffuf", "started");

            int exitCode = getProcessFfuf().waitFor();
            logr.log(Level.INFO, "ScanLib - > PortScan  exited " + exitCode);
            System.out.println("\nFFUF Exited with error code : " + exitCode);

        } catch (Exception ex) {
            logr.log(Level.SEVERE, "ScanLib - > HiddenDirectory  finished", ex);
        }

        ProcessStatus.put("ffuf", "finished");
        getActiveProcess().remove("ffuf");
        return "ok";
    }

    public ArrayList<String> SubdomainDiscovery(String path, String config) {
        logr.log(Level.INFO, "ScanLib - > SubdomainDiscovery  started");
        ArrayList<String> sub = new ArrayList<>();
        ProcessStatus.put("amass", "null");
        try {

            String amass = ("amass enum  -d " + phanteonTarget.getJustDomain()
                    + " -config " + config + " -active -src  -brute -w " + path + " -asn "
                    + phanteonTarget.getAsnNumber() + " -cidr " + phanteonTarget.getCIDR() + " -o PhanteonRESULTS/" + phanteonTarget.getCompanyName() + "Phanteon/"
                    + phanteonTarget.getCompanyName() + "Amass").trim();
            System.out.println(amass);
            ProcessBuilder processBuilderAmass = new ProcessBuilder();
            if (phanteonTarget.getAsnNumber().length() < 3) {
                amass = amass.replace("-asn", "");
                System.out.println("AMASS COMMAD EDITED");
                System.out.println(amass);
            }
            if (phanteonTarget.getCIDR().length() < 3) {
                amass = amass.replace("-cidr", "");
                System.out.println("AMASS COMMAD EDITED");
                System.out.println(amass);
            }
            processBuilderAmass.command("bash", "-c", amass);
            processAmass = processBuilderAmass.start();
            getActiveProcess().put("amass", (int) getProcessAmass().pid());
            ProcessStatus.put("amass", "started");
            BufferedReader reader = new BufferedReader(new InputStreamReader(getProcessAmass().getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                //phanteonGraphical.LiveHandle(line);
                sub.add(line);
            }
            reader.close();
            int exitCode = getProcessAmass().waitFor();
            logr.log(Level.INFO, "ScanLib - > SubdomainDiscovery  exited" + exitCode);
            System.out.println("\nAmass Exited with error code : " + exitCode);
        } catch (Exception ex) {

            logr.log(Level.SEVERE, "ScanLib - > SubdomainDiscovery  error" + ex);
        }

        ProcessStatus.put("amass", "finished");
        getActiveProcess().remove("amass");
        return sub;
    }

    public ArrayList<String> SiteMap(int depth, int thread, int conc) {

        ProcessStatus.put("gospider", "null");
        ArrayList<String> spider = new ArrayList<>();
        try {

            String GoSpider = ("echo " + phanteonTarget.getDestHost() + " | gospider -u web -t " + thread + " -c " + conc + "  -d " + depth + " --js --sitemap --robots  -K 5 --blacklist \".(woff|png|jpeg|svg|gif|jpg)\" -H \"Accept: */*\" -a  -w  >  PhanteonRESULTS/"
                    + phanteonTarget.getCompanyName() + "Phanteon/" + phanteonTarget.getCompanyName() + "GoSpider.txt")
                    .trim();

            logr.log(Level.INFO, "ScanLib - > Gospider started" + GoSpider);
            ProcessBuilder processBuilderGoSpider = new ProcessBuilder();
            processBuilderGoSpider.command("bash", "-c", GoSpider);

            processGoSpider = processBuilderGoSpider.start();
            getActiveProcess().put("gospider", (int) getProcessGoSpider().pid());
            ProcessStatus.put("gospider", "started");
            int exitCode = getProcessGoSpider().waitFor();
            System.out.println("\nGoSpider Exited with error code : " + exitCode);

            logr.log(Level.INFO, "ScanLib - > Gospider exited : " + exitCode);
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(
                    "PhanteonRESULTS/" + phanteonTarget.getCompanyName() + "Phanteon/" + phanteonTarget.getCompanyName() + "GoSpider.txt"));
            while ((line = reader.readLine()) != null) {
                //phanteonGraphical.LiveHandle(line);
                spider.add(line);
            }
            reader.close();
        } catch (Exception ex) {
            logr.log(Level.SEVERE, "ScanLib - > Gospider ERROR", ex);
        }
        ProcessStatus.put("gospider", "finished");
        logr.log(Level.INFO, "ScanLib - > Gospider finished");
        getActiveProcess().remove("gospider");
        return spider;
    }

    public boolean checkParameter(String url, PhanteonTarget target) {
        logr.log(Level.INFO, "ScanLib - > checkparameter started");
        try {
            if (url.contains("?") || url.contains("=")) {
                execute.commandExecutor("echo \"" + url + "\" >> " + target.getPath() + "PotentialVuln");
                return true;
            }
        } catch (Exception ex) {
            logr.log(Level.SEVERE, "ScanLib - > checkparameter error", ex);
        }
        logr.log(Level.INFO, "ScanLib - > checkparameter finished");
        return false;

    }

    public ArrayList<String> exploitSuggester(PhanteonTarget target) {
        logr.log(Level.INFO, "ScanLib - > exploitSuggester started");
        ArrayList<String> exploits = new ArrayList<>();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", "searchsploit --colour --nmap " + target.getPath() + target.getCompanyName() + "Nmap > " + target.getPath() + target.getCompanyName() + "PhanteonExploitSuggester");
            System.out.println("searchsploit --nmap " + target.getPath() + target.getCompanyName() + "Nmap > PhanteonExploitSuggester");
            Process process = processBuilder.start();
            String line;
            int exitCode = process.waitFor();
            BufferedReader reader = new BufferedReader(new FileReader(target.getPath() + target.getCompanyName() + "PhanteonExploitSuggester"));
            while ((line = reader.readLine()) != null) {
                exploits.add(line);
            }
            reader.close();

        } catch (IOException ex) {
            logr.log(Level.SEVERE, "ScanLib - > exploitsuggester error", ex);
        } catch (InterruptedException ex) {
            logr.log(Level.SEVERE, "ScanLib - > exploitsuggester error", ex);
        }
        return exploits;
    }

    /**
     * @return the processFfuf
     */
    public Process getProcessFfuf() {
        return processFfuf;
    }

    /**
     * @return the processNmap
     */
    public Process getProcessNmap() {
        return processNmap;
    }

    /**
     * @return the processAmass
     */
    public Process getProcessAmass() {
        return processAmass;
    }

    /**
     * @return the processGoSpider
     */
    public Process getProcessGoSpider() {
        return processGoSpider;
    }

    /**
     * @return the ActiveProcess
     */
    public HashMap<String, Integer> getActiveProcess() {
        return ActiveProcess;
    }

    public HashMap<String, String> getProcessStatus() {
        return ProcessStatus;
    }

    public void setProcessStatus(String name, String value) {
        ProcessStatus.put(name, value);
    }

    /**
     * @return the phanteonTarget
     */
    public PhanteonTarget getPhanteonTarget() {
        return phanteonTarget;
    }
}
