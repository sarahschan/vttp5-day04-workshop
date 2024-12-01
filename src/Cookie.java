import java.io.*;
import java.util.*;
import java.net.Socket;


public class Cookie {
    
    public String yourFortune(File cookieFile) throws IOException {
        
        // Create an ArrayList to store cookie lines
        List<String> cookieLines = new ArrayList<>();

        // FileReader to read the file
        FileReader fr = new FileReader(cookieFile);
        BufferedReader br = new BufferedReader(fr);
        
        // Read the file to cookieLines array
        String line = "";
        while ((line = br.readLine()) != null) {
            cookieLines.add(line);
        }

        // Generate random line index
        Random randomLineGenerator = new Random();
        int cookieLinesSize = cookieLines.size();
        int randomLine = randomLineGenerator.nextInt(cookieLinesSize);
        String yourFortune = cookieLines.get(randomLine);

        return yourFortune;
    }

}
