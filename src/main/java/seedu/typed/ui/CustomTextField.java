package seedu.typed.ui;


import javafx.scene.control.TextField;

public class CustomTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
       /* if (text.matches("[a-z]")) {
            Text t = new Text("exit");
            t.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 40));
            t.setFill(Color.BLUE);
            String toDisplay = t.getText();
            toDisplay = "<html><font color=\"red\">add</font></html>";
           // this.setStyle("-fx-text-inner-color: red;");
            this.setText("<pre style='color:#000020;background:#f6f8ff;'>red</pre>");*/
        super.replaceText(start, end, text);
        /* } else {
            super.replaceText(start, end, text);
        }*/
    }
}
