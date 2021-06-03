package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity {
    // Server widgets
    private EditText serverPortEditText = null;
    private Button connectButton = null;

    // Client widgets
    private EditText hourEditText = null;
    private EditText minutesEditText = null;
    private Button setButton = null;
    private Button resetButton = null;
    private Button pollButton = null;
    private EditText clientPortEditText = null;
    private EditText clientAddressEditText = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;


    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e("tag", "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }

    }


    private SetButtonClickListener setButtonClickListener = new SetButtonClickListener();
    private class SetButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            String hour = hourEditText.getText().toString();
            String minute = minutesEditText.getText().toString();
            int operationType = Constants.SET;
            clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), minute, hour, operationType);
            clientThread.start();
        }

    }

    private ResetButtonClickListener resetButtonClickListener = new ResetButtonClickListener();
    private class ResetButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            String hour = hourEditText.getText().toString();
            String minute = minutesEditText.getText().toString();
            int operationType = Constants.RESET;
            clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), minute, hour, operationType);
            clientThread.start();
        }

    }

    private PollButtonClickListener pollButtonClickListener = new PollButtonClickListener();
    private class PollButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            String hour = hourEditText.getText().toString();
            String minute = minutesEditText.getText().toString();
            int operationType = Constants.POLL;
            clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), minute, hour, operationType);
            clientThread.start();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        connectButton = (Button)findViewById(R.id.connect_button);
        connectButton.setOnClickListener(connectButtonClickListener);

        hourEditText = (EditText)findViewById(R.id.editTextTextPersonName);
        minutesEditText = (EditText)findViewById(R.id.editTextTextPersonName2);
        setButton = (Button)findViewById(R.id.buttonSet);
        setButton.setOnClickListener(setButtonClickListener);
        resetButton = (Button)findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(resetButtonClickListener);
        pollButton = (Button)findViewById(R.id.buttonPoll);
        clientPortEditText = (EditText)findViewById(R.id.editTextTextPersonName5);
        clientAddressEditText = (EditText)findViewById(R.id.editTextTextPersonName3);
    }


    @Override
    protected void onDestroy() {
        Log.i("tag", "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}