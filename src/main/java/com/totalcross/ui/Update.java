package com.totalcross.ui;
 
import com.totalcross.dao.CPFDAO;
 
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.gfx.Color;
 
public class Update extends Container {
    private Label cpfLabel, cpfLabel1;
    private Button btnupdate;
    private Edit maskedEdit, maskedEditCPF;
 
    public Update() {
 
    }
 
    public void initUI() {
        cpfLabel = new Label("Current CPF:");
        cpfLabel1 = new Label("New CPF:");
 
        maskedEdit = new Edit("999.999.999-99");
        maskedEdit.caption = "";
        maskedEdit.setMode(Edit.NORMAL, true);
        maskedEdit.setValidChars(Edit.numbersSet);
        maskedEdit.transparentBackground = true;
 
        maskedEditCPF = new Edit("999.999.999-99");
        maskedEditCPF.caption = "";
        maskedEditCPF.setMode(Edit.NORMAL, true);
        maskedEditCPF.setValidChars(Edit.numbersSet);
        maskedEditCPF.transparentBackground = true;
 
        btnupdate = new Button("Update", Button.BORDER_OUTLINED);
        btnupdate.setBackForeColors(Color.BLACK, Color.WHITE);
 
        add(cpfLabel, LEFT + 100, TOP + 50, PREFERRED, Update.PREFERRED);
        add(maskedEdit, SAME, AFTER + 50, PREFERRED, Initial.PREFERRED);
 
        add(cpfLabel1, LEFT + 100, AFTER + 50, PREFERRED, Update.PREFERRED);
        add(maskedEditCPF, SAME, AFTER + 50, PREFERRED, Initial.PREFERRED);
 
        add(btnupdate, LEFT, AFTER + 100, FILL, PREFERRED);
        btnupdate.addPressListener((event) -> {
            try {
                updateCPF();
            } catch (Exception ee) {
                MessageBox.showException(ee, true);
            }
        });
 
    }
 
    private void updateCPF() throws java.sql.SQLException {
        if (maskedEdit.getTextWithoutMask() == "" || maskedEditCPF.getTextWithoutMask() == "") {
            MessageBox mb = new MessageBox("Attention!", "Fill all the fields");
            mb.setBackForeColors(Color.WHITE, Color.BLACK);
            mb.popup();
 
        } else {
            // simple example of how you can insert data into SQLite..
            String cpf_antigo = maskedEdit.getTextWithoutMask();
            String cpf_novo = maskedEditCPF.getTextWithoutMask();
            new CPFDAO().updateCPF(cpf_antigo, cpf_novo);
 
            MessageBox mb = new MessageBox("Attention!", "CPF:" + cpf_antigo + " Was updated: " + cpf_novo);
            mb.setBackForeColors(Color.WHITE, Color.BLACK);
            mb.popup();
        }
    }
 
}
