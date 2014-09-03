package programdografikiwektorowej;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class ProgramDoGrafikiWektorowej {

    public static void main(String[] args) {
           EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new OknoGlowne();
                //frame.setLayout(new BoxLayout(frame, BoxLayout.PAGE_AXIS));
                frame.setLocationRelativeTo(null);
                frame.setTitle("XMLWriteTest");
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });

    }
}
