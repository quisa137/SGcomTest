package com.sgcom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;

public class JsonParseTest {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(new File("C:\\Users\\SGcom\\Documents\\EclipsePrj\\SGcomTest\\toJsonText.txt")));
            ArrayList<String> a = new ArrayList();
            String line;
            while ((line = br.readLine()) != null) {
                a.add(line);
            }
            System.out.println("JSON Out: ");

            JSONArray jArr = new JSONArray(a.toArray());
            System.out.println(jArr.toString());
            for (int i = 0; i < jArr.length(); i++) {
                System.out.println(jArr.getString(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}