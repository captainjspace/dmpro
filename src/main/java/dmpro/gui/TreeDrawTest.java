package dmpro.gui;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dmpro.character.Character;
import dmpro.character.CharacterService;

public class TreeDrawTest extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TreeDrawTest() {
	}
	
	
    @Override
    public void paintComponent(Graphics g) {
        // Draw Tree Here
        g.drawOval(5, 5, 25, 25);
        CharacterService cm = new CharacterService();
        Character c = cm.loadCharacter("9983");
        g.drawString(c.toString(), 50,50);
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.add(new TreeDrawTest());
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
    }

}
