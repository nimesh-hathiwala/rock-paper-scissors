package com.hathiwala.interview.ebay.game.rps;

import com.hathiwala.interview.ebay.game.rps.model.Result;

public class Game {

	private Referee referee = new Referee();
	
	public Result play(Player player1, Player player2) {
		if(player1 instanceof RobotPlayer){
			((RobotPlayer) player1).play();
		}
		if(player2 instanceof RobotPlayer){
			((RobotPlayer) player2).play();
		}
		Result result = referee.judge(player1.getGesture(), player2.getGesture());
		updateScores(player1, player2, result);
		return result;
	}

	private void updateScores(Player player1, Player player2, Result result) {
		switch(result){
		case PLAYER1:
			player1.setScore(player1.getScore()+1);
			break;
		case PLAYER2:
			player2.setScore(player2.getScore()+1);
			break;
		}
	}

}
