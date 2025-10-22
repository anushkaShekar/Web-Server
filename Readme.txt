# Name
Anushka Chandrashekar

## Assignment Name
Programming Assignment 1 - Web Server

---

## Assignment Description 
This assignment implements a functional web server that processes HTTP GET requests
and returns the appropriate HTTP responses.  

---

## Submitted Files (Only source files, directory containing screenshots)
1. Server.java
2. ThreadHandler.java
3. Command.java
4. GetCommand.java
5. HttpRequest.java
6. HttpRequestParser.java
7. HttpResponse.java
8. HttpUtil.java
9. ResponseWriter.java
10. KeepAliveHeuristic.java
11. Readme.txt 
12. Makefile
13. screenshots (directory)

---

## Instructions for Running the Program
1. First, create 2 directories called called files and server in /home/moazzeni/webserver_files. 

2. Download all the source code files (located in the directory called server) 
   to /home/moazzeni/webserver_files/server/

3. Download all necessary files you want the server to deliver into files

4. This project requires Java to compile and run. If Java is not installed on your machine, then
   you can download it from : https://www.oracle.com/java/?er=221886

5. Compiling Source Code Files: 
   - cd webserver_files/server. 
   - Compile with: javac *.java

6. Starting the server: 
   - cd back up to webserver_files (use cd ..) 
   - Then, run: java server.Server -document_root "/home/moazzeni/webserver_files/files" -port 8888

7. In order to see https://www.sjsu.edu/ rendered in your Internet browser, open a new browser tab and enter:
   http://localhost:8888/index.html

8. To see an actual HTTP response for valid, forbidden, bad, or not found requests
   open your computer terminal and go to /home/moazzeni/webserver_files/server/. 
   Run: telnet localhost 8888. 
   Finally, enter any HTTP request you want to test (this server only supports GET requests).

---


## Demonstration 

1. Starting the server (serverStart.png)

2. SJSU home page rendered on http://localhost:8888/index.html (homePage.png) - example of html file

3. Example of jpg file rendered on http://localhost:8888/flower.jpg (flower.png)

4. Example of gif rendered on http://localhost:8888/city.gif (city.png)

5. Example of text file rendered on http://localhost:8888/sample.txt (intro.png)

6. 404 Not Found Image (notFound.png)

7. 400 Bad Request Image (badRequest.png)

8. HTTP 1.0 Connection - connection closes immediately (HTTP1.0.png and HTTP1.0pt2.png)

9. HTTP 1.1 Persistent connection - connection stays open for a 
   certain period of time (persistentHTTP.png and persistentHTTPpt2.png)
