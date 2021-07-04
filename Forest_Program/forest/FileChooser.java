package forest;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileChooser extends JFrame implements ActionListener {
    private JFileChooser aJFileChooser;

    @Override
    public void actionPerformed(ActionEvent e) {
        this.aJFileChooser = new JFileChooser();
    }

    public int showOpenDialog(Component parent)
    {
        this.aJFileChooser = new JFileChooser();
        return aJFileChooser.showOpenDialog(parent);
    }
}
