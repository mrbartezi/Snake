import javax.swing.*;
import java.awt.*;

public class ScoreBar extends JPanel {
    public ScoreBar() {
        Dimension dim = getPreferredSize();
        dim.height = 80;
        dim.width = 800;
        setPreferredSize(dim);

    }
}
