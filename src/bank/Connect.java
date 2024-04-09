package bank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connect extends JFrame {
    private static final long serialVersionUID = 5475179439752076273L;
    private Container container = getContentPane();
    private JLabel hostLabel = new JLabel("Host:");
    private JTextField hostField = new JTextField();
    private JLabel portLabel = new JLabel("Port:");
    private JTextField portField = new JTextField();
    private JButton okBtn = new JButton("Connect");
    private JButton clearBtn = new JButton("Clear");

    public String host;
    public int port;
    public boolean complete = false;

    public Connect() {
        setTitle("Connect");
        setBounds(600, 200, 400, 200);
        container.setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        setVisible(true);
    }

    private void init() {
        /* 输入部分 -- Center */
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(2, 2, 10, 10));
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        hostLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fieldPanel.add(hostLabel);
        hostField.setFont(new Font("Arial", Font.PLAIN, 14));
        fieldPanel.add(hostField);
        
        portLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fieldPanel.add(portLabel);
        portField.setFont(new Font("Arial", Font.PLAIN, 14));
        fieldPanel.add(portField);

        container.add(fieldPanel, BorderLayout.CENTER);

        /* 按钮部分 -- South */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        okBtn.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(okBtn);
        
        clearBtn.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(clearBtn);

        container.add(buttonPanel, BorderLayout.SOUTH);

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
                okBtn.doClick(); // 模拟按钮点击事件
            }
        });

        portField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okBtn.doClick(); // 模拟按钮点击事件
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