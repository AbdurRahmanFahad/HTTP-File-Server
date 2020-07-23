import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client_thread extends Thread {

    Socket connection = null;
    String file_name = "";
    Client_thread(Socket s, String f_name)
    {
        connection = s;
        file_name = f_name;
    }
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            OutputStream out = connection.getOutputStream();
            BufferedOutputStream br = new BufferedOutputStream(out);

            PrintStream pr = new PrintStream(out);
            String upload_path = file_name;
            File file = new File(upload_path);


            if (file.exists()) {
                String px = file.getName();
                String sss = "UPLOAD " + px + "\n";
                //sss = sc.nextLine();
                pr.print(sss);
                //System.out.println(sss);
                FileInputStream ff = new FileInputStream(file);

                byte[] array = new byte[1024];

                Thread.sleep(200);

                while (ff.read(array) > 0)
                    out.write(array);

                ff.close();

                System.out.println( px + " Transfer Completed");
            } else {
                System.out.println("Error: No such file found");
                pr.print("Error: Invalid File name");
            }

            out.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
