import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String [] args) throws Exception
    {
        System.out.println("welcome client");



        String filename = "";
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            //System.out.println("Enter File name: ");
            filename = sc.nextLine();
            Socket connection = new Socket("localhost", 6969);


            Client_thread ct = new Client_thread(connection, filename);
            ct.start();

        }
        //connection.close();

    }
}
