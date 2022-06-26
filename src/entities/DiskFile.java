package entities;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DiskFile {

    private java.io.File file;

    public DiskFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public AddressBook getDataFromDiskFile(){
        return new AddressBook();
    }

    public void writeIntoDiskFile(AddressBook addressBook){

    }

    public static String generateName(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return "Address_Book_" + dateFormat.format(Calendar.getInstance().getTime());
    }
}
