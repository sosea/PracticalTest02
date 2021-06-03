package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread {

    private int port = 0;
    private ServerSocket serverSocket = null;


    public ServerThread(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
            start();
        } catch (IOException ioException) {
            Log.e("tag", "An exception has occurred: " + ioException.getMessage());

        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i("tag", "[SERVER THREAD] Waiting for a client invocation...");
                Socket socket = serverSocket.accept();
                Log.i("tag", "[SERVER THREAD] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                if (socket != null)
                {
                    BufferedReader bufferedReader = Utilities.getReader(socket);
                    PrintWriter printWriter = Utilities.getWriter(socket);
                    String hour = bufferedReader.readLine();
                    String minutes = bufferedReader.readLine();
                    String operation = bufferedReader.readLine();
                    Log.i("tag", hour);
                    Log.i("tag", minutes);
                    switch (Integer.parseInt(operation))
                    {
                        case Constants.SET:
                            Log.i("tag", "Set operation");
                            break;
                        case Constants.RESET:
                            Log.i("tag", "Reset operation");
                            break;
                        case Constants.POLL:
                            Log.i("tag", "Poll operation");
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException ioException) {
            Log.e("tag", "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());

        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }


    public void stopThread() {
        interrupt();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e("tag", "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
            }
        }
    }
}
