/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class stores the value of current spy username and password.
 * Since there are only three spies in the data center, so I hard coded the 
 * information part.
 * @author qiuyi
 */
public class DataCenter {
    private static final Map<String, List<String>> map = new HashMap<>();
    private static Map<String, String> location = new HashMap<>();
    
    public DataCenter() {
        List<String> pair1 = new ArrayList<>();
        pair1.add("thisisasalt");
        pair1.add("b94a3ca7f593e8b4d2b41a96eaecb781");
        map.put("mikem", pair1);
        List<String> pair2 = new ArrayList<>();
        pair2.add("helloworldsalt");
        pair2.add("95af06d6dce7dd1400f97c2eb195aa6b");
        map.put("joem", pair2);
        List<String> pair3 = new ArrayList<>();
        pair3.add("guessmewhatsalt");
        pair3.add("8f007d478d12bdd5c5afa44db9fd2b3f");
        map.put("jamesb", pair3);
        location.put("mikem", "");
        location.put("joem", "");
        location.put("jamesb", "");
    }
    
    /**
     * This method is to get the current data map.
     * @return 
     */
    public Map getData() {
        Map<String, List<String>> map = new HashMap<>(this.map);
        return map;
    }
    
    /**
     * This method writes the updated information to the .kml file 
     * for Google Earth Pro use.
     * @param username
     * @param coordiantion 
     */
    public void writeFile(String username, String coordiantion) {
        location.put(username, coordiantion);
        try {
            File file = new File("./SecretAgents.kml");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, false);
            String head = "<kml xmlns=\"http://earth.google.com/kml/2.2\"\n" +
                            "><Document>\n" +
                            "<Style id=\"style1\">\n" +
                            "<IconStyle>\n" +
                            "<Icon>\n" +
                            "<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue-dot.png</href>\n" +
                            "</Icon> </IconStyle> </Style> <Placemark>\n" +
                            "<name>seanb</name>\n" +
                            "<description>Spy Commander</description> <styleUrl>#style1</styleUrl>\n" +
                            "<Point>\n" +
                            "<coordinates>-79.945289,40.44431,0.00000</coordinates> </Point>\n" +
                            "</Placemark>";
            fileWriter.write(head);
            Iterator iterator = location.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                String name = (String) entry.getKey();
                String coordinaString = (String) entry.getValue();
                String cur = "<Placemark>\n" +
                            "<name>"+name+"</name> <description>Spy</description> <styleUrl>#style1</styleUrl> <Point>\n" +
                            "<coordinates>"+coordinaString+"</coordinates> </Point>\n" +
                            "</Placemark>";
                fileWriter.write(cur);
            }
            fileWriter.write("</Document>\n</kml>");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
