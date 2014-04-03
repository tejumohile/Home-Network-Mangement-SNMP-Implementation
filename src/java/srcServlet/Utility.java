package srcServlet;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

/**
 *
 * @author TAM
 */
public class Utility {

    public static ArrayList<String> readFile(String fileName) {
        BufferedReader input = null;
        String line = "";
        ArrayList<String> list = new ArrayList<String>();
        try {
            input = new BufferedReader(new FileReader(fileName));


            while ((line = input.readLine()) != null) {
                if (!line.isEmpty()) {
                    {
                        list.add(line);
                    }
                }
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    public static void writeLine(String fileName, String attributeName, String value) {
        String line = "";
        try {
            ArrayList<String> fileList = readFile(fileName);
            File newfile = new File(fileName);
            if (newfile.exists() && newfile.delete()) {

                BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

                for (int i = 0; i < fileList.size() ; i++) {
                    if (fileList.get(i).split(" ")[0].equals(attributeName)) {
                        out.write(attributeName+" "+value+"\n");
                    } else {
                        out.write(fileList.get(i)+"\n");
                    }
                }
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String getPwr()
    {
    	String value = null;
    	ArrayList<String> list = readFile("acProp.txt");
        for (int i = 0; i < list.size(); i++) {
            //System.out.println(list.get(i));
            if (list.get(i).split(" ")[0].equals("PWR"))
            {
            	value = list.get(i).split(" ")[1];
            	break;
            }
        }
        return value;

    }

    public static void setPwr(String pwr)
    {
    	writeLine("acProp.txt", "PWR", pwr);
    }

    public static String getTemperature()
    {
    	String value = null;
    	ArrayList<String> list = readFile("acProp.txt");
        for (int i = 0; i < list.size(); i++) {
            //System.out.println(list.get(i));
            if (list.get(i).split(" ")[0].equals("TEMP"))
            {
            	value = list.get(i).split(" ")[1];
            	break;
            }
        }
        return value;
    }

    public static void setTemperature(String temp)
    {
    	writeLine("acProp.txt", "TEMP", temp);
    }

    public static String getFanSpeed()
    {
    	String value = null;
    	ArrayList<String> list = readFile("acProp.txt");
        for (int i = 0; i < list.size(); i++) {
            //System.out.println(list.get(i));
            if (list.get(i).split(" ")[0].equals("FAN"))
            {
            	value = list.get(i).split(" ")[1];
            	break;
            }
        }
        return value;
    }

    public static void setFanSpeed(String speed)
    {
    	writeLine("acProp.txt", "FAN", speed);
    }

    public static String getSwingStatus()
    {
    	String value = null;
    	ArrayList<String> list = readFile("acProp.txt");
        for (int i = 0; i < list.size(); i++) {
            //System.out.println(list.get(i));
            if (list.get(i).split(" ")[0].equals("SWING"))
            {
            	value = list.get(i).split(" ")[1];
            	break;
            }
        }
        return value;
    }

    public static void setSwingStatus(String status)
    {
    	writeLine("acProp.txt", "SWING", status);
    }

    public static void main(String a[]) {

        /*writeLine("acProp.txt", "PWR", "85");
        ArrayList<String> list = readFile("acProp.txt");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));*/
    	System.out.println(getPwr());
    	System.out.println(getTemperature());
    	System.out.println(getFanSpeed());
    	System.out.println(getSwingStatus());

/*
    	setPwr("1");
    	setTemperature("115");
    	setFanSpeed("2");
    	setSwingStatus("0");

    	System.out.println("-----------------------");
    	System.out.println(getPwr());
    	System.out.println(getTemperature());
    	System.out.println(getFanSpeed());
    	System.out.println(getSwingStatus());
*/
     }

  }

