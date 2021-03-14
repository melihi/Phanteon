![alt text](https://github.com/melihi/Phanteon/blob/main/phanteonLogo.png?raw=true)

![GitHub repo size](https://img.shields.io/github/repo-size/melihi/Phanteon?style=flat-square)  ![GitHub contributors](https://img.shields.io/github/contributors/melihi/Phanteon?style=flat-square) ![GitHub](https://img.shields.io/github/license/melihi/Phanteon)
# About the project
  This project managing 4 main tools . With this tool you don't need to run terminal commands for first handshake with your target . Phanteon provides to you genereal informations about the target . After phanteon's processes you can dive deep manually with these datas . These external tools makes too much traffic naturally . 


# What is  Phanteon
Phanteon Automatized Scan &amp; Discovery Tool. Phanteon 

Phanteon , aims to provide human readable and clear data about your target . Phanteon automatize popular scaning tools like  :
- [Amass](https://github.com/OWASP/Amass)
- [Nmap](https://github.com/nmap/nmap)
- [Ffuf](https://github.com/ffuf/ffuf)
- [Gospider](https://github.com/jaeles-project/gospider)
- [Wafw00f](https://github.com/EnableSecurity/wafw00f)
- [Nikto](https://github.com/sullo/nikto)
- [Asnlookup api](http://asnlookup.com/)
- [Exploitdb](https://github.com/offensive-security/exploitdb)
- [Spyse.com](spyse.com)
- [whois](https://www.gnu.org/software/inetutils/manual/html_node/whois-invocation.html)
- [traceroute](https://www.gnu.org/software/inetutils/manual/html_node/traceroute-invocation.html)


## Features
```
- Automatically search exploit for open ports
- Automatically search asn number and uses in amass .
- Taking notes  very easy .
- You can customize thread count , port count , fuzzing wordlists
- Add and test proxy for ffuf and wafw00f .
- Select multiple addresses from tables or select url list file and take screenshot.
- Automatically create dorks for sensitive data extraction from  github and searching engines .
- You can run command with basic terminal.
- Automatically runs whois queries and extract necessary datas.
- If discovered addresses takes parameter , adding to possible vulnerable list .
- With Request&Response you can perform get and post requests and u can search in source code.
```
<p float="left">
 <img width="400" alt="portfolio_view" src="https://github.com/melihi/Phanteon/blob/main/ScreenShotExamples/ExploitSample.png?raw=true">
  
  <img width="400" alt="portfolio_view" src="https://github.com/melihi/Phanteon/blob/main/ScreenShotExamples/NoteSample.png?raw=true">
 <pre class="tab">Exploit suggestion example                                              Note taking example </pre>
</p>
<p float="left">
 <img width="400" alt="portfolio_view" src="https://github.com/melihi/Phanteon/blob/main/ScreenShotExamples/WordListSample.png?raw=true">
  <img width="400" alt="portfolio_view" src="https://github.com/melihi/Phanteon/blob/main/ScreenShotExamples/RequestSample.png?raw=true">
<pre class="tab">Wordlist download example                                              Request & Response exaple </pre>
</p>






## Libraries
```
- Apache httpclient
- Jsoup
```

### Prerequisites

This project developed in Arch linux with java 15 . Arch , Java 15+ recommended . Installing the requirements very easy with BlackArch repo . 
Follow the instructions step by step on this link [BlackArch strap.sh](https://blackarch.org/downloads.html#install-repo) . After that run this command 
* Install requirements for Arch linux :
  ```sh
   sudo pacman -S amass ffuf gospider nmap exploitdb phantomjs whois traceroute jre-openjdk

  ```

### Installation

1. After installing requirements ,
2. Clone the repo
   ```sh
   git clone https://github.com/melihi/Phanteon.git
   ```
3. Change directory to :
   ```sh
   cd Phanteon\ Scan\ \&\ Discovery
   ```
4. Make executeable
   ```java
   chmod +x Phanteon-1.0-SNAPSHOT.jar
   ```
5. Run
   ```java
   java -jar Phanteon-1.0-SNAPSHOT.jar
   ```
#### Demo
[![Phanteon](http://img.youtube.com/vi/Nnj5OxXhysU/0.jpg)](http://www.youtube.com/watch?v=Nnj5OxXhysU "Phanteon")




> Logo : [logo](https://www.pngegg.com/en/png-numya) 
