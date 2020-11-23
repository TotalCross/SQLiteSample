package com.totalcross.ui;

import java.util.ArrayList;

import com.totalcross.dao.CPFDAO;

import totalcross.ui.*;
import totalcross.ui.event.*;
import totalcross.ui.gfx.Color;
import totalcross.ui.Control;
import totalcross.ui.dialog.MessageBox;
import java.sql.SQLException;
import totalcross.util.InvalidDateException;


public class Initial extends Container {
    private static Edit maskedEdit;
    private static Button btnOutlined, btnDelete, btnSeeCPF, btnUpdate;
 
    public void initUI() {
        Label cpfLabel = new Label("To register or delete, type your \n CPF:");
        add(cpfLabel, LEFT + 100, AFTER + 50, PREFERRED, Initial.PREFERRED);
 
        maskedEdit = new Edit("999.999.999-99");
        btnOutlined = new Button("Register CPF", Button.BORDER_OUTLINED);
        btnSeeCPF = new Button("see Registered CPFs", Button.BORDER_OUTLINED);
        btnUpdate = new Button("update CPF", Button.BORDER_OUTLINED);
        btnDelete = new Button("Delete CPF", Button.BORDER_OUTLINED);
 
        maskedEdit.caption = "";
        maskedEdit.setMode(Edit.NORMAL, true);
        maskedEdit.setValidChars(Edit.numbersSet);
        maskedEdit.transparentBackground = true;
        maskedEdit.addPressListener(new PressListener() {
 
            public void controlPressed(ControlEvent event) {
                if (event.target == maskedEdit && maskedEdit.getText().length() == "999.999.999-99".length()) {
                    btnOutlined.setEnabled(true);
                    btnOutlined.setForeColor(Color.BLUE);
                    btnOutlined.repaintNow();
                } else {
                    btnOutlined.setEnabled(false);
                    btnOutlined.setForeColor(Color.BLACK);
                    btnOutlined.repaintNow();
                }
 
            }
        });
 
        btnOutlined.setEnabled(false);
        btnOutlined.setBackForeColors(Color.BLACK, Color.WHITE);
        btnSeeCPF.setBackForeColors(Color.BLACK, Color.WHITE);
        btnDelete.setBackForeColors(Color.BLACK, Color.WHITE);
        btnUpdate.setBackForeColors(Color.BLACK, Color.WHITE);
 
        add(maskedEdit, SAME, AFTER + 50, PREFERRED, Initial.PREFERRED);
        add(btnOutlined, LEFT, AFTER + 100, FILL, PREFERRED);
        add(btnSeeCPF, LEFT, AFTER + 30, FILL, PREFERRED);
        add(btnUpdate, LEFT, AFTER + 30, FILL, PREFERRED);
        add(btnDelete, LEFT, AFTER + 30, FILL, PREFERRED);
 
        btnDelete.addPressListener((event) -> {
            try {
                deleteCPF();
            } catch (Exception ee) {
                MessageBox.showException(ee, true);
            }
        });
 
        btnOutlined.addPressListener((event) -> {
            try {
                doInsert();
            } catch (Exception ee) {
                MessageBox.showException(ee, true);
            }
        });
 
        btnUpdate.addPressListener((event) -> {
            try {
                MainWindow.getMainWindow().swap(new Update());
            } catch (Exception ee) {
                MessageBox.showException(ee, true);
            }
        });
 
        btnSeeCPF.addPressListener((event) -> {
            try {
                ArrayList<String> cpfs = new ArrayList<>();
                cpfs = new CPFDAO().getCPF();
                MessageBox mb;
                if (cpfs.size() > 0) {
                    for (int i = 0; i < cpfs.size(); i++) {
                        mb = new MessageBox((i + 1) + "Registered CPF", cpfs.get(i));
                        mb.setBackForeColors(Color.WHITE, Color.BLACK);
                        mb.popup();
                    }
                } else {
                    mb = new MessageBox("Attention", "No CPF was registered");
                    mb.setBackForeColors(Color.WHITE, Color.BLACK);
                    mb.popup();
                }
 
            } catch (Exception ee) {
                MessageBox.showException(ee, true);
            }
        });
    }
 
    private static void doInsert() throws SQLException, InvalidDateException, java.sql.SQLException {
        if (maskedEdit.getTextWithoutMask() == "") {
            MessageBox mb = new MessageBox("Attention!", "Fill the CPF field");
            mb.setBackForeColors(Color.WHITE, Color.BLACK);
            mb.popup();
 
        } else {
            // simple example of how you can insert data into SQLite..
            String cpf = maskedEdit.getTextWithoutMask();
            boolean success = new CPFDAO().insertCPF(cpf);
 
            if (success) {
                MessageBox mb = new MessageBox("Attention!", "the CPF:" + cpf + " was registered succesfully!");
                mb.setBackForeColors(Color.WHITE, Color.BLACK);
                mb.popup();
            } else {
                MessageBox mb = new MessageBox("Attention!", "Error while registering");
                mb.setBackForeColors(Color.WHITE, Color.BLACK);
                mb.popup();
            }
        }
    }
 
    private static void deleteCPF() throws java.sql.SQLException {
        if (maskedEdit.getTextWithoutMask() == "") {
            MessageBox mb = new MessageBox("Attention!", "Fill the CPF Field");
            mb.setBackForeColors(Color.WHITE, Color.BLACK);
            mb.popup();
 
        } else {
            // simple example of how you can insert data into SQLite..
            String cpf = maskedEdit.getTextWithoutMask();
            new CPFDAO().deleteCPF(cpf);
 
            MessageBox mb = new MessageBox("Attention!", "CPF:" + cpf + " Was deleted!");
            mb.setBackForeColors(Color.WHITE, Color.BLACK);
            mb.popup();
 
        }
    }
}


