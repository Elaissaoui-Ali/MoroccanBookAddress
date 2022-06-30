package gui;

import entities.AddressBook;
import entities.DiskFile;
import entities.PersonRecord;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

    public class Window extends JFrame {

    private static int openedWindows = 0;
    private final int WIDTH = 900;
    private final int HEIGHT = 650;
    private DiskFile diskFile;
    private JTable jTable;
    private DefaultTableModel tableModel;
    private AddressBook addressBook;
    private boolean dataChanged = false;

    public Window(AddressBook addressBook) throws HeadlessException {
        super("Untitled");
        init(addressBook);
    }

        public Window(AddressBook addressBook, String title) throws HeadlessException {
            super(title);
            init(addressBook);
        }

    public void init(AddressBook addressBook){
        openedWindows++;
        this.addressBook = addressBook;
        this.setVisible(true);
        this.setBounds(10 * openedWindows, 10 * openedWindows, WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addMenuBar();
        this.initTable();
        if(this.addressBook.getDataTable().size() > 0){
            for (PersonRecord pr: this.addressBook.getDataTable()){
                this.tableModel.addRow(pr.toRow());
            }
        }
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if(Window.this.dataChanged){
                    int input = JOptionPane.showConfirmDialog(Window.this,
                            "Do you want to proceed?", "Select an Save...",JOptionPane.YES_NO_CANCEL_OPTION);
                    if(input == 0){
                        Window.this.saveToDiskFile();
                        Window.this.dispose();
                    }else if(input == 1){
                        Window.this.dispose();
                    }
                }else{
                    Window.this.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
    }


    private void addMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        this.addFileMenu(menuBar);
        this.addEditMenu(menuBar);
        this.setJMenuBar(menuBar);
        this.validate();
        this.repaint();
    }

    private void initTable() {
        tableModel = new DefaultTableModel();
        jTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
            return false;
        };
    };
        for (String s: PersonRecord.LABEL_LIST) {
            tableModel.addColumn(s);
        }
        tableModel.setRowCount(0);

        this.add( new JScrollPane( this.jTable) );
        this.add(this.jTable);
    }

    private void addFileMenu(JMenuBar menuBar){
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new MenuOption("New Address Book", "Create new address book") {
            @Override
            public void action() {
                new Window(new AddressBook());
            }
        });

        fileMenu.add(new MenuOption("Open Address Book", "Open from disk file") {
            @Override
            public void action() {
                openFromDiskFile();
            }
        });

        fileMenu.add(new MenuOption("Save...", "Save to disk file") {
            @Override
            public void action() {
                saveToDiskFile();
            }
        });

        fileMenu.add(new MenuOption("Quit", "Close window") {
            @Override
            public void action() {
                quitApp();
            }
        });
        menuBar.add(fileMenu);
    }

    private void addEditMenu(JMenuBar menuBar){
        JMenu editMenu = new JMenu("Option");
        editMenu.add(new MenuOption("New Person Record...", "Create new person record") {
            @Override
            public void action() {
                createNewRecord();
            }
        });

        editMenu.add(new MenuOption("Edit selected...", "Edit selected record") {
            @Override
            public void action() {
                editSelected();
            }
        });
        editMenu.add(new MenuOption("Delete", "Delete selected record") {
            @Override
            public void action() {
                deleteSelected();
            }
        });
        menuBar.add(editMenu);
    }

    private void deleteSelected() {
        int selectedRow = Window.this.jTable.getSelectedRow();
        if(selectedRow < 0){
            JOptionPane.showConfirmDialog(this,
                    "No row is selected", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int input = JOptionPane.showConfirmDialog(this,
                "Do you want to proceed?", "Select an Option...",JOptionPane.YES_NO_CANCEL_OPTION);
        System.out.println(selectedRow);
        if(input == 0){
            this.addressBook.getDataTable().remove(selectedRow);
            this.tableModel.removeRow(selectedRow);
            Window.this.dataChanged = true;
        }
    }

    private void editSelected() {
        PersonRecord recordToEdit = this.addressBook.getDataTable().get(this.jTable.getSelectedRow());
        new AddEditDialog(this, recordToEdit, new AddEditDialog.DialogAction() {
            @Override
            public void action(PersonRecord newRecord) {
                int selectedRow = Window.this.jTable.getSelectedRow();
                System.out.println(newRecord);
                Window.this.addressBook.getDataTable().set(Window.this.jTable.getSelectedRow(), newRecord);
                Window.this.tableModel.setValueAt(newRecord.getFirstName(), selectedRow, 0);
                Window.this.tableModel.setValueAt(newRecord.getLastName(), selectedRow, 1);
                Window.this.tableModel.setValueAt(newRecord.getAddress(), selectedRow, 2);
                Window.this.tableModel.setValueAt(newRecord.getCity(), selectedRow, 3);
                Window.this.tableModel.setValueAt(newRecord.getPostalCode(), selectedRow, 4);
                Window.this.tableModel.setValueAt(newRecord.getPhoneNumber(), selectedRow, 5);
                Window.this.dataChanged = true;
            }
        });
    }

    private void openFromDiskFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName();
                return name.substring(name.length() - 4).equals(".txt");
            }

            @Override
            public String getDescription() {
                return null;
            }
        });
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file.getName()
                    .substring(file.getName().length() - 4)
                    .equals(".txt")){
               new Window(new AddressBook(new DiskFile(file)), file.getName().substring(0,file.getName().length() - 4));
            }else{
                JOptionPane.showMessageDialog(this,
                        "File should be a text file.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            System.out.println("Open command cancelled by user!");
        }
    }

    private void saveToDiskFile(){
        if(this.addressBook.getDiskFile() == null || this.addressBook.getDiskFile().getFile() == null){
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showSaveDialog(this);
            if (response == JFileChooser.APPROVE_OPTION){
                this.diskFile = new DiskFile(fileChooser.getSelectedFile());
                this.diskFile.setFile(fileChooser.getSelectedFile());
                this.diskFile.writeIntoDiskFile(this.addressBook);
                this.dataChanged = false;
                this.setTitle(fileChooser.getSelectedFile().getName());
            }else{
                System.out.println("file open operation was cancelled!");
            }
        }
    }

    private void quitApp(){
        System.out.println("quitOption");
    }

    private void createNewRecord(){
        new AddEditDialog(this, new AddEditDialog.DialogAction() {
            @Override
            public void action(PersonRecord newRecord) {
                Window.this.addressBook.addPersonRecord(newRecord);
                Window.this.tableModel.addRow(newRecord.toRow());
                Window.this.dataChanged = true;
            }
        });
    }

}
