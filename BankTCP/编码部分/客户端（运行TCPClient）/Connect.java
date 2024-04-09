package bank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connect extends JFrame {
    private static final long serialVersionUID = 5475179439752076273L;
    private Container container = getContentPane();
    private JLabel hostLabel = new JLabel("地址");
    private JTextField hostField = new JTextField();
    private JLabel portLabel = new JLabel("端口");
    private JTextField portField = new JTextField();
    private JButton okBtn = new JButton("连接");
    private JButton clearBtn = new JButton("取消");

    public String host;
    public int port;
    public boolean complete = false;
    public Connect() {
    	setResizable(false);
        setTitle("ConnectSetting");
        setBounds(600, 200, 574, 629);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        setVisible(true);
    }

    private void init() {
        getContentPane().setLayout(null);
        okBtn.setBounds(30, 321, 200, 200);
        getContentPane().add(okBtn);
        
        okBtn.setFont(new Font("宋体", Font.BOLD, 80));
        clearBtn.setBounds(325, 321, 200, 200);
        getContentPane().add(clearBtn);
        
        clearBtn.setFont(new Font("宋体", Font.BOLD, 80));
        portField.setBounds(225, 171, 300, 90);
        getContentPane().add(portField);
        portField.setText("2525");
        portField.setFont(new Font("Arial", Font.PLAIN, 50));
        hostField.setBounds(225, 71, 300, 90);
        getContentPane().add(hostField);
        hostField.setFont(new Font("Arial", Font.PLAIN, 50));
        portLabel.setBounds(30, 171, 180, 90);
        portLabel.setFont(new Font("宋体", Font.PLAIN, 80));
        getContentPane().add(portLabel);
        
        portLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hostLabel.setBounds(35, 71, 180, 90);
        hostLabel.setFont(new Font("宋体", Font.PLAIN, 80));
        getContentPane().add(hostLabel);
        
        hostLabel.setHorizontalAlignment(SwingConstants.CENTER);

        addListeners();
    }

    public void addListeners() {
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                host = hostField.getText();
                String portS = portField.getText();
                if (null == host || portS == null || host.trim().length() == 0 || portS.trim().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Host or Port cannot be empty.");
                    return;
                }
                try {
                    port = Integer.parseInt(portS);
                    complete = true;
                    Connect.this.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid port number: " + portS);
                }
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hostField.setText("");
                portField.setText("");
            }
        });

        hostField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okBtn.doClick(); 
            }
        });

        portField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okBtn.doClick(); 
            }
        });
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) {
        new Connect();
    }
}