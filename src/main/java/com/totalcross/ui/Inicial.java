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


public class Inicial extends Container {
    private static Edit maskedEdit;
    private static Button btnOutlined, btnDeletar, btnVerCPF, btnAtualizar;
 
    public void initUI() {
        Label cpfLabel = new Label("Para cadastrar ou deletar, digite o \nseu CPF:");
        add(cpfLabel, LEFT + 100, AFTER + 50, PREFERRED, Inicial.PREFERRED);
 
        maskedEdit = new Edit("999.999.999-99");
        btnOutlined = new Button("Cadastrar CPF", Button.BORDER_OUTLINED);
        btnVerCPF = new Button("Ver CPFs Cadastrados", Button.BORDER_OUTLINED);
        btnAtualizar = new Button("Atualizar CPF", Button.BORDER_OUTLINED);
        btnDeletar = new Button("Deletar CPF", Button.BORDER_OUTLINED);
 
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
        btnVerCPF.setBackForeColors(Color.BLACK, Color.WHITE);
        btnDeletar.setBackForeColors(Color.BLACK, Color.WHITE);
        btnAtualizar.setBackForeColors(Color.BLACK, Color.WHITE);
 
        add(maskedEdit, SAME, AFTER + 50, PREFERRED, Inicial.PREFERRED);
        add(btnOutlined, LEFT, AFTER + 100, FILL, PREFERRED);
        add(btnVerCPF, LEFT, AFTER + 30, FILL, PREFERRED);
        add(btnAtualizar, LEFT, AFTER + 30, FILL, PREFERRED);
        add(btnDeletar, LEFT, AFTER + 30, FILL, PREFERRED);
 
        btnDeletar.addPressListener((event) -> {
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
 
        btnAtualizar.addPressListener((event) -> {
            try {
                MainWindow.getMainWindow().swap(new Update());
            } catch (Exception ee) {
                MessageBox.showException(ee, true);
            }
        });
 
        btnVerCPF.addPressListener((event) -> {
            try {
                ArrayList<String> cpfs = new ArrayList<>();
                cpfs = new CPFDAO().getCPF();
                MessageBox mb;
                if (cpfs.size() > 0) {
                    for (int i = 0; i < cpfs.size(); i++) {
                        mb = new MessageBox((i + 1) + "º CPF Cadastrado", cpfs.get(i));
                        mb.setBackForeColors(Color.WHITE, Color.BLACK);
                        mb.popup();
                    }
                } else {
                    mb = new MessageBox("Atenção", "Nenhum CPF foi cadastrado");
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
            MessageBox mb = new MessageBox("Atenção!", "Preencha o campo CPF");
            mb.setBackForeColors(Color.WHITE, Color.BLACK);
            mb.popup();
 
        } else {
            // simple example of how you can insert data into SQLite..
            String cpf = maskedEdit.getTextWithoutMask();
            boolean success = new CPFDAO().insertCPF(cpf);
 
            if (success) {
                MessageBox mb = new MessageBox("Atenção!", "CPF:" + cpf + " foi cadastrado com sucesso!");
                mb.setBackForeColors(Color.WHITE, Color.BLACK);
                mb.popup();
            } else {
                MessageBox mb = new MessageBox("Atenção!", "Erro ao cadastrar");
                mb.setBackForeColors(Color.WHITE, Color.BLACK);
                mb.popup();
            }
        }
    }
 
    private static void deleteCPF() throws java.sql.SQLException {
        if (maskedEdit.getTextWithoutMask() == "") {
            MessageBox mb = new MessageBox("Atenção!", "Preencha o campo CPF");
            mb.setBackForeColors(Color.WHITE, Color.BLACK);
            mb.popup();
 
        } else {
            // simple example of how you can insert data into SQLite..
            String cpf = maskedEdit.getTextWithoutMask();
            new CPFDAO().deleteCPF(cpf);
 
            MessageBox mb = new MessageBox("Atenção!", "CPF:" + cpf + " foi deletado!");
            mb.setBackForeColors(Color.WHITE, Color.BLACK);
            mb.popup();
 
        }
    }
}


