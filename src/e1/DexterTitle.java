package e1;

import java.awt.EventQueue;
import java.awt.Graphics;

import genericscenes.Title;
import logic.Program;

public class DexterTitle extends Title {

	private static final long serialVersionUID = 1L;

	public DexterTitle() {
		super("Dexter");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	@Override
	public void nextScene(){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Program.ex.play(new Tonight());
			}
		});
	}

}
