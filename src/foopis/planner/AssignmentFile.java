package foopis.planner;

import java.io.*;

class AssignmentFile
{
    private static final String HOME_DIR = System.getProperty("user.home")+"\\myplanner\\";

    static void createSaveDir()
    {
        File file = new File(HOME_DIR);
        if(!file.exists()&&!file.isDirectory())
        {
            if(file.mkdir()) System.out.println("Successfully created Save Directory at "+HOME_DIR);
        }
    }

    static void writeFile(Assignment assignment, String fileName)
    {
        try {
            FileOutputStream fileOut = new FileOutputStream(HOME_DIR+fileName+".txt");
            ObjectOutputStream out= new ObjectOutputStream(fileOut);
            out.writeObject(assignment);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Assignment readFile(String fileName)
    {
        try {
            File file = new File(HOME_DIR+fileName+".txt");
            if (file.exists()&&file.isFile()) {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                Assignment a = (Assignment) in.readObject();
                in.close();
                return a;
            }else{
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static boolean removeFile(String fileName) {
        File file = new File(HOME_DIR+fileName + ".txt");

        return file.delete();
    }
}
