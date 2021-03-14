/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Phanteon.Console;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author aeruginosa
 */
public class PhanteonGetWeb {
    
    String UserAgent
            = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
    String Accept = "test/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    String Refferer[] = {"Referer", "http://www.google.com/"};
    
    String AcceptEncoding[] = {"Accept-Encoding", "br, gzip, deflate"};
    private final static Logger logr = Logger.getLogger(PhanteonGetWeb.class.getName());
    
    public void PhanteonGetWeb() {
    }
    
    public String GetGithubReadme(String address) {
        String ass = null;
        try {
            Document doc
                    = Jsoup.connect(address)
                            .userAgent(UserAgent)
                            // .header(Accept[0],Accept[1])
                            .timeout(3000)
                            .get();
            Elements a = doc.select("article.markdown-body entry-content container-lg");
            
            doc.select("svg").remove();
            doc.select("script").remove();
            
            ass = doc.body().select("article").html();
            
        } catch (Exception ex) {
            
            logr.log(Level.SEVERE, "PhanteongetWeb -> GetGithubReadme error", ex);
        }
        return ass;
    }
    
    public String GetContent(String address) {
        String content = null;
        try {
            Document doc
                    = Jsoup.connect(address)
                            .userAgent(UserAgent)
                            .header("Accept", Accept)
                            .header(AcceptEncoding[0], AcceptEncoding[1])
                            .timeout(3000)
                            .get();
            Elements a = doc.select("article.markdown-body entry-content container-lg");
            
            doc.select("svg").remove();
            doc.select("script").remove();
            doc.select("style").remove();
            content = doc.body().html();
            
        } catch (Exception ex) {
            
            logr.log(Level.SEVERE, "PhanteongetWeb -> GetContent error", ex);
        }
        return content;
    }
    
    public String TakeScreenShot(String path, String address) {
        logr.log(Level.INFO, "PhanteongetWeb -> TakeScreenShot working");
        CommandExecution commandExec = new CommandExecution();
        
        String command
                = "python3 Libraries/webscreenshot/webscreenshot.py -vv "
                + address
                + " -r phantomjs   -o "
                + path + "/Screenshots"
                + "  -a \"User-Agent:"
                + UserAgent
                + "\"  -t 2300 ";
        
        String output = "";
        try {
            
            String line;
            String b = commandExec.commandExecutor(command);
            
            int index = b.indexOf("output_file=\"") + 13;
            for (int i = index; i < b.length(); i++) {
                if (b.charAt(i) != '"') {
                    output += b.charAt(i);
                } else {
                    break;
                }
            }
            
        } catch (Exception ex) {
            logr.log(Level.SEVERE, "PhanteongetWeb ->TakeScreenShot ERROR", ex);
        }
        return output;
    }
    
    public String GetSourceCode(String address, String type, List<NameValuePair> data) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse httpresponse;
        Scanner sc;
        String response = "";
        if (type == "GET") {
            try {
                HttpGet httpget = new HttpGet(address);
                httpresponse = httpclient.execute(httpget);
                sc = new Scanner(httpresponse.getEntity().getContent());
                while (sc.hasNext()) {
                    response += sc.nextLine();
                }
                
                return response;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (type == "POST") {
            try {
                HttpPost httpost = new HttpPost(address);
                
                httpost.setEntity(new UrlEncodedFormEntity(data));
                httpresponse = httpclient.execute(httpost);
                
                sc = new Scanner(httpresponse.getEntity().getContent());
                while (sc.hasNext()) {
                    response += sc.nextLine();
                }
                
                return response;
            } catch (IOException ex) {
                
            }
            
        } else {
            System.out.println("PhanteongetWeb -> GetSourceCode ERROR");
        }
        
        return "";
    }
    
    public boolean ProxyCheck(String adress, int port) throws IOException {
        
        HttpGet request = new HttpGet("https://google.com/");
        
        RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(new HttpHost(adress, port))
                .setConnectionRequestTimeout(50)
                .build();
        String status;
        request.setConfig(requestConfig);
        request.addHeader(HttpHeaders.USER_AGENT, UserAgent);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(request);
        status = response.getStatusLine().toString();
        System.out.println(status);
        if (status.contains("200")) {
            return true;
        } else {
            return false;
        }
    }
}
