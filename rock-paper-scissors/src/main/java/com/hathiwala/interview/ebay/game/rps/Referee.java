package com.hathiwala.interview.ebay.game.rps;

import static com.hathiwala.interview.ebay.game.rps.model.Result.DRAW;
import static com.hathiwala.interview.ebay.game.rps.model.Result.PLAYER2;
import static com.hathiwala.interview.ebay.game.rps.model.Result.PLAYER1;

import com.hathiwala.interview.ebay.game.rps.model.Result;
import com.hathiwala.interview.ebay.game.rps.model.Gesture;

public class Referee {

	private Result[][] results = {
			// Rock,	Paper, 		Scissors
			{DRAW, 		PLAYER2, 	PLAYER1}, 	// Rock
			{PLAYER1, 	DRAW, 		PLAYER2}, 	// Paper
			{PLAYER2, 	PLAYER1, 	DRAW}  		// Scissors
	};
	
	public Result judge(Gesture gesture1, Gesture gesture2){
		return results[gesture1.ordinal()][gesture2.ordinal()];
	}
}
