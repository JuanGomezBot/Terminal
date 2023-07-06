// Duplexer.java

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Duplexer {
    BufferedReader reader;
    PrintWriter writer;
    Socket so;

    public Duplexer(Socket so) throws IOException {
        this.so = so;
        InputStream input = so.getInputStream();
        InputStreamReader ir = new InputStreamReader(input);
        reader = new BufferedReader(ir);
        writer = new PrintWriter(so.getOutputStream());
    }

    public void send(String data) {
        writer.write(data + "\n");
        writer.flush();
    }

    public String read() throws IOException {
        return reader.readLine();
    }
}
