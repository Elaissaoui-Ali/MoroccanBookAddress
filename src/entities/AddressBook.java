package entities;

import java.util.ArrayList;

public class AddressBook {
    private DiskFile diskFile;
    private ArrayList<PersonRecord> dataTable;

    public AddressBook(){
        dataTable = new ArrayList<>();
    }

    public AddressBook(DiskFile diskFile){
        this.diskFile = diskFile;
        this.fillDataTable();
    }

    public DiskFile getDiskFile() {
        return diskFile;
    }

    public void setDiskFile(DiskFile diskFile) {
        this.diskFile = diskFile;
    }

    public ArrayList<PersonRecord> getDataTable() {
        return dataTable;
    }

    public void setDataTable(ArrayList<PersonRecord> dataTable) {
        this.dataTable = dataTable;
    }

    public Object[][] toTable(){
        Object[][] table = new Object[this.dataTable.size()][6];
        return table;
    }

    public void fillDataTable(){
        this.dataTable = this.diskFile.getDataFromDiskFile();
    }

    public void addPersonRecord(PersonRecord personRecord){
        if (this.dataTable == null){
            this.dataTable = new ArrayList<>();
        }
        this.dataTable.add(personRecord);
    }
}
