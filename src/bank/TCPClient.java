package bank;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class TCPClient {
    public static void main(String argv[]) throws Exception
    {
        Connect con = new Connect();//地址界面
        while (!con.complete) {
            try {
                Thread.sleep(100); // 暂停100毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String host=con.getHost();
        int port= con.getPort();

        Socket clientSocket = new Socket(host, port);//创建clientSocket
        DataOutputStream outToServer = new DataOutputStream (clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));

        IOUI io=new IOUI(clientSocket,outToServer,inFromServer);



    }
} 