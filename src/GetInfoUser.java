
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

public class GetInfoUser extends JFrame {
        private JTextField nameField;
        private JButton submitButton;
        private JLabel messageLabel;
        private String rString;

        public GetInfoUser(String title,String out){
            setLayout(new FlowLayout());
            setTitle(title);
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            JLabel nameLabel = new JLabel("Enter " + out);
            nameField = new JTextField(20);
            submitButton = new JButton("Submit");
            messageLabel = new JLabel();
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            String input = JOptionPane.showInputDialog("Please enter a string:");

//            submitButton.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    rString = new String(nameField.getText());
//                    if (!rString.isEmpty()) {
//                        messageLabel.setText(out +" recieved!" +  " value " + rString);
//                    } else {
//                        messageLabel.setText("Please enter " + out);
//                    }
//                }
//            });



            JPanel panel = new JPanel();
            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(submitButton);

            add(panel, BorderLayout.NORTH);
            add(messageLabel, BorderLayout.CENTER);

            setLocationRelativeTo(null);
        }

        public String getrString(){
            return rString;
        }

    public static void main(String[] args) {
        // Create the frame on the event dispatching thread.

//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//               GetInfoUser getInfoUser = new GetInfoUser("emri","Name");
//            }
//        });

    }

    }


