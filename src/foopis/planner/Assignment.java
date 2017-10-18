package foopis.planner;

import java.io.Serializable;

public class Assignment implements Serializable
{
    private String desc;
    private int[] date;
    private String notes;

    public Assignment(String desc, int[] date, String notes)
    {
        this.update(desc,date,notes);
    }

    void update(String desc, int[] date, String notes)
    {
        this.desc = desc;
        this.date = date;
        this.notes = notes;
    }

    int[] getDate()
    {
        return date;
    }

    String getDesc()
    {
        return desc;
    }

    String getNotes()
    {
        return notes;
    }

    String getFormattedDate()
    {
        return date[1] + "/" + date[2] + "/" +date[0];
    }
}
