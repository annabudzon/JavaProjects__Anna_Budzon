package Crawler;

import javafx.scene.control.TextArea;

public class CustomTextArea {
    public static TextArea tArea;
    
    public static TextArea display(){
        tArea = new TextArea();
        tArea.setPrefRowCount(10);
        tArea.setPrefColumnCount(100);
        tArea.setWrapText(true);
        tArea.setPrefWidth(400);
        tArea.appendText("");
           
        return tArea;
    }
        
     public static TextArea getTextArea() {
        return tArea;
    }
    public static void setTextArea(final TextArea textArea) {
        tArea = textArea;
    }
}
