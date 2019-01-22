package com.heartstar.game;
//this class is where I check everything concerning the characters in my game
// it controls the positions of the characters, as long as moving, jumping
// and all the collisions in the game

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

public class Player{
	private int x, y;
	private float yVel,xVel;
	
	private int left,right,up,down,direction;
	
	private boolean jumping, falling, onGround, holding, pushing, moving, winning, dead, soundmute;
	
	private int lside,rside,ground,top;
	
	private int width = 118, height = 146;
	
	
	private float textureCount, soundTime;
	
	private Sound jumpsound, spikesound;
	
	public Player(int x, int y){

		this.x = x;
		this.y = y;
		jumping = false;
		falling = true;
		onGround = false;
		lside = 0;
		rside = 1920;
		ground = -100;
		top = 1080;
		
		textureCount = 0;
		left = 0;
		right = 1;
		up = 2;
		down = 3;
		direction = left;
		holding = false;
		pushing = false;
		dead = false;
		soundmute = false;
		soundTime = 0;
		
		xVel = 0;
		
		jumpsound = Gdx.audio.newSound(Gdx.files.internal("Jump.wav"));
		spikesound = Gdx.audio.newSound(Gdx.files.internal("Spike.wav"));
		
	}
	public int getDirection(){
		return direction;
	}
	
	public void gravity(){
		//adds gravity up to a terminal velocity
		if(yVel < -10){
			yVel = -10;
		}
		else{
			yVel-= 0.7;
		}
		
		if(yVel < 0){
			//when velocity is negative, it means you are falling
			jumping = false;
			falling = true;
			//onGround = false;
		}
		y += yVel;
		if(onGround){
			//no y velocity when standing on something
			yVel = 0;
		}
	}
	public void movex(){
		//moving the x component of the characters
		if(xVel < 0){
			//gain xVelocity, up to a maximum
			//this is to make it look more natural
			xVel += 0.5;
			xVel = Math.max(xVel, -7);
		}
		else if(xVel > 0){
			xVel -= 0.5;
			xVel = Math.min(xVel, 7);
		}
		x += xVel;
	}
	
	public void move(){
		gravity();
		movex();
		moving = false;
		//check whether moving left/right
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			xVel -= 1;
			direction = left;
			moving = true;

		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			xVel += 1;
			direction = right;
			moving = true;
		
		}
		if(Gdx.input.isKeyPressed(Keys.UP)){
			jump();
		}
		collisions();
		soundTime += Gdx.graphics.getDeltaTime();
	}
	
	//if the character is holding the other player
	//the other player needs to move too
	//overloading the move function
	public void move(Player op){
		gravity();
		movex();
		moving = false;
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			xVel -= 1;
			direction = left;
			op.addxVel(-1);
			moving = true;

		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			xVel += 1;
			direction = right;
			op.addxVel(1);
			moving = true;
		
		}
		collisions();
		soundTime += Gdx.graphics.getDeltaTime();
	}
	//this checks the collisions with the borders of the screen
	public void collisions(){
		x = Math.max(lside, x);
		x = Math.min(rside, x);
		
		y = Math.max(ground, y);
		if(y > top){
			y = top;
			yVel = 0;
		}
		
		lside = -500;
		rside = 2500;
		if(falling){
			ground = -100;
			onGround = false;
		}
		top = 1500;
	}
	public float getTextureCount(){
		return textureCount;
	}
	
	//jumping function
	public void jump(){
		//only jump when on the ground/ head
		if(onGround){
			//set yVel that slowly decreases
			yVel = 20;
			jumping = true;
			if(!soundmute){
				//doesn't play sound twice in one jump
				if(soundTime > 0.1){
					jumpsound.play();
					soundTime = 0;
				}
			}
		}
		//jump only on ground/on player
		onGround = false;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setY(float y){
		this.y = (int)y;
	}
	public void addxVel(double d){
		xVel += d;
	}
	public boolean getJumping() {
		return jumping;
	}
	public boolean getFalling() {
		return falling;
	}
	public boolean getMoving() {
		return moving;
	}
	public void setHolding(boolean b){
		holding = b;
	}
	public void setPushing(boolean b){
		pushing = b;
	}
	public boolean getHolding(){
		return holding;
	}
	public boolean getPushing(){
		return pushing;
	}
	public boolean getWinning(){
		return winning;
	}
	public boolean getonGround(){
		return onGround;
	}
	public void setDead(boolean dead){
		this.dead = dead;
	}
	public boolean getDead(){
		return dead;
	}
	//mute all the sound
	public void setSoundMute(boolean soundmute){
		this.soundmute = soundmute;
	}
	
	//checking the collisions of the players
	public void checkPlayerCollision(Player op){
		Rectangle playerRect1 = new Rectangle(x+20,y,width-40,height);
		Rectangle playerRect2 = new Rectangle(op.getX()+20,op.getY(),width-40,height-25);
		op.setHolding(false);
		op.setPushing(false);
		//check if they are pushing/ on top
		if(playerRect1.overlaps(playerRect2)){
			if(y+21 > op.getY()+height-25){
				if(falling){
					falling = false;
					//if they are on top, push them back up
					ground = (int)(op.getY())+height-26;
					op.setHolding(true);
					onGround = true;
				}		
			}
			if(y+height-op.getY() > 1){
				if(x+73 > op.getX()+width){
					//if pushing, op now moves
					op.addxVel(-1.5);
					op.setPushing(true);
				}
				if(x+width-73 < op.getX()){
					op.setPushing(true);
					op.addxVel(1.5);
				}
			}
		}
	}
	
	//checks if players are colliding with platform/ on top of it
	public void checkWinPlatformCollision(Rectangle winplatform){
		Rectangle playerRect2 = new Rectangle(x+20,y,width-40,height);
		winning = false;
		if(playerRect2.overlaps(winplatform)){
			if(y+16 > winplatform.y+winplatform.getHeight()){
				//if on top, you win
				if(falling){
					falling = false;
					ground = (int)(winplatform.y+winplatform.getHeight())-1;
					onGround = true;
					winning = true;
				}	
			}	
			//If crashing on the bottom
			if(y+height-20 < winplatform.y){
				top = (int)(winplatform.y-height);
			}
		}
		//colliding from the side
		if(playerRect2.overlaps(winplatform)){
			if(winplatform.y+winplatform.getHeight()-y > 1){
				//left side
				if(x+30 > winplatform.x+winplatform.getWidth()){
					lside = (int)(winplatform.x+winplatform.getWidth())-21;
				}
				//right side
				if(x+width-30 < winplatform.x){
					rside = (int)(winplatform.x-width)+21;
				}
			}
		}
	}
	
	//can jump on, can't fall off
	public void checkPlatCollision(ArrayList<Rectangle> plats){
		Rectangle playerRect1 = new Rectangle(x+25,y,width-50,height);
		for(Rectangle r:plats){
			if(playerRect1.overlaps(r)){
				if(y+16 > r.y+r.getHeight()){
					if(falling){
						falling = false;
						ground = (int)(r.y+r.getHeight())-1;
						onGround = true;
					}
				}
			}
		}
	}
	
	//touch spike and die
	public boolean checkSpikeCollision(ArrayList<Rectangle> spikes){
		Rectangle playerRect1 = new Rectangle(x+25,y,width-50,height);
		for(Rectangle r:spikes){
			if(playerRect1.overlaps(r)){
				//play the spike death sound
				if(!soundmute){
					spikesound.play();
				}

				return true;
			}
		}
		return false;
	}
	
	//touch spike and die
	public boolean checkSpikeUpCollision(ArrayList<Rectangle> spikes){
		Rectangle playerRect1 = new Rectangle(x+25,y,width-50,height);
		for(Rectangle r:spikes){
			if(playerRect1.overlaps(r)){
				
				if(!soundmute){
					spikesound.play();
				}
				return true;
			}
		}
		return false;
	}
	
	//for the normal block collision
	public void checkBlockCollision(ArrayList<Rectangle> blocks){
		//rects widths are shortens because they have hair sticking out
		Rectangle playerRect1 = new Rectangle(x+25,y,width-50,height);
		Rectangle playerRect2 = new Rectangle(x+20,y,width-40,height);

		for(Rectangle r:blocks){
			if(playerRect1.overlaps(r)){
				//If on top
				
				if(y+16 > r.y+r.getHeight()){
					//System.out.println(y+12+" "+(r.y+r.getHeight()));
					if(falling){
						falling = false;
						//-1 is so my character doesn't keep falling into the block and glitching
						ground = (int)(r.y+r.getHeight())-1;
						onGround = true;
					}
				}	
				//If crashing on the bottom
				if(y+height-20 < r.y){
					top = (int)(r.y-height);
				}
				
			}
			
			if(playerRect2.overlaps(r)){
				//If crashing onto the lside
				//Need to avoid the blocks im always overlapping
				if(r.y+r.getHeight()-y > 1){
					//left side
					if(x+30 > r.x+r.getWidth()){
						lside = (int)(r.x+r.getWidth())-21;
					}
					
					
					if(x+width-30 < r.x){
						rside = (int)(r.x-width)+21;
					}
					
				}
			}
		}
	}
}