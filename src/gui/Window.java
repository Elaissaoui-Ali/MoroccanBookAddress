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
import java.io.File;

public class Window extends JFrame {

    private static int openedWindows = 0;
    private final int WIDTH = 900;
    private final int HEIGHT = 650;
    private DiskFile diskFile;
    private JTable jTable;
    private DefaultTableModel tableModel;
    private AddressBook addressBook;

    public Window(AddressBook addressBook) throws HeadlessException {
        super("Untitled");
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
        jTable = new JTable(tableModel);
        for (String s: PersonRecord.LABEL_LIST) {
            tableModel.addColumn(s);
            System.out.println(s);
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
                openFromDiskFile();
            }
        });

        menuBar.add(editMenu);
    }

    private void createNewAddressBook(){
        System.out.println("createOption");
        this.setTitle("createOption");
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
               new Window(new AddressBook(new DiskFile(file)));
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
                String fileName = fileChooser.getSelectedFile().toString();
                if (!fileName.substring(fileName.length() - 4).equals(".txt")){
                    fileName = fileName + ".txt";
                }
            }else{
                System.out.println("file open operation was cancelled!");
            }
        }
    }

    private void quitApp(){
        System.out.println("quitOption");
    }

    private void createNewRecord(){
        JDialog dialog = new JDialog(this, "New Record");
        JPanel panel = new JPanel();

        JTextField firstName = new JTextField("First Name", 20);
        JTextField lastName = new JTextField("Last Name", 20);
        JTextField address = new JTextField("Address", 20);
        JTextField city = new JTextField("City", 20);
        JTextField postalCode = new JTextField("Postal Code", 20);
        JTextField phoneNumber = new JTextField("Phone Number", 20);
        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PersonRecord newRecord = new PersonRecord(
                        firstName.getText(),
                        lastName.getText(),
                        address.getText(),
                        city.getText(),
                        postalCode.getText(),
                        phoneNumber.getText()
                );
                Window.this.addressBook.addPersonRecord(newRecord);
                Window.this.tableModel.addRow(newRecord.toRow());
                dialog.dispose();
            }
        });

        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.add(firstName);
        panel.add(lastName);
        panel.add(address);
        panel.add(city);
        panel.add(postalCode);
        panel.add(phoneNumber);
        panel.add(addBtn);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

}
