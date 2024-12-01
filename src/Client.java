import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

// java -cp fortunecookie.jar fc.Client localhost:12345

public class Client {
    
    public static void main(String[] args) throws UnknownHostException, IOException {
        
        // Define localhost and port
        String[] terms = args[0].split(":");
        String host = terms[0];
        int port = Integer.parseInt(terms[1]);

        // Open console for client to send cookie rquest
        Console clientConsole = System.console();

        // Open connection to server
        Socket clientConn = new Socket(host, port);

        // Open connection to server and recieve cookie
        // Output stream to send message to server
        OutputStream os = clientConn.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);


        while (true) {
            
            String cookieRequest = clientConsole.readLine("Type [get-cookie] to recieve your fortune, or [close] to exit: \n").toLowerCase();

            if (cookieRequest.equals("get-cookie")) {
                
                // Write command to server
                bw.write(cookieRequest + "\n");
                bw.flush();


                // Input stream to recieve cookie
                InputStream is = clientConn.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(reader);

                // Read cookie from server
                String cookie = br.readLine();
                System.out.println(cookie.substring(13));

            } else if (cookieRequest.equals("close")) {
                bw.write(cookieRequest + "\n");
                bw.flush();

                // Exit message and close
                System.out.println("Closing the application");
                break;


            } else {
                System.out.println("Invalid request - try again");
            }
        }
    }


}
