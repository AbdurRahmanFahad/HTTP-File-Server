import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Date;

public class Serve_thread extends Thread {

    Socket connection;
    PrintWriter printWriter = null;

    Serve_thread(Socket cn, PrintWriter p)
    {
        this.printWriter = p;
        this.connection = cn;
    }

    @Override
    public void run()
    {

        try {
            InputStream is = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            PrintStream pr = new PrintStream(out);
            String request = in.readLine();
            System.out.println(request);
            printWriter.println(request);
            String response = "";

            if( request != null && request.length()>0)
            {
                if(request.startsWith("GET"))
                {
                    String r = request.substring(5, request.length() - 9);

                    if(r.contains("%20"))
                        r = r.replace("%20", " ");
                    String content = get_index_html(r);
                    File ff = new File(r);

                    if(!ff.exists())
                    {
                        pr.print("HTTP/1.1 404 NOT FOUND\r\n"); response += "HTTP/1.1 404 NOT FOUND\r\n";
                        pr.print("Server: Java HTTP Server: 1.0\r\n"); response += "Server: Java HTTP Server: 1.0\r\n";
                        pr.print("Date: " + new Date() + "\r\n"); response += "Date: " + new Date() + "\r\n";
                        pr.print("Content-Type: text/html\r\n"); response += "Content-Type: text/html\r\n";
                        pr.print("Content-Length: " + content.length() + "\r\n"); response += "Content-Length: " + content.length() + "\r\n";
                        pr.print("\r\n");
                        pr.print(content);


                    }
                    else
                    {
                        pr.print("HTTP/1.1 200 OK\r\n"); response += "HTTP/1.1 200 OK\r\n";
                        pr.print("Server: Java HTTP Server: 1.0\r\n"); response += "Server: Java HTTP Server: 1.0\r\n";
                        pr.print("Date: " + new Date() + "\r\n"); response += "Date: " + new Date() + "\r\n";

                        if (ff.isDirectory())
                        {
                            pr.print("Content-Type: text/html\r\n"); response += "Content-Type: text/html\r\n";
                            pr.print("Content-Length: " + content.length() + "\r\n"); response += "Content-Type: text/html\r\n";
                            pr.print("\r\n");
                            pr.print(content);
                        }
                        else
                        {
                            pr.print("Content-Disposition: " + "attachment" + "\r\n"); response += "Content-Disposition: " + "attachment" + "\r\n";
                            pr.print("Content-Type: " + get_file_type(ff) + "\r\n"); response += "Content-Type: " + get_file_type(ff) + "\r\n";
                            pr.print("Content-Length: " + ff.length() + "\r\n"); response += "Content-Length: " + ff.length() + "\r\n";
                            pr.print("\r\n");
                            InputStream fl = new FileInputStream(ff);
                            file_send(fl, out);
                            out.flush();
                        }
                    }

                    pr.flush();
                }
                else if(request.startsWith("UPLOAD"))
                {

                    response += "upload request : ";
                    String download_filename = request.substring(7);
                    System.out.println("upload request : " + download_filename);
                    response += download_filename + "\n";
                    //download_filename = "hello.jpg";
                    FileOutputStream fout = new FileOutputStream("root/"+download_filename);
                    byte [] bb = new byte[1024];
                    int size = 0;

                    while((size=(is.read(bb, 0, bb.length)))>0)
                    {
                        fout.write(bb, 0, size);
                    }
                    fout.close();
                    is.close();
                    System.out.println("File received : " + download_filename);
                    response += "File received : " + download_filename + "\n";
                }
                else if(request.startsWith("PUT"))
                {
                    System.out.println(request);
                }
            }
            printWriter.write(response);
            printWriter.flush();
            connection.close();
        }
        catch (Exception e)
        {
        }

    }

    public String get_index_html(String path)
    {
        String html = null;

        File f = new File(path);

        if(!f.exists())
        {
            return get_404_html();
        }
        else
        {
            if(f.isDirectory())
            {
                String [] file_list = f.list();
                String ht_list = "";
                html = "<html>\n" +
                        "\t<head>\n" +
                        "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "\t</head>\n" +
                        "\t<body>\n";
                for(String xx : file_list)
                {
                    String f2_path = path + '/' + xx;
                    

                    File f2 = new File(f2_path);
                    if(f2.isDirectory())
                    {
                        ht_list += "\t\t<b><a href ="+"\"/"+f2_path+'"'+">"+ xx + "</a></b><br>\n";
                    }
                    else
                    {
                        ht_list += "\t\t<a href ="+"\"/"+f2_path+'"'+">"+ xx + "</a><br>\n";
                    }
                }

                html += ht_list;
                html += "\t</body>\n" +
                        "</html>";
            }
        }


        return html;
    }


    public String get_404_html()
    {
        String er = "<html>\n" +
                "\t<head>\n" +
                "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<h1> 404: Page not found </h1>\n" +
                "\t</body>\n" +
                "</html>";

        return er;
    }


    public void file_send(InputStream f, OutputStream os)
    {
        try {
            byte[] array = new byte[1024];
            while (f.available()>0) {
                os.write(array, 0, f.read(array));
                //Thread.sleep(200);
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    public String get_file_type(File f) throws IOException {
        return Files.probeContentType(f.toPath());
    }

}
