## Discovery Server (HW5)

###### How to run:

Discovery server is implemented in Python. To run this server, 
$ python discovery.py portNumber

where,
portNumber: is the port number that discovery will listen on, for requests.


Once you have the discovery up and running, you need to then start Conversion servers and Proxy servers with the IP/Hostname and Port Number of this Discovery server.

** ADD/REMOVE/LOOKUP protocols are described in the protocols/ folder. This discovery does exactly that, not more, not less!**

#### Proxy Server

Our proxy server is an extended version of HW2 Proxy server.
To compile and run:
```
$ javac ProxyServer.java
```

```
$ java ProxyServer i:<my ip/hostname>:<my portno> d:<discovery1 ip/hostname>:<discovery1 portno>[,...]

where,
i:<ip>:<port> - are address and portnumber of this proxyserver. address should be an external ip address

d:<ip>:<port>,[...] - address and portnumber of discovery servers. This can be used to register to multiple discovery servers.

ex:
to register to 1 discovery server:
$ java ProxyServer i:localhost:9999 d:localhost:7777

to register to 2 discovery servers:
$ java ProxyServer i:localhost:9999 d:localhost:7777,localhost:6666
```

**Note:**
* Proxy server also supports reading discovery servers and conversion table from file. 
* Conversion server list is removed from the config file, instead Discovery server list is brought in.
* Tags: 
```
  start tag: "=DISCOVERY_SERVER_LIST" 
  server:	<ip or hostname>:<portno>
  end tag: "=END_LIST"
```
ex:
```
=DISCOVERY_SERVER_LIST
localhost:5555
=END_LIST
=CONVERSION_TABLE
ft->cm:ft->in,in->cm
cm->ft:cm->in,in->ft
m->in:m->cm,cm->in
in->m:in->cm,cm->m
=END_TABLE
```
* Each discovery server entry will contain IP address or hostname and port number with colon as a delimiter.
* Limitation: Only using maps to store discovery's ip,portno. So, if you have 2 discovery servers running on machine, only the last one is picked for discovery.
* At the start of the server, ADD requests are sent to all the discovery servers specified. 
* When the server terminates gracefully (which will never happen!), it sends p REMOVE request to each server.
* Whenever a conversion is requested by the client, Proxy server breaks the conversion into multi-steps based on it's predefined table, and sends out LOOKUP request to Discovery server in its list. It sends out request to each Discovery server until it receives a valid "<hostname/IP> <portno>". If it receives a "None" response or "FAILURE" response, it continues to LOOKUP with remaining Discovery servers.

#### Conversion servers

#### Python Conversion Server 1 - 

Our conversion server is an extended version of HW2 conversion server. It takes the IP address of the discovery server and the Conversion Server along with the Port number of both the servers. The Conversion server then registers its IP and port number to the discovery server. This conversion server converts from b to in or in to b and returns the client with the required output.
To compile and run:

Usage: python <programName.py> <IP:Conversion Server> <Port: Conversion server> <IP:Discovery Server> <Port: Discovery server>

where,

programName.py - It is the name of the conversion server written in python.

IP: Conversion Server - The IP of the conversion is given as an input parameter. 

Port: Conversion Server - It is the port number of the Conversion server on which the conversion server will run.

IP: Discovery Server - It is the IP address of discovery server

Port: Discovery server - It is port number of discovery server.


Run:
```
python PythonConvServ1.py 54.152.95.76 8888 54.152.95.76 5555
```
Output:
```
registered
Response: SUCCESS

Started server on  8888
```
#### Python Conversion Server 2 - 

Our conversion server is an extended version of HW2 conversion server. It takes the IP address of the discovery server and the Conversion Server along with the Port number of both the servers. The Conversion server then registers its IP and port number to the discovery server. This conversion server converts from cm to m or m to cm and returns the client with the required output.


To compile and run:

Usage:
```
python <programName.py> <IP:Conversion Server> <Port: Conversion server> <IP:Discovery Server> <Port: Discovery server>
```
where,

programName.py - It is the name of the conversion server written in python.

IP: Conversion Server - The IP of the conversion is given as an input parameter.  

Port: Conversion Server - It is the port number of the Conversion server on which the conversion server will run.

IP: Discovery Server - It is the IP address of discovery server.

Port: Discovery server - It is port number of discovery server.


Run:
```
python PythonConvServ1.py 54.152.95.76 8889 54.152.95.76 5555
```
Output:
```
registered
Response: SUCCESS

Started server on  8889
```


#### C Conversion Server -

The conversion server is an extended version of HW2 conversion server. 

It converts between banana (b) and pounds (lbs).

It is written in C.

Compile:

gcc -o convserver.o convserver.c

Example:
```
gcc ConvServer.c 
```
Run:

./convserver.o DiscoveryIP DiscoveryPort SelfServerIP SelfServerPort



DiscoveryIP: It is the IP address of discovery server.

DiscoveryPort: It is the port number of discovery server.

SelfServerIp: It is the IP address of conversion server.

SelfServerPort: It is the port number of conversion server.


Example:
```
./a.out 52.23.242.100 5555 52.23.242.100 5554 
```

Output:
```
Started server on port 5554
ADD b lbs 52.23.242.100 5554
```


####Java Conversion Server - 

The conversion server is an extended version of HW2 conversion server. 

It converts between inch (in) and centimeter (cm).

It is written in java.


Compile:

javac ConvServer.java

Example:
```
javac ConvServer.java
```

Run:

java ConvServer DiscoveryIP DiscoveryPort SelfServerIP SelfServerPort

DiscoveryIP: It is the IP address of discovery server.

DiscoveryPort: It is the port number of discovery server.

SelfServerIp: It is the IP address of conversion server.

SelfServerPort: It is the port number of conversion server.

Example:
```
java ConvServer 52.23.242.100 5555 52.23.242.100 5553
```
Output:
```
Started server on port 5553
```

Note:

When inputting the port number and IP address of conversion server and discovery server, the sequence of the four arguments are different. 

In the conversion servers written in python, first input the IP address and port number of conversion server, and then input the IP address and port number discovery server.

While in the conversion servers written in Java and C, first input discovery server's IP address and port number, and then input the conversion server's IP address and port number.

####Fault Tolerance:
To check how the system handles fault tolerance, the discovery server can be stopped abruptly and restarted.
 You can see that the server will regain the lost data from the backup file named discoveryData.txt that
 contains information of all the conversion servers that registered with the system before crash. If the file 
 is not found or it is not created yet then a new file will be created and data will be stored in that file.
###




