package com.heartstar.game;
//Zak Hu
/*This is a game that is played by one player. It is a puzzle game where the user needs to use to characters
 * to try and complete levels. To complete a level, both characters must be on the yellow platform.
 * Characters can jump on each other, carry each other or push.
 * 
 * This is a game already created and is on the App Store. This is just a copy of the original game with 
 * changes that I have added.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class HeartStar extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tmp;
	ShapeRenderer shapeRenderer;
	OrthographicCamera cam;
	Music backgroundmusic, menumusic;
	Sound fallsound, changesound, winningsound;
	
	Texture bluefL, bluejL, bluefR, bluejR, blueH, blueSad, pinkfL, pinkjL, pinkfR, pinkjR, pinkH, pinkSad;
	
	Animation platformlanimation;
	Texture platformlsheet;
	TextureRegion[] platformlframes;
	
	//all the sprites needed to do animations of players
	Animation bluestillanimation, blueholdLanimation, blueholdRanimation, blueholdWLanimation, blueholdWRanimation;
	Animation bluestillLanimation, bluestillRanimation, blueWLanimation, blueWRanimation, blueSadanimation, blueVanimation;
	Texture bluestillsheet, blueholdLsheet, blueholdRsheet, bluestillLsheet, bluestillRsheet;
	Texture blueholdWLsheet, blueholdWRsheet, blueWLsheet, blueWRsheet, blueSadsheet, blueVsheet;
	TextureRegion[] bluestillframes, blueholdLframes, blueholdRframes, blueholdWLframes, blueholdWRframes, blueWLframes, blueWRframes;
	TextureRegion[] bluestillLframes, bluestillRframes, blueSadframes, blueVframes;
	
	Animation pinkstillanimation, pinkholdLanimation, pinkholdRanimation, pinkholdWLanimation, pinkholdWRanimation;
	Animation pinkstillLanimation, pinkstillRanimation, pinkWLanimation, pinkWRanimation, pinkSadanimation, pinkVanimation;
	Texture pinkstillsheet, pinkholdLsheet, pinkholdRsheet, pinkstillLsheet, pinkstillRsheet;
	Texture pinkholdWLsheet, pinkholdWRsheet, pinkWLsheet, pinkWRsheet, pinkSadsheet, pinkVsheet;
	TextureRegion[] pinkstillframes, pinkholdLframes, pinkholdRframes, pinkholdWLframes, pinkholdWRframes, pinkWLframes, pinkWRframes;
	TextureRegion[] pinkstillLframes, pinkstillRframes, pinkSadframes, pinkVframes;
	
	Texture blueblock, pinkblock, purpleblock, blueplat,pinkplat, bluebackground, pinkbackground, winplatform, blacktex;
	Texture bluepinkbackground, tut1, tut2, tut3, tut4, tut5, skipb, info1, info2, info3, info4, winningscreen;
	Sprite blueb, pinkb, purpleb, bluep, pinkp, bluesp, pinksp, bluespup, pinkspup, bluebg, pinkbg, black, black2, bpbg;
	
	Texture menu, lvlpg1, lvlpg2, bluespike, pinkspike, bluespikeup, pinkspikeup, blueDying, pinkDying;
	Texture playb, playbd, restartb, restartbd, backb, backbd, pauseb, musicb, soundb, musicmb, soundmb, musicbd, soundbd;
	
	int [][] blueLvl, pinkLvl, purpleLvl;
	Player[] players;
	Rectangle platRect, pauseRect, playRect, restartRect, backRect, musicRect, soundRect, menuplayRect, lvlpgbackRect, lvlRect;
	Rectangle nextpageRect, prevpageRect, skipRect;
	int platRectx, platRecty, pausebx, pauseby, lvlpage;
	
	//current player, other player
	Player cp, op;
	int currentNum, levelNum, tutNum;
	int blockSize;
	float elapsedTime, dyingTime, winningTime, endTime, fallingSpeed;
	int bg, bg2;
	String state;
	int mx, my;
	int bluestartx, bluestarty, pinkstartx, pinkstarty;
	boolean fading, pause, soundmute, mouseReady, musicDown, soundDown, winningsoundplaying, tutorial;
	float alpha;
	
	//ScreenShaking
	float randomAngle, shakeRadius, offsetx, offsety;
	
	
	ArrayList<Rectangle> blueBlockList;
	ArrayList<Rectangle> pinkBlockList;
	ArrayList<Rectangle> purpleBlockList;
	ArrayList<Rectangle> bluePlatList;
	ArrayList<Rectangle> pinkPlatList;
	ArrayList<Rectangle> blueSpikeList;
	ArrayList<Rectangle> pinkSpikeList;
	ArrayList<Rectangle> blueSpikeUpList;
	ArrayList<Rectangle> pinkSpikeUpList;
	
	ArrayList<Rectangle> confettiList;
	
	@SuppressWarnings("unchecked")
	@Override
	public void create () {
		//All of the arraylists for blocks
		blueBlockList = new ArrayList<Rectangle>();
		pinkBlockList = new ArrayList<Rectangle>();
		purpleBlockList = new ArrayList<Rectangle>();
		bluePlatList = new ArrayList<Rectangle>();
		pinkPlatList = new ArrayList<Rectangle>();
		blueSpikeList = new ArrayList<Rectangle>();
		pinkSpikeList = new ArrayList<Rectangle>();
		blueSpikeUpList = new ArrayList<Rectangle>();
		pinkSpikeUpList = new ArrayList<Rectangle>();
		confettiList = new ArrayList<Rectangle>();
		
		batch = new SpriteBatch();
		
		winplatform = new Texture("Platform.png");
		menu = new Texture("Menu.png");
		lvlpg1 = new Texture("LevelSelect1.png");
		lvlpg2 = new Texture("LevelSelect2.png");
		bluepinkbackground = new Texture("BluePinkBackground.png");
		bpbg = new Sprite(bluepinkbackground);
		
		tut1 = new Texture("Tutorial1.png");
		tut2 = new Texture("Tutorial2.png");
		tut3 = new Texture("Tutorial3.png");
		tut4 = new Texture("Tutorial4.png");
		tut5 = new Texture("Tutorial5.png");
		skipb = new Texture("SkipButton.png");
		winningscreen = new Texture("WinningScreen.png");
		
		info1 = new Texture("InfoCard1.png");
		info2 = new Texture("InfoCard2.png");
		info3 = new Texture("InfoCard3.png");
		info4 = new Texture("InfoCard4.png");
		
		playb = new Texture("PlayButton.png");
		playbd = new Texture("PlayButtonDown.png");
		restartb = new Texture("RestartButton.png");
		restartbd = new Texture("RestartButtonDown.png");
		backb = new Texture("BackButton.png");
		backbd = new Texture("BackButtonDown.png");
		pauseb = new Texture("PauseButton.png");
		musicb = new Texture("MusicButton.png");
		soundb = new Texture("SoundButton.png");
		musicmb = new Texture("MusicMuteButton.png");
		soundmb = new Texture("SoundMuteButton.png");
		musicbd = new Texture("MusicButtonDown.png");
		soundbd = new Texture("SoundButtonDown.png");
		
		blueblock = new Texture("BlueBlock.png");
		pinkblock = new Texture("PinkBlock.png");
		purpleblock = new Texture("PurpleBlock.png");
		blueplat = new Texture("BluePlat.png");
		pinkplat = new Texture("PinkPlat.png");
		bluespike = new Texture("BlueSpikes.png");
		pinkspike = new Texture("PinkSpikes.png");
		bluespikeup = new Texture("BlueSpikesUp.png");
		pinkspikeup = new Texture("PinkSpikesUp.png");
		
		blueb = new Sprite(blueblock);
		pinkb = new Sprite(pinkblock);
		purpleb = new Sprite(purpleblock);
		bluep = new Sprite(blueplat);
		pinkp = new Sprite(pinkplat);
		bluesp = new Sprite(bluespike);
		pinksp = new Sprite(pinkspike);
		bluespup = new Sprite(bluespikeup);
		pinkspup = new Sprite(pinkspikeup);
		
		bluebackground = new Texture("BlueBackground.png");
		bluebg = new Sprite(bluebackground);
		pinkbackground = new Texture("PinkBackground.png");
		pinkbg = new Sprite(pinkbackground);
		blacktex = new Texture("Black.jpg");
		black = new Sprite(blacktex);
		black2 = new Sprite(blacktex);
		black.setPosition(-200, -200);
		black2.setPosition(-200, -200);
		black2.setAlpha(0.5f);
		
		blueH = new Texture("BlueHolding.png");
		pinkH = new Texture("PinkHolding.png");
		
		blueSad = new Texture("BlueSadPose.png");
		pinkSad = new Texture("PinkSadPose.png");
		
		blueDying = new Texture("BlueDying.png");
		pinkDying = new Texture("PinkDying.png");
		
		bluejL = new Texture("BlueJumpL.png");
		bluefL = new Texture("BlueFallL.png");
		bluejR = new Texture("BlueJumpR.png");
		bluefR = new Texture("BlueFallR.png");
		pinkjL = new Texture("PinkJumpL.png");
		pinkfL = new Texture("PinkFallL.png");
		pinkjR = new Texture("PinkJumpR.png");
		pinkfR = new Texture("PinkFallR.png");
		
		
		//to make all my animations, I made a spritesheet and then
		//cut every picture into a Texture Region
		platformlsheet = new Texture("PlatformLight.png");
		platformlframes = new TextureRegion[6];
		
		bluestillsheet = new Texture("BlueStillSheet.png");
		bluestillframes = new TextureRegion[4];
	    pinkstillsheet = new Texture("PinkStillSheet.png");
		pinkstillframes = new TextureRegion[4];
		
		blueholdLsheet = new Texture("BlueHoldLSheet.png");
		blueholdLframes = new TextureRegion[4];
		blueholdRsheet = new Texture("BlueHoldRSheet.png");
		blueholdRframes = new TextureRegion[4];
		pinkholdLsheet = new Texture("PinkHoldLSheet.png");
		pinkholdLframes = new TextureRegion[4];
		pinkholdRsheet = new Texture("PinkHoldRSheet.png");
		pinkholdRframes = new TextureRegion[4];
		
		bluestillLsheet = new Texture("BluestillLSheet.png");
		bluestillLframes = new TextureRegion[4];
		bluestillRsheet = new Texture("BluestillRSheet.png");
		bluestillRframes = new TextureRegion[4];
		pinkstillLsheet = new Texture("PinkstillLSheet.png");
		pinkstillLframes = new TextureRegion[4];
		pinkstillRsheet = new Texture("PinkstillRSheet.png");
		pinkstillRframes = new TextureRegion[4];
		
		blueholdWLsheet = new Texture("BlueHoldWLSheet.png");
		blueholdWLframes = new TextureRegion[6];
		blueholdWRsheet = new Texture("BlueHoldWRSheet.png");
		blueholdWRframes = new TextureRegion[6];
		
		blueWLsheet = new Texture("BlueWLSheet.png");
		blueWLframes = new TextureRegion[6];
		blueWRsheet = new Texture("BlueWRSheet.png");
		blueWRframes = new TextureRegion[6];
		pinkholdWLsheet = new Texture("PinkHoldWLSheet.png");
		pinkholdWLframes = new TextureRegion[6];
		pinkholdWRsheet = new Texture("PinkHoldWRSheet.png");
		pinkholdWRframes = new TextureRegion[6];
		pinkWLsheet = new Texture("PinkWLSheet.png");
		pinkWLframes = new TextureRegion[6];
		pinkWRsheet = new Texture("PinkWRSheet.png");
		pinkWRframes = new TextureRegion[6];
		
		blueSadsheet = new Texture("BlueSad.png");
		blueSadframes = new TextureRegion[4];
		pinkSadsheet = new Texture("PinkSad.png");
		pinkSadframes = new TextureRegion[4];
		
		blueVsheet = new Texture("BlueVictorySheet.png");
		blueVframes = new TextureRegion[5];
		pinkVsheet = new Texture("PinkVictorySheet.png");
		pinkVframes = new TextureRegion[5];
		
		//Takes in all the sprite sheets and cuts it up 145, 160
		int index = 0;
		for (int j = 0; j < 2; j++){
	    	for(int i = 0; i < 2; i++) {
	    		
	    		//cuts every sprite sheet 145 by 160 and puts them into Texture Region
	    		bluestillframes[index] = TextureRegion.split(bluestillsheet,145,160)[j][i];
	    		pinkstillframes[index] = TextureRegion.split(pinkstillsheet,145,160)[j][i];
	    		
	    		blueholdLframes[index] = TextureRegion.split(blueholdLsheet,145,160)[j][i];
	    		blueholdRframes[index] = TextureRegion.split(blueholdRsheet,145,160)[j][i];
	    		pinkholdLframes[index] = TextureRegion.split(pinkholdLsheet,145,160)[j][i];
	    		pinkholdRframes[index] = TextureRegion.split(pinkholdRsheet,145,160)[j][i];
	    		
	    		blueholdWLframes[index] = TextureRegion.split(blueholdWLsheet,145,160)[j][i];
	    		blueholdWRframes[index] = TextureRegion.split(blueholdWRsheet,145,160)[j][i];
	    		pinkholdWLframes[index] = TextureRegion.split(pinkholdWLsheet,145,160)[j][i];
	    		pinkholdWRframes[index] = TextureRegion.split(pinkholdWRsheet,145,160)[j][i];
	    		
	    		bluestillLframes[index] = TextureRegion.split(bluestillLsheet,145,160)[j][i];
	    		bluestillRframes[index] = TextureRegion.split(bluestillRsheet,145,160)[j][i];
	    		pinkstillLframes[index] = TextureRegion.split(pinkstillLsheet,145,160)[j][i];
	    		pinkstillRframes[index] = TextureRegion.split(pinkstillRsheet,145,160)[j][i];
	    		
	    		blueSadframes[index] = TextureRegion.split(blueSadsheet,145,160)[j][i];
	    		pinkSadframes[index] = TextureRegion.split(pinkSadsheet,145,160)[j][i];

	    		index++;
	    	}
	    }
		index = 0;
		for(int j = 0; j < 2; j++){
	    	for(int i = 0; i < 3; i++) {
	    		platformlframes[index] = TextureRegion.split(platformlsheet,169,85)[j][i];
	    		
	    		blueholdWLframes[index] = TextureRegion.split(blueholdWLsheet,145,160)[j][i];
	    		blueholdWRframes[index] = TextureRegion.split(blueholdWRsheet,145,160)[j][i];
	    		pinkholdWLframes[index] = TextureRegion.split(pinkholdWLsheet,145,160)[j][i];
	    		pinkholdWRframes[index] = TextureRegion.split(pinkholdWRsheet,145,160)[j][i];
	    		
	    		
	    		blueWLframes[index] = TextureRegion.split(blueWLsheet,145,160)[j][i];
	    		blueWRframes[index] = TextureRegion.split(blueWRsheet,145,160)[j][i];
	    		pinkWLframes[index] = TextureRegion.split(pinkWLsheet,145,160)[j][i];
	    		pinkWRframes[index] = TextureRegion.split(pinkWRsheet,145,160)[j][i];
	    		
	    		if(index != 5){
	    			blueVframes[index] = TextureRegion.split(blueVsheet,145,160)[j][i];
		    		pinkVframes[index] = TextureRegion.split(pinkVsheet,145,160)[j][i];
	    		}
	    		index++;
	    	}
	    }
		//takes all the texture regions and turns them into animations
		//the number means how long each frame lasts
		//this draws all the animations for my game
		platformlanimation = new Animation(0.15f,platformlframes);
		
	    bluestillanimation = new Animation(0.5f,bluestillframes);
	    pinkstillanimation = new Animation(0.5f,pinkstillframes);
	    
	    blueholdLanimation = new Animation(0.5f,blueholdLframes);
	    blueholdRanimation = new Animation(0.5f,blueholdRframes);
	    pinkholdLanimation = new Animation(0.5f,pinkholdLframes);
	    pinkholdRanimation = new Animation(0.5f,pinkholdRframes);
	    
	    bluestillLanimation = new Animation(0.5f,bluestillLframes);
	    bluestillRanimation = new Animation(0.5f,bluestillRframes);
	    pinkstillLanimation = new Animation(0.5f,pinkstillLframes);
	    pinkstillRanimation = new Animation(0.5f,pinkstillRframes);
	    
	    blueholdWLanimation = new Animation(0.10f,blueholdWLframes);
	    blueholdWRanimation = new Animation(0.10f,blueholdWRframes);
	    pinkholdWLanimation = new Animation(0.10f,pinkholdWLframes);
	    pinkholdWRanimation = new Animation(0.10f,pinkholdWRframes);
	    
	    blueWLanimation = new Animation(0.10f,blueWLframes);
	    blueWRanimation = new Animation(0.10f,blueWRframes);
	    pinkWLanimation = new Animation(0.10f,pinkWLframes);
	    pinkWRanimation = new Animation(0.10f,pinkWRframes);
	    
	    blueSadanimation = new Animation(0.15f,blueSadframes);
	    pinkSadanimation = new Animation(0.15f,pinkSadframes);
	    
	    blueVanimation = new Animation(0.15f,blueVframes);
	    pinkVanimation = new Animation(0.15f,pinkVframes);

	    shapeRenderer = new ShapeRenderer();
	    //I use orthographic camera to do my shaking when the characters die
	    cam = new OrthographicCamera(1920,1080);
	    cam.setToOrtho(false);
	    cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
	    cam.update();
	    
	    //music
	    //I loop all the music so they never stop playing
	    backgroundmusic = Gdx.audio.newMusic(Gdx.files.internal("HeartStarBackgroundSong.mp3"));
	    backgroundmusic.setLooping(true);
	    menumusic = Gdx.audio.newMusic(Gdx.files.internal("MenuSong.mp3"));
	    menumusic.setLooping(true);
	    
	    //sounds
	    fallsound = Gdx.audio.newSound(Gdx.files.internal("Fall.wav"));
	    changesound = Gdx.audio.newSound(Gdx.files.internal("Change.wav"));
	    winningsound = Gdx.audio.newSound(Gdx.files.internal("Winning.wav"));
	    
	    //this hold the characters
		players = new Player[2];
		
		blockSize = 112;
		elapsedTime = 0f;
		dyingTime = 0f;
		winningTime = 0f;
		endTime = 0f;
		bg = 0;
		bg2 = 0;
		fading = false;
		pause = false;
		soundmute = false;
		mouseReady = true;
		musicDown = false;
		soundDown = false;
		winningsoundplaying = false;
		tutorial = true;
		pausebx = 1792;
		pauseby = 956;
		pauseRect = new Rectangle(pausebx, pauseby, 128, 124);
		playRect = new Rectangle(859, 190, 202, 212);
		restartRect = new Rectangle(1209, 190, 202, 212);
		backRect = new Rectangle(509, 190, 202, 212);
		musicRect = new Rectangle(684, 850, 202, 212);
		soundRect = new Rectangle(1034, 850, 202, 212);
		menuplayRect = new Rectangle(765,45,390,159);
		lvlpgbackRect = new Rectangle(22,22,202,212);
		nextpageRect = new Rectangle(1671,456,202,212);
		prevpageRect = new Rectangle(118,456,202,212);
		skipRect = new Rectangle(1770,950,202,212);
		
		
		fallingSpeed = -15;
		
		
		shakeRadius = 0;
		
		currentNum = 0;
		levelNum = 1;
		tutNum = 1;
		
		//the game starts off in the menu
		state = "menu";
		
	}
	
	//gets everything from txt files and makes the lvl
	//I generate a new lvl each time a new level is loaded
	public void generateLvl(int num){
		try {
			//creates three 2d arrays files, one from each colour
			blueLvl = createMap("BlueMap"+num+".txt");
			pinkLvl = createMap("PinkMap"+num+".txt");
			purpleLvl = createMap("PurpleMap"+num+".txt");
			
			//gets the starting positions of the plat and both players
			Scanner startingLocations = new Scanner(new BufferedReader(new FileReader("Starting"+num+".txt")));
			bluestartx = Integer.parseInt(startingLocations.nextLine());
			bluestarty = Integer.parseInt(startingLocations.nextLine());
			pinkstartx = Integer.parseInt(startingLocations.nextLine());
			pinkstarty = Integer.parseInt(startingLocations.nextLine());
			players[0] = new Player(bluestartx, bluestarty);
			players[1] = new Player(pinkstartx, pinkstarty);
			cp = players[currentNum];
			op = players[1-currentNum];
			
			//get the x and y coordinates for the final platform
			platRectx = Integer.parseInt(startingLocations.nextLine());
			platRecty = Integer.parseInt(startingLocations.nextLine());
			platRect = new Rectangle(platRectx+25,platRecty+32,224-25,80);
			
			//clears the lists from previous maps
			blueBlockList.clear();
			pinkBlockList.clear();
			purpleBlockList.clear();
			bluePlatList.clear();
			pinkPlatList.clear();
			blueSpikeList.clear();
			pinkSpikeList.clear();
			blueSpikeUpList.clear();
			pinkSpikeUpList.clear();
			
			//adds a different object based on what number it is
			for(int y = 0; y < 9; y++){
				for(int x = 0; x < 17; x++){
					if(blueLvl[y][x] == 1){
						blueBlockList.add(new Rectangle(x*blockSize, (9-y)*blockSize,112,112));
					}
					if(blueLvl[y][x] == 4){
						bluePlatList.add(new Rectangle(x*blockSize, (9-y)*blockSize,112,112));
					}
					if(blueLvl[y][x] == 6){
						blueSpikeList.add(new Rectangle(x*blockSize+2, (9-y)*blockSize,108,56));
					}
					if(blueLvl[y][x] == 8){
						blueSpikeUpList.add(new Rectangle(x*blockSize+2, (9-y)*blockSize+56,108,56));
					}
					if(pinkLvl[y][x] == 2){
						pinkBlockList.add(new Rectangle(x*blockSize, (9-y)*blockSize,112,112));
					}
					if(pinkLvl[y][x] == 5){
						pinkPlatList.add(new Rectangle(x*blockSize, (9-y)*blockSize,112,112));
					}
					if(pinkLvl[y][x] == 7){
						pinkSpikeList.add(new Rectangle(x*blockSize+2, (9-y)*blockSize,108,56));
					}
					if(pinkLvl[y][x] == 9){
						pinkSpikeUpList.add(new Rectangle(x*blockSize+2, (9-y)*blockSize+56,108,56));
					}
					if(purpleLvl[y][x] == 3){
						purpleBlockList.add(new Rectangle(x*blockSize, (9-y)*blockSize,112,112));
					}
				}
			}

			startingLocations.close();
	    } 
		catch (Exception e) {
	    	e.printStackTrace();
	    }
		
	}
	
	@Override
	public void render() {
		//either screen is at menu, level select or game
		if(state.equals("menu")){
			menu();
		}
		else if(state.equals("levelselect")){
			levelselect();
		}
		else if(state.equals("game")){
			if(levelNum == 1 && tutorial){
				tutorial();
			}
			//if the user beat the game
			else if(levelNum >= 18){
				winningScreen();
			}
			else{
				game();
			}
		}	
	}
	
	public void menu(){
		batch.begin();
		batch.draw(menu, 0, 0);
		batch.end();
		
		mx = Gdx.input.getX();
		my = 1080-Gdx.input.getY();
		
		
		//play button takes you to level select screen
		if(menuplayRect.contains(mx, my)){
			if(Gdx.input.isButtonPressed(Buttons.LEFT)){
				cp = players[0];
				op = players[1];
			    state = "levelselect";
			}
		}
		//menu music begins
		menumusic.play();
	}
	
	public void levelselect(){
		batch.begin();
		//checks which screen to draw
		if(levelNum <= 10){
			batch.draw((lvlpg1), 0, 0);
			lvlpage = 0;
		}
		else{
			batch.draw((lvlpg2), 0, 0);
			lvlpage = 1;
		}
		batch.end();
		mx = Gdx.input.getX();
		my = 1080-Gdx.input.getY();
		//buttons that are on the level screen
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && mouseReady){
			if(lvlpgbackRect.contains(mx, my)){
				state = "menu";
				mouseReady = false;
			}
			else if(nextpageRect.contains(mx, my)){
				levelNum += 10;
				levelNum = Math.min(levelNum, 11);
				mouseReady = false;
			}
			else if(prevpageRect.contains(mx, my)){
				levelNum -= 10;
				levelNum = Math.max(levelNum, 1);
				mouseReady = false;
			}
			for(int i = 1; i >= 0; i--){
				for(int j = 0; j < 5; j++){
					lvlRect = new Rectangle(377+j*259, 308+i*288, 202, 212);
					if(lvlRect.contains(mx, my)){
						levelNum = (lvlpage*10)+(1-i)*5 + (j+1);
						//17 is the highest level
						if(levelNum <= 17){
							state = "game";
							//gets the level number
							
							generateLvl(levelNum);
							tutorial = true;
							tutNum = 1;
							mouseReady = false;
						}	
					}
				}
			}
		}
		else{
			mouseReady = true;
		}
		
		backgroundmusic.stop();
		menumusic.play();
	}
	
	public void restart(){
		//renews the players
		players[0] = new Player(bluestartx, bluestarty);
		players[1] = new Player(pinkstartx, pinkstarty);
		cp = players[currentNum];
		op = players[1-currentNum];
	}
	
	public void game(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//the batch draws according to the camera
		batch.begin();
		batch.setProjectionMatrix(cam.combined);
		//always draws the backgrounds and the blocks
		drawingObjects();
		//I draw the tutorial images depending on the level
		if(levelNum == 1){
			batch.draw(info1, 392, 700);
		}
		if(levelNum == 3){
			batch.draw(info2, 1123, 675);
		}
		if(levelNum == 4){
			batch.draw(info3, 1180, 630);
		}
		if(levelNum == 8){
			batch.draw(info4, 955, 560);
		}
		//play the game music
		//stop the menu music
		backgroundmusic.play();
		menumusic.stop();
		
		//if someone is dead
		if(cp.getDead()){
			backgroundmusic.pause();
			if(currentNum == 0){
				//animation differs based on how long they have been dead
				if(dyingTime > .6){
					//this draws it after it stops his animation
					//makes sure the animation only happens once
					batch.draw(pinkSad,op.getX(), op.getY());
				}
				else{
					//this draws the animation
					batch.draw((TextureRegion) pinkSadanimation.getKeyFrame(dyingTime, true),op.getX(), op.getY());
				}
				batch.draw(blueDying,cp.getX(), cp.getY());
				cp.setY(cp.getY()-fallingSpeed);
			}
			else{
				if(dyingTime > .6){
					batch.draw(blueSad,op.getX(), op.getY());
				}
				else{
					batch.draw((TextureRegion) blueSadanimation.getKeyFrame(dyingTime, true),op.getX(), op.getY());
				}
				batch.draw(pinkDying,cp.getX(), cp.getY());
				cp.setY(cp.getY()-fallingSpeed);
			}
			//falling speed is originally -15 but it increases in the negative direction
			fallingSpeed += 0.7;
			dyingTime += Gdx.graphics.getDeltaTime();
		}
		//if the character you aren't controlling dies
		else if(op.getDead()){
			backgroundmusic.pause();
			if(currentNum == 0){
				if(dyingTime > .6){
					batch.draw(blueSad,cp.getX(), cp.getY());
				}
				else{
					batch.draw((TextureRegion) blueSadanimation.getKeyFrame(dyingTime, true),cp.getX(), cp.getY());
				}
				batch.draw(pinkDying,op.getX(), op.getY());
				op.setY(op.getY()-fallingSpeed);
			}
			else{
				if(dyingTime > .6){
					batch.draw(pinkSad,cp.getX(), cp.getY());
				}
				else{
					batch.draw((TextureRegion) pinkSadanimation.getKeyFrame(dyingTime, true),cp.getX(), cp.getY());
				}
				batch.draw(blueDying,op.getX(), op.getY());
				op.setY(op.getY()-fallingSpeed);
			}
			fallingSpeed += 0.7;
			dyingTime += Gdx.graphics.getDeltaTime();
		}
		//if paused, can't control players
		else if(pause){
			//still draws the players
			drawingPlayers();
			batch.end();
			pause();
			batch.begin();
		}
		//if both players are on the winning platform
		else if(cp.getWinning() && op.getWinning() || cp.getWinning() && cp.getHolding() || op.getWinning() && op.getHolding()){
			backgroundmusic.pause();
			//check if blue or pink is on the left/right
			if(players[0].getX() > players[1].getX()){
				//animation is offset so they cheer at different times
				batch.draw((TextureRegion) pinkVanimation.getKeyFrame(winningTime+0.375f, true), platRectx-5, platRecty+112);
				batch.draw((TextureRegion) blueVanimation.getKeyFrame(winningTime, true), platRectx+106, platRecty+112);
			}
			else{
				batch.draw((TextureRegion) blueVanimation.getKeyFrame(winningTime+0.375f, true), platRectx-5, platRecty+112);
				batch.draw((TextureRegion) pinkVanimation.getKeyFrame(winningTime, true), platRectx+106, platRecty+112);
			}
			//play sound only once
			if(!winningsoundplaying){
				winningsound.play();
				winningsoundplaying = true;
			}
			
			winningTime += Gdx.graphics.getDeltaTime();
		}
		else{
			//if just playing normally
			drawingPlayers();
		    cam.update();
			playing();
		}
		//after winning, it goes to next level
		if(winningTime > 3){
			levelNum++;
			winningTime = 0;
			winningsoundplaying = false;
			backgroundmusic.play();
			//only do new level if it's under 18 because 17 is the last level
			if(levelNum < 18){
				generateLvl(levelNum);
			}
		}
		//fading after 1.8 seconds
		else if(winningTime > 1.8){
			fading = true;
		}
		
		//after dying, the level resets
		if(dyingTime > 3){
			//retarts after 3 seconds
			restart();
			backgroundmusic.play();
			dyingTime = 0;
			fallingSpeed = -15;
		}
		else if(dyingTime > 1.8){
			//fade after death
			fading = true;
		}
		//after dying, the screen shakes
		if(dyingTime > 0 && dyingTime < 0.4){
			//the radius is how much the screen shakes
			shakeRadius = 5;
		}
		if(shakeRadius >= 1){
			//Shake random directions
			//shake radius decreases
			shakeRadius *= 0.9;
			randomAngle = (float) (Math.random()*2*Math.PI);
			//random offset
			offsetx = (float) (Math.cos(randomAngle)*shakeRadius);
			offsety = (float) (Math.sin(randomAngle)*shakeRadius);
			//offset the camera in random directions
			cam.translate(offsetx, offsety);
		}
		else{
			//Reset the position
			cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		}
		
		/*
		if(Gdx.input.isKeyPressed(Keys.R)){
			restart();
		}
		*/
		
		 //Fading black
    	alpha = (float)(black.getColor().a);
    	//change alpha value of black image
        if(fading){
        	if(black.getColor().a >= 0.97f){
        		black.setAlpha(1f);
        		fading = false;
        	}
        	else{
            	black.setAlpha(alpha+0.02f);
        	}

        }
        else{
            if(black.getColor().a <= 0.03){
            	black.setAlpha(0f);
        	}
            else{
                black.setAlpha(alpha-0.02f);
            }
            
        }
    	//draw the black image
        black.draw(batch);
		
        //add time for all the animations
		elapsedTime += Gdx.graphics.getDeltaTime();
		cam.update();
		shapeRenderer.end();
		batch.end();
	}
	
	public void tutorial(){
		batch.begin();
		//tutorial has moving background
		bpbg.setPosition(0, bg2);
		bpbg.draw(batch);
		//moving the background
		bg2 -= 1;
		if(bg2 <= -1080){
			bg2 = 0;
		}
		//draws tutorial images
		batch.draw(tut1, 150, 585);
		if(tutNum >= 2){
			batch.draw(tut2, 150, 585);
		}
		if(tutNum >= 3){
			batch.draw(tut3, 977, 585);
		}
		if(tutNum >= 4){
			batch.draw(tut4, 150, 36);
		}
		if(tutNum >= 5){
			batch.draw(tut5, 977, 36);
		}
		if(tutNum >= 6){
			tutorial = false;
		}
		//press space or enter to go to next image
		if(Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyJustPressed(Keys.ENTER)){
			tutNum ++;
		}
		batch.end();
	}
	
	//draws the winning screen for 10 seconds
	public void winningScreen(){
		if(endTime <= 10){
			//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(winningscreen, 0, 0);
			batch.end();
			//draw confetti
			confetti();
			endTime += Gdx.graphics.getDeltaTime();
		}
		else{
			//goes back to the menu
			endTime = 0;
			state = "menu";
			levelNum = 1;
			backgroundmusic.stop();
			confettiList.clear();
		}
	}
	
	public void drawingObjects(){
		//setting the alpha values of the blocks to make them transparent
		if(currentNum == 0){
			pinkb.setAlpha(.4f);
			blueb.setAlpha(1f);
			pinkp.setAlpha(.4f);
			bluep.setAlpha(1f);
			pinksp.setAlpha(.4f);
			bluesp.setAlpha(1f);
			pinkspup.setAlpha(.4f);
			bluespup.setAlpha(1f);
			bluebg.setPosition(bg, bg);
			bluebg.draw(batch);
			//moving the background
			bg += 1;
			if(bg >= 0){
				bg = -900;
			}
		}
		else{
			pinkb.setAlpha(1f);
			blueb.setAlpha(.4f);
			pinkp.setAlpha(1f);
			bluep.setAlpha(.4f);
			pinksp.setAlpha(1f);
			bluesp.setAlpha(.4f);
			pinkspup.setAlpha(1f);
			bluespup.setAlpha(.4f);
			pinkbg.setPosition(bg, bg);
			pinkbg.draw(batch);
			bg -= 1;
			if(bg <= -900){
				bg = 0;
			}
		}
		
		//drawing blocks
		for(int y = 0; y < 9; y++){
			for(int x = 0; x < 17; x++){
				//Blue blocks need to be drawn second to cover pink
				if(currentNum == 0){
					/* 1  blue block
					 * 2  pink block
					 * 3  purple block
					 * 4  blue platform
					 * 5  pink platform
					 * 6  blue spikes (facing up)
					 * 7  pink spikes
					 * 8  blue spikes (facing down)
					 * 9 pink spikes
					 */
					if(pinkLvl[y][x] == 2){
						//Y value is inverted so I take height of 2d array - y
						pinkb.setPosition(x*blockSize, (9-y)*blockSize);
						pinkb.draw(batch);
					}
					else if(pinkLvl[y][x] == 5){
						pinkp.setPosition(x*blockSize, ((9-y)*blockSize) + 56);
						pinkp.draw(batch);
					}
					else if(pinkLvl[y][x] == 7){
						pinksp.setPosition(x*blockSize+2, ((9-y)*blockSize));
						pinksp.draw(batch);
					}
					else if(pinkLvl[y][x] == 9){
						pinkspup.setPosition(x*blockSize+2, ((9-y)*blockSize) + 56);
						pinkspup.draw(batch);
					}
					if(blueLvl[y][x] == 1){
						blueb.setPosition(x*blockSize, (9-y)*blockSize);
						blueb.draw(batch);
					}
					else if(blueLvl[y][x] == 4){
						bluep.setPosition(x*blockSize, ((9-y)*blockSize) + 56);
						bluep.draw(batch);
					}
					else if(blueLvl[y][x] == 6){
						bluesp.setPosition(x*blockSize+2, ((9-y)*blockSize));
						bluesp.draw(batch);
					}
					else if(blueLvl[y][x] == 8){
						bluespup.setPosition(x*blockSize+2, ((9-y)*blockSize) + 56);
						bluespup.draw(batch);
					}
				}
				//repeat code needed for it to look nice, blues are drawn first this time
				else{
					if(blueLvl[y][x] == 1){
						blueb.setPosition(x*blockSize, (9-y)*blockSize);
						blueb.draw(batch);
					}
					else if(blueLvl[y][x] == 4){
						bluep.setPosition(x*blockSize, ((9-y)*blockSize) + 56);
						bluep.draw(batch);
					}
					else if(blueLvl[y][x] == 6){
						bluesp.setPosition(x*blockSize+2, ((9-y)*blockSize));
						bluesp.draw(batch);
					}
					else if(blueLvl[y][x] == 8){
						bluespup.setPosition(x*blockSize+2, ((9-y)*blockSize) + 56);
						bluespup.draw(batch);
					}
					if(pinkLvl[y][x] == 2){
						pinkb.setPosition(x*blockSize, (9-y)*blockSize);
						pinkb.draw(batch);
					}
					else if(pinkLvl[y][x] == 5){
						pinkp.setPosition(x*blockSize, ((9-y)*blockSize) + 56);
						pinkp.draw(batch);
					}
					else if(pinkLvl[y][x] == 7){
						pinksp.setPosition(x*blockSize+2, ((9-y)*blockSize));
						pinksp.draw(batch);
					}
					else if(pinkLvl[y][x] == 9){
						pinkspup.setPosition(x*blockSize+2, ((9-y)*blockSize) + 56);
						pinkspup.draw(batch);
					}
				}
				if(purpleLvl[y][x] == 3){
					purpleb.setPosition(x*blockSize, (9-y)*blockSize);
					purpleb.draw(batch);
				}
			}
		}
		//draws the winning platform
		batch.draw(winplatform, platRectx, platRecty);
		batch.draw((TextureRegion) platformlanimation.getKeyFrame(elapsedTime, true), platRectx+28, platRecty+113);
		batch.draw(pauseb, pausebx, pauseby);
		
	}
	
	public void playing(){
		//drawing movement
		//shapeRenderer.rect(0,0,100,100);
		if(cp.getHolding()||cp.getPushing()){
			cp.move(op);
		}
		else{
			cp.move();
		}
		//other player has gravity and can be pushed but is not controlled
		op.gravity();
		op.movex();
		op.collisions();
		
		//check collisions that affect both players
		cp.checkPlayerCollision(op);
		op.checkPlayerCollision(cp);
		cp.checkBlockCollision(purpleBlockList);
		op.checkBlockCollision(purpleBlockList);
		cp.checkWinPlatformCollision(platRect);
		op.checkWinPlatformCollision(platRect);
		
		
		if(currentNum == 0){
			//check all the collisions that affect separate players
			cp.checkBlockCollision(blueBlockList);
			op.checkBlockCollision(pinkBlockList);
			cp.checkPlatCollision(bluePlatList);
			op.checkPlatCollision(pinkPlatList);
			//check if they hit the spikes
			if(cp.checkSpikeCollision(blueSpikeList) || cp.checkSpikeUpCollision(blueSpikeUpList)){
				cp.setDead(true);
			}
			if(op.checkSpikeCollision(pinkSpikeList) || op.checkSpikeUpCollision(pinkSpikeUpList)){
				op.setDead(true);
			}
		}
		else{
			cp.checkBlockCollision(pinkBlockList);
			op.checkBlockCollision(blueBlockList);
			cp.checkPlatCollision(pinkPlatList);
			op.checkPlatCollision(bluePlatList);
			if(cp.checkSpikeCollision(pinkSpikeList) || cp.checkSpikeUpCollision(pinkSpikeUpList)){
				cp.setDead(true);
			}
			if(op.checkSpikeCollision(blueSpikeList) || op.checkSpikeUpCollision(blueSpikeUpList)){
				op.setDead(true);
			}
		}
		
		//if they fall off the screen
		if(cp.getY() <= -100){
			//soundmute can be changed in pause menu
			if(!soundmute){
				fallsound.play();
			}
			//they are dead
			cp.setDead(true);
		}
		if(op.getY() <= -100){
			if(!soundmute){
				fallsound.play();
			}
			op.setDead(true);
		}	
		
		//control the other player
        changePlayer();
        
        
        
        //clicking buttonssss
        mx = Gdx.input.getX();
		my = 1080-Gdx.input.getY();
		//to pause
        if(pauseRect.contains(mx, my)){
			if(Gdx.input.isButtonPressed(Buttons.LEFT)){
				pause = true;
			}
		}
	}
	
	public void drawingPlayers(){
		//if you are the blue player
		if(currentNum == 0){
			if(op.getHolding()){
				//draws this if other player is holding the current player
				batch.draw(pinkH, op.getX(), op.getY());
			}
			else{
				//if other player is doing nothing
			    batch.draw((TextureRegion) pinkstillanimation.getKeyFrame(elapsedTime, true),op.getX(), op.getY());
			}
			
			//if facing the left direction
			if(cp.getDirection() == 0){
				//if current player is jumping
				if(cp.getJumping()){
					batch.draw(bluejL, cp.getX(), cp.getY());
				}
				//if player is falling
				else if(cp.getFalling()){
					batch.draw(bluefL, cp.getX(), cp.getY());
				}
				//if player is holding the other player
				else if(cp.getHolding()){
					if(cp.getMoving()){
					    batch.draw((TextureRegion) blueholdWLanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
					}
					else{
					    batch.draw((TextureRegion) blueholdLanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
					}
				}
				//if player is walking
				else if(cp.getMoving()){
					batch.draw((TextureRegion) blueWLanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
				}
				//if player is doing nothing
				else{
					batch.draw((TextureRegion) bluestillLanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
				}
			}
			//facing the right direction
			else if(cp.getDirection() == 1){
				if(cp.getJumping()){
					batch.draw(bluejR, cp.getX(), cp.getY());
				}
				else if(cp.getFalling()){
					batch.draw(bluefR, cp.getX(), cp.getY());
				}
				else if(cp.getHolding()){
					if(cp.getMoving()){
					    batch.draw((TextureRegion) blueholdWRanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
					}
					else{
					    batch.draw((TextureRegion) blueholdRanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
					}
				}
				else if(cp.getMoving()){
					batch.draw((TextureRegion) blueWRanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
				}
				else{
					batch.draw((TextureRegion) bluestillRanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
				}
			}
		}
		else{
			//other character sprites
			if(op.getHolding()){
				batch.draw(blueH, op.getX(), op.getY());
			}
			else{
		        batch.draw((TextureRegion) bluestillanimation.getKeyFrame(elapsedTime, true),op.getX(), op.getY());
			}
			
			
			if(cp.getDirection() == 0){
				if(cp.getJumping()){
					batch.draw(pinkjL, cp.getX(), cp.getY());
				}
				else if(cp.getFalling()){
					batch.draw(pinkfL, cp.getX(), cp.getY());
				}
				else if(cp.getHolding()){
					if(cp.getMoving()){
					    batch.draw((TextureRegion) pinkholdWLanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
					}
					else{
					    batch.draw((TextureRegion) pinkholdLanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
					}
				}
				else if(cp.getMoving()){
					batch.draw((TextureRegion) pinkWLanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
				}
				else{
					batch.draw((TextureRegion) pinkstillLanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
				}
			}
			else if(cp.getDirection() == 1){
				if(cp.getJumping()){
					batch.draw(pinkjR, cp.getX(), cp.getY());
				}
				else if(cp.getFalling()){
					batch.draw(pinkfR, cp.getX(), cp.getY());
				}
				else if(cp.getHolding()){
					if(cp.getMoving()){
					    batch.draw((TextureRegion) pinkholdWRanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
					}
					else{
					    batch.draw((TextureRegion) pinkholdRanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
					}
				}
				else if(cp.getMoving()){
					batch.draw((TextureRegion) pinkWRanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
				}
				else{
					batch.draw((TextureRegion) pinkstillRanimation.getKeyFrame(elapsedTime, true),cp.getX(), cp.getY());
				}
			}
		}
	}
	
	public void pause(){
		mx = Gdx.input.getX();
		my = 1080-Gdx.input.getY();
		batch.begin();
		//fading
		black2.draw(batch);
		batch.draw(playb, 859, 190);
		batch.draw(restartb, 1209, 190);
		batch.draw(backb, 509, 190);
		//check to see if music is muted or not
		if(backgroundmusic.getVolume() == 0 && !musicDown){
			batch.draw(musicmb, 684, 850);
		}
		else if(backgroundmusic.getVolume() == 1 && !musicDown){
			batch.draw(musicb, 683, 850);
		}
		if(soundmute && !soundDown){
			batch.draw(soundmb, 1034, 850);
		}
		else if(!soundmute && !soundDown){
			batch.draw(soundb, 1034, 850);
		}
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT)){
			//draws the picture of button that's being pressed down
			if(playRect.contains(mx, my)){
				batch.draw(playbd, 859, 190);
			}
			else if(restartRect.contains(mx, my)){
				batch.draw(restartbd, 1209, 190);
			}
			else if(backRect.contains(mx, my)){
				batch.draw(backbd, 509, 190);
			}
			else if(musicRect.contains(mx, my)){
				musicDown = true;
				if(musicDown){
					batch.draw(musicbd, 684, 850);
				}				
			}
			else if(soundRect.contains(mx, my)){
				soundDown = true;
				if(soundDown){
					batch.draw(soundbd, 1034, 850);
				}		
				
			}
			if(mouseReady){
				//does the function of buttons in the pause screen
				if(playRect.contains(mx, my)){
					pause = false;
					mouseReady = false;
				}
				else if(restartRect.contains(mx, my)){
					pause = false;
					mouseReady = false;
					restart();
				}
				else if(backRect.contains(mx, my)){
					pause = false;
					mouseReady = false;
					state = "levelselect";
				}
				else if(musicRect.contains(mx, my)){
					//change background volume
					backgroundmusic.setVolume(1-backgroundmusic.getVolume());
					mouseReady = false;
				}
				else if(soundRect.contains(mx, my)){
					//changed soundmute
					cp.setSoundMute(!soundmute);
					op.setSoundMute(!soundmute);
					soundmute = !soundmute;
					mouseReady = false;
				}
			}			
		}
		else{
			//reset everything if mouse button is not down
			mouseReady = true;
			musicDown = false;
			soundDown = false;
		}
		
		//press escape to exit
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			pause = false;
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		blueblock.dispose();
		pinkblock.dispose();
	}
	
	public static int[][] createMap(String filename) throws Exception {
		//Takes in a text file and returns a 2d array
        int[][] mapLevel = new int [9][17];
        Scanner inFile = new Scanner(new BufferedReader(new FileReader(filename)));
        int row = 0;
        while(inFile.hasNextLine()){
        	//splits all the spaces
            String[] vals = inFile.nextLine().trim().split("\\s+");
            for(int col = 0; col < 17; col++){
            	//takes in the numbers in the text file
                mapLevel[row][col] = Integer.parseInt(vals[col]);
            }
            row++;
        }
        inFile.close();

        return mapLevel;
    }
	
	public void changePlayer(){
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
			//only switch player when on the ground
			if(cp.getonGround()&&op.getonGround()){
				if(!soundmute){
					changesound.play();
				}
				op = players[currentNum];
				currentNum = 1-currentNum;
				cp = players[currentNum];
			}
		}
	}
	
	//this creates rectangles that fall from the top of the screen
	public void confetti(){
		double randInt = (Math.random()*7);
		//randomly creates a confetti, only a slight chance each time
		if(randInt < 1){
			confettiList.add(new Rectangle((int)(Math.random()*1920), 1080, 5,5));
		}
		
		//draws the confetti
		shapeRenderer.begin(ShapeType.Filled);
		for(Rectangle r: confettiList){
			//sets a random colour and adds a random x value while subtracting y value
			//shapeRenderer.setColor((float)(Math.random()*1), (float)(Math.random()*1), (float)(Math.random()*1), 1);
			shapeRenderer.setColor(134,57,134,1);
			r.y -= 2;
			r.x += (int)(Math.random()*6)-3;
			shapeRenderer.rect(r.x, r.y, 10, 10);
		}
		
		shapeRenderer.end();
	}
}
