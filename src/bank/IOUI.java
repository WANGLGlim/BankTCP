package bank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IOUI extends JFrame {
    private JPanel messagePanel = new JPanel();    // 上部面板
    private JTextArea messageArea = new JTextArea(); // 文本域
    private JScrollPane scrollPane = new JScrollPane(); // 滚动窗格

    private JPanel inputPanel = new JPanel(); // 下部面板
    private JTextField inputField = new JTextField(20);
    private JButton sendButton = new JButton("发送");

    private Socket clientSocket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private final JButton drawButton = new JButton("取款");
    private final JButton exitButton = new JButton("退出");

    public IOUI(Socket clientSocket, DataOutputStream outToServer, BufferedReader inFromServer) {
    	setResizable(false);
        this.clientSocket = clientSocket;
        this.outToServer = outToServer;
        this.inFromServer = inFromServer;

        setTitle("Client");
        setBounds(600, 300, 857, 510);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
                inputPanel.setBounds(10, 238, 819, 233);
                inputPanel.setLayout(null);
                inputField.setFont(new Font("宋体", Font.PLAIN, 66));
                inputField.setBounds(0, 16, 320, 129);
        
                // 下部面板设置
                inputField.setPreferredSize(new Dimension(300, 30));
                inputPanel.add(inputField);
        
        JButton balaButton = new JButton("查询余额");
        balaButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String message = "BALA";
                messageArea.append("Client: " + message + "\n");
                try {
                    outToServer.writeBytes(message + "\n");
                } catch (IOException ex) {
                    System.out.println("发送失败！");
                    messageArea.append("发送失败！\n-----------\n");
                }
                try {
                    String response = inFromServer.readLine();
                    messageArea.append("Server: " + response + "\n");
                    if (response.equals("BYE")) {
                        messageArea.append("连接已断开，如需再次访问，请重启程序！\n-----------\n");
                    }
                } catch (IOException ex) {
                    System.out.println("接收失败！");
                    messageArea.append("接收失败！\n-------------\n");
                }
                inputField.setText("");
        	}
        });
        balaButton.setBounds(25, 177, 100, 44);
        inputPanel.add(balaButton);
        
        JButton sendButton_1 = new JButton("存款");
        sendButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        sendButton_1.setBounds(296, 176, 100, 47);
        inputPanel.add(sendButton_1);
        sendButton.setBounds(356, 30, 157, 100);
        inputPanel.add(sendButton);
        getContentPane().setLayout(null);
        scrollPane.setBounds(10, 10, 819, 218);

        // 添加到窗口中
        getContentPane().add(scrollPane);
        scrollPane.setViewportView(messageArea);
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 80));
        messageArea.append("连接成功！\n-------------\n");
        getContentPane().add(inputPanel);
        drawButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String money = inputField.getText();
        		String message = "WDRA " + money;
                messageArea.append("Client: " + message + "\n");
                try {
                    outToServer.writeBytes(message + "\n");
                } catch (IOException ex) {
                    System.out.println("发送失败！");
                    messageArea.append("发送失败！\n-------------\n");
                }
                try {
                    String response = inFromServer.readLine();
                    messageArea.append("Server: " + response + "\n");
                    if (response.equals("BYE")) {
                        messageArea.append("连接已断开，如需再次访问，请重启程序！\n");
                    }
                } catch (IOException ex) {
                    System.out.println("接收失败！");
                    messageArea.append("接收失败！\n------------\n");
                }
                inputField.setText("");
        	}
        });
        drawButton.setBounds(161, 176, 100, 47);
        
        inputPanel.add(drawButton);
        exitButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String message = "BYE";
                messageArea.append("Client: " + message + "\n");
                try {
                    outToServer.writeBytes(message + "\n");
                } catch (IOException ex) {
                    System.out.println("发送失败！");
                    messageArea.append("发送失败！\n------------\n");
                }
                try {
                    String response = inFromServer.readLine();
                    messageArea.append("Server: " + response + "\n");
                    if (response.equals("BYE")) {
                        messageArea.append("连接已断开，如需再次访问，请重启程序！\n----------\n");
                    }
                } catch (IOException ex) {
                    System.out.println("接收失败！");
                    messageArea.append("接收失败！\n------------\n");
                }
                inputField.setText("");
        	}
        });
        exitButton.setBounds(709, 176, 100, 47);
        
        inputPanel.add(exitButton);

        addActionListeners();
    }

    private void addActionListeners() {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String message = inputField.getText();
        messageArea.append("Client: " + message + "\n");
        try {
            outToServer.writeBytes(message + "\n");
        } catch (IOException ex) {
            System.out.println("发送失败！");
            messageArea.append("发送失败！\n-----------\n");
        }
        try {
            String response = inFromServer.readLine();
            messageArea.append("Server: " + response + "\n");
            if (response.equals("BYE")) {
                messageArea.append("连接已断开，如需再次访问，请重启程序！\n-----------\n");
            }
        } catch (IOException ex) {
            System.out.println("接收失败！");
            messageArea.append("接收失败！\n----------\n");
        }
        inputField.setText("");
    }
}