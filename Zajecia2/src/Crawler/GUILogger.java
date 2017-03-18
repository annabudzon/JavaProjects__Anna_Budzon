package Crawler;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUILogger extends JFrame implements ActionListener,Logger {
    private JTextArea textArea;
    
    public GUILogger() {
		setTitle("CRAWLER - NOTIFICATION"); //tytuł okna
                setSize(400,400);
                setLocation(200,200);	
                setLayout(null);
                
	}
  
    @Override
    public void log( String status, Student student){
        JScrollPane scrollPane = new JScrollPane(textArea);
                textArea = new JTextArea(300,300);
                textArea.setEditable(false);
                JScrollPane areaScrollPane = new JScrollPane(textArea);
                areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                areaScrollPane.setPreferredSize(new Dimension(250, 250));
                  textArea.setText(status+student);
                add(textArea);
        NewJFrame frame = new NewJFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //zamykanie okna
        frame.setVisible(true); 
      
        
    }
    
    @Override
    public void log(String status, int iteracja){}
    @Override
    public void log(String status){
        NewJFrame frame = new NewJFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //zamykanie okna
        textArea.setText(status);
        frame.setVisible(true); 
        
    }

    public void actionPerformed(ActionEvent evt) {
      
    }
}
