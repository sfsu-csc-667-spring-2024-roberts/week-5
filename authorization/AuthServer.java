package authorization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AuthServer {
  public AuthServer() {

  }

  public void start() throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(9876)) {
      // Take a request, and just automatically respond with 401 Unauthorized
      // WWW-Authenticate: Basic realm="667 Server"
      respondWithUnauthorized(serverSocket.accept());

      // Take a request, and just automatically respond with 403 Forbidden
      respondWithForbidden(serverSocket.accept());
    }
  }

  private void respondWithUnauthorized(Socket socket) throws IOException {
    System.out.println("===== REQUEST CONTENT (1st Request)=====");

    String firstRequestContent = getRequestContent(socket.getInputStream());
    System.out.println(firstRequestContent);

    OutputStream out = socket.getOutputStream();
    out.write("HTTP/1.1 401 Unauthorized\r\n".getBytes());
    out.write("WWW-Authenticate: Basic realm=\"667 Server\"\r\n".getBytes());
    out.write("\r\n".getBytes());

    out.flush();
    socket.close();
  }

  private void respondWithForbidden(Socket socket) throws IOException {
    System.out.println("===== REQUEST CONTENT (2nd Request)=====");

    String content = getRequestContent(socket.getInputStream());
    System.out.println(content);

    OutputStream out = socket.getOutputStream();
    out.write("HTTP/1.1 403 Forbidden\r\n".getBytes());
    out.write("\r\n".getBytes());

    out.flush();
    socket.close();
  }

  private String getRequestContent(InputStream in) throws IOException {
    StringBuffer buffer = new StringBuffer();

    BufferedReader reader = new BufferedReader(
        new InputStreamReader(in));

    String line;
    while ((line = reader.readLine()) != null && line.trim().length() != 0) {
      buffer.append(String.format("> %s\n", line));
    }

    return buffer.toString();
  }

  public static void main(String[] args) throws IOException {
    (new AuthServer()).start();
  }
}