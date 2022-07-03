package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class AddressBook {
    private DiskFile diskFile;
    private ArrayList<PersonRecord> dataTable;

    public AddressBook() {
        dataTable = new ArrayList<>();
    }

    public AddressBook(DiskFile diskFile) {
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

    public Object[][] toTable() {
        Object[][] table = new Object[this.dataTable.size()][6];
        return table;
    }

    public void fillDataTable() {
        this.dataTable = this.diskFile.getDataFromDiskFile();
    }

    public void addPersonRecord(PersonRecord personRecord) {
        if (this.dataTable == null) {
            this.dataTable = new ArrayList<>();
        }
        this.dataTable.add(personRecord);
    }

    public void sort(int index) {
        if(index == 1){
            this.dataTable = (ArrayList<PersonRecord>) this.dataTable
                    .stream().sorted(Comparator.comparing(PersonRecord::getLastName))
                    .collect(Collectors.toList());

        }else if(index == 4){
            System.out.println("hello");
            this.dataTable = (ArrayList<PersonRecord>) this.dataTable
                    .stream().sorted(Comparator.comparing(PersonRecord::getPostalCode))
                    .collect(Collectors.toList());
        }

        for (PersonRecord r : this.dataTable) {
            System.out.println(r.toString());
        }

    }


}
