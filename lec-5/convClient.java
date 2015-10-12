// CS 6421 - Simple Message Board Client in Java
// Compile with: javac convClient
// Run with:     java convClient

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class convClient
{
    public static void main(String[] args) throws IOException
    {
        
        
        //Get the address and the port number of host from the command line
        String hostAddress = "xuchangle.koding.io";
        int portNum =5555;
        
        //Create a Socket object called myClientSocket
        Socket myClientSocket;
        
        //Use the output stream of the socket
        PrintWriter output;
        
        //Get the information of name and message from the command line.
        
        //Receive the result from the server
        BufferedReader fromUser;
        
        for (int i = 0; i < 50; i++)
        {
            //Create a Socket object called myClientSocket
            myClientSocket =  new Socket(hostAddress,portNum);
            
            //Use the output stream of the socket
            output = new PrintWriter(myClientSocket.getOutputStream(), true);
            
            //Get the information of name and message from the command line.
            
            //Receive the result from the server
            fromUser = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
        
            //Send the information to the server
            //output.println(fromConv + " " + toConv + " " + value);
            output.println(args[0] + " " + args[1] + " " + args[2]);
            
            while(fromUser.readLine() != null)
                System.out.println("user: " + fromUser.readLine());
            
            //Close
            output.close();
            myClientSocket.close();
        }
        
        
    }
}
