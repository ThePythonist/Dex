package e1;

import java.awt.Color;
import java.awt.Graphics;

import genericscenes.Slides;
import logic.ObjectCollider;
import logic.Person;
import people.Dexter;
import people.Donovan;
import sound.Sound;

public class TheHouse extends Slides{
	
	private static final long serialVersionUID = 1L;
	
	private Dexter dexter = new Dexter();
	private Donovan donovan = new Donovan();
	private int speech = 0;
	private int floorHeight = 300;
	private long startTime;
	private Sound bgMusic = new Sound("music/blood theme.wav");
	private ObjectCollider[] choirBoys = new ObjectCollider[3];

	public TheHouse(){
		setBackground(new Color(0,0,0));
		setCharacter(dexter);
		setCanMove(false);
		startTime = System.currentTimeMillis();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if (speech == 0 && System.currentTimeMillis() >= startTime+3000){
			String[] text = {"L[1]o[1]o[1]k[1]."};
			setTextbox(dexter, text);
			speech++;
			startTime = System.currentTimeMillis();
		}
		
		if (getSlide() == 1){
			g.setColor(new Color(51, 51, 51));
			g.fillRect(0, getHeight() - floorHeight, getWidth(), floorHeight);
			
			donovan.draw(g);
			dexter.draw(g);
			
			if (speech > 9){
				for (int i=0; i<choirBoys.length; i++){
					choirBoys[i].draw(g);
				}
			}
		}
		
		if (getTextbox() != null) {
			getTextbox().draw(g, getSize());
			if (getTextbox().isEnded()) {
				setTextbox(null);
			}
		} else {
			if (startTime < 0){
				startTime = System.currentTimeMillis();
			}
			if (speech == 1 && System.currentTimeMillis() >= startTime+3000){
				String[] text = {"No."};
				setTextbox(donovan, text);
				speech++;
			}else if (speech == 2){
				String[] text = {"Oh yes[5].[5].[5]."};
				setTextbox(dexter, text);
				speech++;
				startTime = -1;
			} else if (speech == 3 && System.currentTimeMillis() >= startTime+1000){
				String[] text = {"NO![10] NO!"};
				setTextbox(donovan, text);
				speech++;
				startTime = -1;
			} else if (speech == 4 && System.currentTimeMillis() >= startTime+2000){
				setSlide(1);
				donovan.setLastDirection(Person.IM_BACKWARD);
				dexter.setLastDirection(Person.IM_LEFT);
				donovan.setX(5);
				donovan.setY(getHeight()-floorHeight-donovan.getFacingImage().getHeight(null));
				dexter.setX(donovan.getX()+donovan.getFacingImage().getWidth(null));
				dexter.setY(getHeight()-floorHeight-dexter.getFacingImage().getHeight(null));
				speech++;
				startTime = -1;
			} else if (speech == 5 && System.currentTimeMillis() >= startTime+2000){
				String[] text = {"It's horrible,[2] isn't it?"};
				setTextbox(dexter, text);
				speech++;
				startTime = -1;
			} else if (speech == 6 && System.currentTimeMillis() >= startTime+2000){
				String[] text = {"Please[2].[2].[2]."};
				setTextbox(donovan, text);
				speech++;
				startTime = -1;
			} else if (speech == 7 && System.currentTimeMillis() >= startTime+3000){
				dexter.setLastDirection(Person.IM_LEFT);
				String[] text = {"OPEN YOUR EYES[3] AND LOOK[2] AT WHAT YOU DID!"};
				setTextbox(dexter, text);
				speech++;
				startTime = -1;
			} else if (speech == 8 && System.currentTimeMillis() >= startTime+2000){
				String[] text = {"Look or I'll cut your eyelids right off your face."};
				setTextbox(dexter, text);
				speech++;
				startTime = -1;
			} else if (speech == 9 && System.currentTimeMillis() >= startTime+2000){
				donovan.setLastDirection(Person.IM_RIGHT);
				dexter.setLastDirection(Person.IM_RIGHT);
				bgMusic.play(-1);
				speech++;
				int spacing = 150;
				for (int i=0; i<choirBoys.length; i++){
					choirBoys[i] = new ObjectCollider("characters/choirboy/dead.png", 5, 0, 0);
					choirBoys[i].setX((getWidth()-choirBoys[i].getWidth())/2 + i*(choirBoys[i].getWidth()+spacing));
					choirBoys[i].setY(getHeight()-floorHeight-choirBoys[i].getHeight());
				}
				startTime = -1;
			} else if (speech == 10 && System.currentTimeMillis() >= startTime+3000){
				String[] text = {"It took me a long time to get all these little boys clean.", "One of them had been in the ground so long he was falling apart.[5] I pulled him out in bits and pieces."};
				setTextbox(dexter, text);
				speech++;
			} else if (speech == 11){
				String[] text = {"Hail Mary, full of grace, the Lord..."};
				setTextbox(donovan, text);
				speech++;
			} else if (speech == 12){
				String[] text = {"STOP![5] That never helped anyone."};
				setTextbox(dexter, text);
				speech++;
				startTime = -1;
			} else if (speech == 13 && System.currentTimeMillis() >= startTime+1000){
				String[] text = {"Please[2].[2].[2]."};
				setTextbox(donovan, text);
				speech++;
				startTime = -1;
			} else if (speech == 14 && System.currentTimeMillis() >= startTime+500){
				String[] text = {"It's time for the truth."};
				setTextbox(dexter, text);
				speech++;
				startTime = -1;
			} else if (speech == 15 && System.currentTimeMillis() >= startTime+2000){
				String[] text = {"PLEASE[2].[2].[2].", "You can have anything[2].[2].[2]."};
				setTextbox(donovan, text);
				speech++;
				startTime = -1;
			} else if (speech == 16 && System.currentTimeMillis() >= startTime+1000){
				String[] text = {"That's good.[5] Beg.", "Did these little boys beg?"};
				setTextbox(dexter, text);
				speech++;
				startTime = -1;
			} else if (speech == 17 && System.currentTimeMillis() >= startTime+2000){
				donovan.setRight(loadImage("characters/donovan/kneeling.png", 3));
				donovan.setY(getHeight()-floorHeight-donovan.getFacingImage().getHeight(null));
				String[] text = {"I could'nt help myself, I just couldn't[1].[1].[1]. Please,[5] you have to understand."};
				setTextbox(donovan, text);
				speech++;
				startTime = -1;
			} else if (speech == 18 && System.currentTimeMillis() >= startTime+2000){
				dexter.setLastDirection(Person.IM_LEFT);
				speech++;
				startTime = -1;
			} else if (speech == 19 && System.currentTimeMillis() >= startTime+500){
				String[] text = {"Trust me, I definitely understand.[10] See, I can't help myself either.", "But children?[5] I could never do that.[5] Not like you.[5] Never[3], ever[3] kids."};
				setTextbox(dexter, text);
				speech++;
				startTime = -1;
			} else if (speech == 20 && System.currentTimeMillis() >= startTime+1000){
				setSlide(0);
				speech++;
				startTime = -1;
			} else if (speech == 21 && System.currentTimeMillis() >= startTime+2000){
				String[] text = {"W[2]h[2]y[2]?"};
				setTextbox(donovan, text);
				speech++;
				startTime = -1;
			} else if (speech == 22 && System.currentTimeMillis() >= startTime+2000){
				bgMusic.stop();
				String[] text = {"I have standards."};
				setTextbox(dexter, text);
				speech++;
			}
		}
		
		repaint();
	}

	@Override
	public void nextScene() {
		
	}

}
