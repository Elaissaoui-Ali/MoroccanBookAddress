package entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DiskFile {

    private java.io.File file;
    private FileWriter fileWriter;

    public DiskFile(File file) {
        this.setFile(file);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        String fileName = file.toString();
        if (!fileName.substring(fileName.length() - 4).equals(".txt")){
            fileName = fileName + ".txt";
        }
        this.file = new File(fileName);
    }

    public ArrayList<PersonRecord> getDataFromDiskFile(){
        ArrayList<PersonRecord> result = new ArrayList<>();
        try {
            String fileData = Files.readString(Paths.get(this.file.getPath()));
            JSONArray js = new JSONArray(fileData);
            for (int i = 0; i < js.length(); i++) {
                JSONObject jo = js.getJSONObject(i);
                PersonRecord pr = new PersonRecord(
                        jo.getString("firstName"),
                        jo.getString("lastName"),
                        jo.getString("address"),
                        jo.getString("phoneNumber"),
                        jo.getString("city"),
                        jo.getString("postalCode")
                );
                result.add(pr);
            }
            System.out.println(result);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void writeIntoDiskFile(AddressBook addressBook) {
        JSONArray jsonArray = new JSONArray(addressBook.getDataTable());
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String generateName(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return "Address_Book_" + dateFormat.format(Calendar.getInstance().getTime());
    }
}
