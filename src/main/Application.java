package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Application
{
    /**
     * Constant windows name
     */
    public final static String NOM_FENETRE = "Generate a repeating file";
    
    /**
     * Main
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Initialize the app client
     */
    private static void launch() {
        JFrame frame = new JFrame(NOM_FENETRE);
        frame.getContentPane().add(createPanel(frame, NOM_FENETRE), BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    
    /**
     * Launch the generation
     * 
     * @param modelFile
     * @param attributs
     * @throws Exception
     */
    private static void execute(String modelFile, String attributs) throws Exception {
        Generator.generate(modelFile, attributs);
    }
    
    /**
     * Positions the different elements of the ihm
     * 
     * @param frame
     * @param nomFenetre
     * @return
     */
    private static Component createPanel(final JFrame frame, final String nomFenetre) {
        final JTextArea inputBean = new JTextArea();
        JComboBox<String> modelFile = new JComboBox<>(new File("resources/models").list());
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton buttonLancer = new JButton("Start processing");
        buttonLancer.setBounds(90, 228, 200, 23);
        buttonLancer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputBean.selectAll();
                String attributs = inputBean.getSelectedText();
                frame.dispose();
                try {
                    execute((String) modelFile.getSelectedItem(), attributs);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Generation error", nomFenetre + " - Generation error", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(frame, ex, nomFenetre + " - Generation error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(frame, "End of generation", nomFenetre + " - End of generation", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(buttonLancer);
        
        JLabel labelNomModele = new JLabel("Model :");
        labelNomModele.setBounds(21, 14, 80, 14);
        panel.add(labelNomModele);
        
        modelFile.setBounds(81, 11, 276, 21);
        panel.add(modelFile);

        inputBean.setAutoscrolls(true);
        JScrollPane scrollPane = new JScrollPane(inputBean);
        scrollPane.setBounds(20, 41, 340, 180);
        panel.add(scrollPane);

        return panel;
    }

}
