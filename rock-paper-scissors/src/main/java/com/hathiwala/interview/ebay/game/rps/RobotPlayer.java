package com.hathiwala.interview.ebay.game.rps;

import java.util.Random;

import com.hathiwala.interview.ebay.game.rps.model.Gesture;


public class RobotPlayer extends Player{

	private Random random = new Random();

	public Gesture play() {
		int randomNumber = random.nextInt(Gesture.values().length);
		setGesture(Gesture.values()[randomNumber]);
		return getGesture();
	}

}
