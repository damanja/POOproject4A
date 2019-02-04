package Code;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Jeu extends JFrame{
	private JTextField jtf1;
	private JTextField jtf2;
//	private JPanel container = new JPanel();
	private JButton demarrer;
	private JComboBox jb;
	
	public Jeu () {
		setBounds(30,30,300,150);
		setLayout(new FlowLayout());
	    this.setLocationRelativeTo(null);
	    Box vb = Box.createVerticalBox();
	    JPanel container1 = new JPanel();
	    container1.setLayout(new FlowLayout());	    
		JLabel jl = new JLabel("Difficulté ");
		container1.add(jl);
		
		String [] difficulte = {"Facile","Moyen","Difficile"};
		jb = new JComboBox(difficulte);
		container1.add(jb);
		
//		jtf1= new JTextField(5);
	//	container1.add(jtf1);
		
		vb.add(container1);
		
		JPanel container2 = new JPanel();
		JLabel jl2 = new JLabel("Nombre de repas");
		container2.add(jl2);
		jtf2= new JTextField(5);
		container2.add(jtf2);
		vb.add(container2);
		
//		container.setLayout(new BorderLayout());
		demarrer = new JButton("Start");
//		container.add(container1, BorderLayout.CENTER);
//		container.add(demarrer,BorderLayout.SOUTH);
		vb.add(demarrer);
		this.add(vb);
//		this.setContentPane(container);
		this.setVisible(true);
		ActionListener a = new MonActionListener();
		demarrer.addActionListener(a);
	}
	
	private class MonActionListener implements ActionListener { 

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
			int difficult;
			int nbrfood;
			String difficulty = (String) jb.getSelectedItem();
//			difficult = Integer.parseInt(jtf1.getText());
			if(difficulty.equals("Facile")) difficult =1;
			else if(difficulty.equals("Moyen")) difficult =2;
			else difficult =3;
			nbrfood= Integer.parseInt(jtf2.getText());
			
			EventQueue.invokeLater(() -> {
	            JFrame ex = new Plateau(difficult, nbrfood);
	            ex.setVisible(true);
	        });
			dispose();
			}catch(NumberFormatException e1){
					JOptionPane.showMessageDialog(null, "Veuillez saisir un nombre de repas ",
							"Error", JOptionPane.ERROR_MESSAGE);; 
				}
		}}
	
	public static void main(String []Args) {
		//Test fen= new Test();
		//fen.setVisible(true);
		
	}
}
