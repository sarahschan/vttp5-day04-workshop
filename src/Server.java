import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // java -cp fortunecookie.jar fc.Server 12345 cookie_file.txt

    public static void main(String[] args) throws IOException {
        
        // Define port and file to open
        int port = Integer.parseInt(args[0]);
        File cookieFile = new File(args[1]);


        // Open the port
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Fortune cookie server listening on port " + port);


        while (true) {

            // Server's socket connection and accept
            Socket serverConn = serverSocket.accept();
            System.out.println("A client has connected");

            // Input stream to recieve client's request
            InputStream is = serverConn.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            // Output stream to send cookie to client
            OutputStream os = serverConn.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            // Check client's request
            String clientRequest = "";

            while ((clientRequest = br.readLine()) != null) {

                // If client request "get-cookie"
                if (clientRequest.equals("get-cookie")) {
                    // handover to cookie class
                    // get fortune
                    Cookie cookie = new Cookie();
                    String fortuneToSend = cookie.yourFortune(cookieFile);
                    bw.write("cookie-text: " + fortuneToSend + "\n");
                    bw.flush();


                    // Alternative: OutputStream + BufferedOutputStream
                    // OutputStream os = serverConn.getOutputStream();
                    // BufferedOutputStream bos = new BufferedOutputStream(os);
                    // byte[] fortuneToSendBytes = fortuneToSend.getBytes();
                    // bos.write(fortuneToSendBytes);
                    // bos.flush();


                } else if (clientRequest.equals("close")) {
                    System.out.println("Closing connection with client");
                    break;      // Exit the loop to go to serverConn.close

                } else {
                    bw.write("Unknown command, please try again\n");
                    bw.flush();
                }
            }

            serverConn.close();
            System.out.println("Connection with client closed");
            
        }
        
    }
    
}
