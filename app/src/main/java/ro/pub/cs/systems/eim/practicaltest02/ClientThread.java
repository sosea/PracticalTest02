package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String minutes;
    private String hours;
    private int operation;

    private Socket socket;

    public ClientThread(String address, int port, String minutes, String hours, int operation) {
        this.address = address;
        this.port = port;
        this.minutes = minutes;
        this.hours = hours;
        this.operation = operation;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            printWriter.println(hours);
            printWriter.flush();
            printWriter.println(minutes);
            printWriter.flush();
            printWriter.println(operation);
            printWriter.flush();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
