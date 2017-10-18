package foopis.planner;

import javax.swing.*;
import java.awt.*;

public class AssignmentButton extends JButton
{
    Assignment assignment;

    AssignmentButton(Assignment a, boolean isHalf, Main main)
    {
        this.assignment=a;
        this.setText(assignment.getDesc());
        this.setToolTipText(assignment.getFormattedDate());
        this.setHalf(isHalf);
        this.setBackground(Color.WHITE);
        this.addActionListener(main);
        this.setVisible(true);
    }

    Assignment getAssignment()
    {
        return  assignment;
    }

    private void setHalf(boolean half)
    {
        if(half)
        {
            this.setPreferredSize(GOOFI.HALF_BUTTON_SIZE);
        }else{
            this.setPreferredSize(GOOFI.FULL_BUTTON_SIZE);
        }
    }
}
