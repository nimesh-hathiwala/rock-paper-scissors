package com.hathiwala.interview.ebay.game.rps;

import com.hathiwala.interview.ebay.game.rps.model.Gesture;

public class Player {

	private Gesture gesture;
	private int score;
	
	public Gesture getGesture() {
		return gesture;
	}

	public void setGesture(Gesture gesture) {
		this.gesture = gesture;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
