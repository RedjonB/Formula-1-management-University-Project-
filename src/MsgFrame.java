import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MsgFrame extends JFrame {
    public MsgFrame(String title, String msg) {
        setTitle(title);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton button = new JButton("Close");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MsgFrame.this.dispose();
            }
        });

        JLabel label = new JLabel(msg);
        add(button,BorderLayout.SOUTH);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label,BorderLayout.CENTER);


    }
}
