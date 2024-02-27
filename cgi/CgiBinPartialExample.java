package cgi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class CgiBinPartialExample {
  public static void main(String[] args) throws IOException {
    ProcessBuilder builder = new ProcessBuilder(args[0]);

    Map<String, String> env = builder.environment();
    env.put("HTTP_Header-Name", "Value of the header");
    env.put("SERVER_PROTOCOL", "HTTP/1.1");
    env.put("QUERY_STRING", "a=b&c=2");

    Process process = builder.start();
    process.getOutputStream().write("This is the body of the POST request".getBytes());

    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    StringBuffer buffer = new StringBuffer();

    while ((line = reader.readLine()) != null) {
      buffer.append(line + "\r\n");
    }

    // Server start HTTP Response "HTTP/1.1 200 Ok\r\n";
    System.out.println(String.format("Result was:\n\n%s", buffer.toString()));
  }
}
