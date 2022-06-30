package gui;

import entities.PersonRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class AddEditDialog extends JDialog {
    public interface DialogAction {
        public void action(PersonRecord newRecord);
    }
    private PersonRecord personRecord;
    public AddEditDialog(Window parent,DialogAction action){
        super(parent, "New Record");
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
                personRecord = new PersonRecord(
                        firstName.getText(),
                        lastName.getText(),
                        address.getText(),
                        city.getText(),
                        postalCode.getText(),
                        phoneNumber.getText()
                );
                action.action(personRecord);
                AddEditDialog.this.dispose();
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

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(this);
        this.setVisible(true);
    }


    public AddEditDialog(Window parent,PersonRecord personRecord, DialogAction action){
        super(parent, "Edit");
        this.personRecord = personRecord;
        JPanel panel = new JPanel();
        JTextField firstName = new JTextField(personRecord.getFirstName(), 20);
        JTextField lastName = new JTextField(personRecord.getLastName(), 20);
        JTextField address = new JTextField(personRecord.getAddress(), 20);
        JTextField city = new JTextField(personRecord.getCity(), 20);
        JTextField postalCode = new JTextField(personRecord.getPostalCode(), 20);
        JTextField phoneNumber = new JTextField(personRecord.getPhoneNumber(), 20);
        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddEditDialog.this.personRecord = new PersonRecord(
                        firstName.getText(),
                        lastName.getText(),
                        address.getText(),
                        city.getText(),
                        postalCode.getText(),
                        phoneNumber.getText()
                );
                action.action(personRecord);
                AddEditDialog.this.dispose();
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

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(this);
        this.setVisible(true);
    }
}
