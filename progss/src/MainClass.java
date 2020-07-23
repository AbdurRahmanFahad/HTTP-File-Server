import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;

public class MainClass {

    public static void main(String [] args) throws Exception
    {
        System.out.println("hello");

        ServerSocket s_socket = new ServerSocket(6969);

        System.out.println("Waiting to be connected...");

        FileWriter  writer      = new FileWriter("log.txt");
        PrintWriter printWriter = new PrintWriter(writer);

        while (true)
        {
            Socket s = s_socket.accept();
            System.out.println("New connection accepted");
            Thread t = new Serve_thread(s, printWriter);
            t.start();
        }

        //printWriter.close();

    }
}
