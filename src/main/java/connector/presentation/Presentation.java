package connector.presentation;

import javax.swing.*;

public class Presentation {
    private JPanel pnlMain;
    private JLabel lblImage;
    private JLabel lblEPC;
    private JLabel lblTime;

    public Presentation() {
        JFrame presentationFrame = new JFrame("Studienarbeit Präsentation");
        presentationFrame.setContentPane(new JPanel());
        presentationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        presentationFrame.pack();
        presentationFrame.setVisible(true);
    }

    public void updateInformation(String imgPath, String epc, String time) {
        lblImage.setIcon(new ImageIcon(imgPath));
        lblEPC.setText(epc);
        lblTime.setText(time);
    }
}

