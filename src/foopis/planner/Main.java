package foopis.planner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Main implements ActionListener
{
    static final String ADD = "ADD_BUTTON";
    static final String CANCEL = "CANCEL_BUTTON";
    static final String REMOVE = "REMOVE_BUTTON";
    static final String CONFIRM = "CONFIRM_BUTTON";

    private LinkedList<Assignment> assignments;
    private GOOFI gui;
    private Assignment editAssignment;

    private Main()
    {
        assignments = new LinkedList<>();
        gui = new GOOFI();
        editAssignment = null;
    }

    private void init()
    {
        AssignmentFile.createSaveDir();
        gui.init(this);
    }

    private void go()
    {
        loadAssignments();
        orderAssignments();
        gui.setButtons(assignments,this);
    }

    private void loadAssignments()
    {
        for(int i=0;i<32;i++)
        {
            Assignment a = AssignmentFile.readFile(Integer.toString(i));
            if(a!=null)
            {
                assignments.add(a);
            }
        }
    }

    private void saveAssignments()
    {
        for(int i=0;i<32;i++)
        {
            if(i<assignments.size()) {
                Assignment a = assignments.get(i);
                if (a != null) {
                    AssignmentFile.writeFile(a, Integer.toString(i));
                } else {
                    if(AssignmentFile.removeFile(Integer.toString(i))) System.out.println("Successfully Removed File "+i+".txt");
                }
            }else{
                if(AssignmentFile.removeFile(Integer.toString(i))) System.out.println("Successfully Removed File " +i+".txt");
            }
        }
    }

    private void orderAssignments()
    {
        LinkedList<Assignment> orderedList = new LinkedList<>();
        while(assignments.size()>0)
        {
            Assignment a = assignments.get(0);
            for(int i=1;i<assignments.size();i++)
            {
                Assignment b = assignments.get(i);
                if(a.getDate()[0]==b.getDate()[0])
                {
                    if(a.getDate()[1]==b.getDate()[1])
                    {
                        if(a.getDate()[2]>a.getDate()[2])
                        {
                            a=b;
                        }
                    }else if(a.getDate()[1]>b.getDate()[1])
                    {
                        a=b;
                    }
                }else if(a.getDate()[0]>b.getDate()[0]){
                    a=b;
                }
            }
            assignments.remove(a);
            orderedList.add(a);
        }
        assignments = orderedList;
    }

    public static void main(String[] args)
    {
        Main main = new Main();
        main.init();
        main.go();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals(ADD))
        {
            if(assignments.size()<32) {
                gui.openEditFrame(null);
            }else{
                JOptionPane.showMessageDialog(null,"32 is the maximum amount of assignments. Please remove some before continuing.");
            }
        }else if(e.getActionCommand().equals(CANCEL))
        {
            gui.closeEditFrame();
        }else if(e.getActionCommand().equals(REMOVE))
        {
            assignments.remove(editAssignment);
            editAssignment=null;
            orderAssignments();
            saveAssignments();
            gui.setButtons(assignments,this);
            gui.closeEditFrame();

            for(Assignment a:assignments)
            {
                System.out.println(a.getDesc());
            }
        }else if(e.getActionCommand().equals(CONFIRM))
        {
            if(editAssignment!=null)
            {
                assignments.remove(editAssignment);
                editAssignment.update(gui.getDesc(),gui.getDate(),gui.getNotes());
                assignments.add(editAssignment);
                gui.closeEditFrame();
            }else{
                assignments.add(new Assignment(gui.getDesc(),gui.getDate(),gui.getNotes()));
                gui.closeEditFrame();
            }

            editAssignment=null;
            orderAssignments();
            saveAssignments();
            gui.setButtons(assignments,this);
        }else{
            AssignmentButton a = (AssignmentButton) e.getSource();
            editAssignment = a.getAssignment();
            gui.openEditFrame(editAssignment);
        }
    }

    static String getYear()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    static String getMonth()
    {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);
    }

    static String getDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
