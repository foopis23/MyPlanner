package foopis.planner;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

class GOOFI
{
    private static final Dimension PANEL_SIZE = new Dimension(300,505);
    static final Dimension FULL_BUTTON_SIZE = new Dimension((int) (PANEL_SIZE.getWidth()-25),25);
    static final Dimension HALF_BUTTON_SIZE = new Dimension((int) FULL_BUTTON_SIZE.getWidth()/2,25);
    private static final String[] months = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
    private static final String[] days = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};

    private JFrame frame;
    private JPanel panel;
    private JButton addButton;
    private LinkedList<AssignmentButton> buttons = new LinkedList<>();

    private JFrame editFrame;
    private JPanel editPanel;
    private JTextField descField;
    private JTextArea notesArea;
    private JComboBox<String> yearBox;
    private JComboBox<String> monthBox;
    private JComboBox<String> dayBox;
    private JButton cancel;
    private JButton remove;
    private JButton confirm;

    GOOFI()
    {
        frame = new JFrame("MyPlanner");
        panel = new JPanel();
        addButton = new JButton("+");

        editFrame = new JFrame("MyPlanner");
        editPanel = new JPanel();
        descField = new JTextField();
        notesArea = new JTextArea();
        yearBox = new JComboBox<>(new String[]{Integer.toString(Integer.parseInt(Main.getYear())-1),Main.getYear(),Integer.toString(Integer.parseInt(Main.getYear())+1)});
        monthBox = new JComboBox<>(months);
        dayBox = new JComboBox<>(days);
        cancel = new JButton("Cancel");
        remove = new JButton("Remove");
        confirm = new JButton("Confirm");
    }

    void init(Main main)
    {
        editPanel.setPreferredSize(new Dimension((int)PANEL_SIZE.getWidth(),(int)PANEL_SIZE.getWidth()));
        editPanel.setBackground(Color.WHITE);
        descField.setPreferredSize(new Dimension((int)FULL_BUTTON_SIZE.getWidth(),40));
        descField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),"Desc"));
        yearBox.setPreferredSize(new Dimension((int) FULL_BUTTON_SIZE.getWidth()/3,25));
        yearBox.setBackground(Color.WHITE);
        monthBox.setPreferredSize(new Dimension((int) FULL_BUTTON_SIZE.getWidth()/3,25));
        monthBox.setBackground(Color.WHITE);
        dayBox.setPreferredSize(new Dimension((int) FULL_BUTTON_SIZE.getWidth()/3,25));
        dayBox.setBackground(Color.WHITE);
        notesArea.setPreferredSize(new Dimension((int)FULL_BUTTON_SIZE.getWidth(),100));
        notesArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),"Notes"));
        notesArea.setLineWrap(true);
        cancel.setPreferredSize(new Dimension((int) FULL_BUTTON_SIZE.getWidth()/3,25));
        cancel.setActionCommand(Main.CANCEL);
        cancel.addActionListener(main);
        cancel.setBackground(Color.WHITE);
        remove.setPreferredSize(new Dimension((int) FULL_BUTTON_SIZE.getWidth()/3,25));
        remove.setBackground(Color.WHITE);
        remove.setActionCommand(Main.REMOVE);
        remove.addActionListener(main);
        confirm.setPreferredSize(new Dimension((int) FULL_BUTTON_SIZE.getWidth()/3,25));
        confirm.setBackground(Color.WHITE);
        confirm.setActionCommand(Main.CONFIRM);
        confirm.addActionListener(main);
        editPanel.add(descField);
        editPanel.add(monthBox);
        editPanel.add(dayBox);
        editPanel.add(yearBox);
        editPanel.add(notesArea);
        editPanel.add(cancel);
        editPanel.add(remove);
        editPanel.add(confirm);
        editFrame.getContentPane().add(editPanel);
        editFrame.setResizable(false);
        editFrame.pack();
        editFrame.setVisible(false);

        panel.setPreferredSize(PANEL_SIZE);
        panel.setBackground(Color.WHITE);
        addButton.setActionCommand(Main.ADD);
        addButton.setPreferredSize(FULL_BUTTON_SIZE);
        addButton.setBackground(Color.WHITE);
        addButton.addActionListener(main);

        panel.add(addButton);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void setCurrentDate()
    {
        yearBox.setSelectedItem(Main.getYear());
        monthBox.setSelectedItem(Main.getMonth());
        dayBox.setSelectedItem(Main.getDate());
    }

    void openEditFrame(Assignment b)
    {
        if(b==null) {
            descField.setText(null);
            notesArea.setText(null);
            remove.setVisible(false);
            setCurrentDate();
            confirm.setText("Add");
        }else{
            descField.setText(b.getDesc());
            notesArea.setText(b.getNotes());
            yearBox.setSelectedItem(Integer.toString(b.getDate()[0]));
            monthBox.setSelectedItem(Integer.toString(b.getDate()[1]));
            dayBox.setSelectedItem(Integer.toString(b.getDate()[2]));
            remove.setVisible(true);
            confirm.setText("Confirm");
        }

        editFrame.setLocationRelativeTo(frame);
        editFrame.setVisible(true);
        editFrame.requestFocus();
    }

    void closeEditFrame()
    {
        editFrame.setVisible(false);
        frame.requestFocus();
    }

    void setButtons(LinkedList<Assignment> assignments, Main main)
    {
        for(AssignmentButton b:buttons)
        {
            panel.setVisible(false);
            panel.remove(b);
            panel.setVisible(true);
        }
        buttons.clear();
        boolean half = assignments.size()>16;

        for(Assignment a: assignments)
        {
            buttons.add(new AssignmentButton(a,half, main));
        }

        for(AssignmentButton b: buttons)
        {
            panel.setVisible(false);
            panel.add(b);
            panel.setVisible(true);
        }
    }

    String getDesc()
    {
        return descField.getText();
    }

    String getNotes()
    {
        return notesArea.getText();
    }

    int[] getDate(){
        return new int[]{Integer.parseInt((String) yearBox.getSelectedItem()),Integer.parseInt((String) monthBox.getSelectedItem()),Integer.parseInt((String) dayBox.getSelectedItem())};
    }
}
