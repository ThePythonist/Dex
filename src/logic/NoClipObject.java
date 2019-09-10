package logic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class NoClipObject<T extends Positioned> implements Positioned{
	
	private int xOffset;
	private int yOffset;
	private BufferedImage image;
	private T follows;
	
	public NoClipObject(BufferedImage image, T follows, int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.image = image;
		this.follows = follows;
	}
	
	public int getX(){
		return follows.getX()+xOffset;
	}
	
	public int getY(){
		return follows.getY()+yOffset;
	}
	
	public int getXOffset(){
		return xOffset;
	}
	
	public int getYOffset(){
		return yOffset;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public T getFollows(){
		return follows;
	}
	
	public void setXOffset(int xOffset){
		this.xOffset = xOffset;
	}
	
	public void setYOffset(int yOffset){
		this.yOffset = yOffset;
	}
	
	public void setImage(BufferedImage image){
		this.image = image;
	}
	
	public void setFollows(T follows){
		this.follows = follows;
	}
	
	public void draw(Graphics g){
		g.drawImage(image, getX(), getY(), null);
	}
	
	public int getWidth(){
		return image.getWidth();
	}
	
	public int getHeight(){
		return image.getHeight();
	}

}
