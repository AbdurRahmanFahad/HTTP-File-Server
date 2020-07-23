# HTTP-File-Server

A simultaneous multiple client handling capable HTTP File Server written in Java. 
This program can be used to transfer files in the same LAN between different machines.
Multiple clients can access multiple files and download them simultaneously. 

**Uploading Files to Server**
Many clients can also upload files to the server

**Over the net**
Someone having a Public IP address can set up a server using this program to transfer files over the net.

# User Manual

**Running in the same machine or same LAN**

* Clone the repository in your local machine.
* Run `MainClass.main()` first. The server is on now, waiting for clients to be connected.
* If you want to browse your local machine from your local machine:
  * Open a browser.
  * Go to `http://localhost:6969/C:/` to browse your C drive or put any drive location to browse it.
  * Click on any file to download it.
  * Run `Client.main()` and it will wait for a file name input. Type the file name or give path to the file 
    to upload it to the root directory.
  * **Notice:** If you type only filename to upload it, it must be in the root directory.

**Running Over the Net** 

You will need a Public Ip address for this.
* Run `MainClass.main()` in the machine having a Public IP
* Open a browser in any other machine. 
* Go to `http://yourIpAddress:6969/C:/` to browse your C drive or put any drive location to browse it. 
  Here you need to put the machine's IP in which `MainClass.main()` is running.
* Click on any file to download it. 
* For file uploading, You need to change `Client.java`.

```javascript
  Socket connection = new Socket("localhost", 6969);
```
* Here replace `localhost` with your server machine's IP. For example, 
```javascript
  Socket connection = new Socket("103.84.253.245", 6969);
```  
* Now run `Client.main()` and it will wait for a file name input. Type the file name or give path to the file 
  to upload it.
* Uploaded file from client's machine will be stored in the root directory of server.
  
  
  
  
  
  
  
  
