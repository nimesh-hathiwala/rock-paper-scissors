package com.hathiwala.interview.ebay.game.rps;

import static com.hathiwala.interview.ebay.game.rps.model.Result.DRAW;
import static com.hathiwala.interview.ebay.game.rps.model.Result.PLAYER2;
import static com.hathiwala.interview.ebay.game.rps.model.Result.PLAYER1;
import static com.hathiwala.interview.ebay.game.rps.model.Gesture.PAPER;
import static com.hathiwala.interview.ebay.game.rps.model.Gesture.ROCK;
import static com.hathiwala.interview.ebay.game.rps.model.Gesture.SCISSORS;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hathiwala.interview.ebay.game.rps.model.Result;

public class RefereeTest {

	private Referee referee;
	
	@Before
	public void setup(){
		referee = new Referee();
	}
	
	@Test
	public void judge_shouldReturnDraw_whenRockVsRock(){
		Result result = referee.judge(ROCK, ROCK);
		assertEquals("Result is not as expected", DRAW, result);
	}

	@Test
	public void judge_shouldReturnPlayer2_whenRockVsPaper(){
		Result result = referee.judge(ROCK, PAPER);
		assertEquals("Result is not as expected", PLAYER2, result);
	}
	
	@Test
	public void judge_shouldReturnPlayer1_whenRockVsScissors(){
		Result result = referee.judge(ROCK, SCISSORS);
		assertEquals("Result is not as expected", PLAYER1, result);
	}
	
	@Test
	public void judge_shouldReturnPlayer1_whenPaperVsRock(){
		Result result = referee.judge(PAPER, ROCK);
		assertEquals("Result is not as expected", PLAYER1, result);
	}
	
	@Test
	public void judge_shouldReturnDraw_whenPaperVsPaper(){
		Result result = referee.judge(PAPER, PAPER);
		assertEquals("Result is not as expected", DRAW, result);
	}
	
	@Test
	public void judge_shouldReturnPlayer2_whenPaperVsScissors(){
		Result result = referee.judge(PAPER, SCISSORS);
		assertEquals("Result is not as expected", PLAYER2, result);
	}
	
	@Test
	public void judge_shouldReturnPlayer2_whenScissorsVsRock(){
		Result result = referee.judge(SCISSORS, ROCK);
		assertEquals("Result is not as expected", PLAYER2, result);
	}
	
	@Test
	public void judge_shouldReturnPlayer1_whenScissorsVsPaper(){
		Result result = referee.judge(SCISSORS, PAPER);
		assertEquals("Result is not as expected", PLAYER1, result);
	}
	
	@Test
	public void judge_shouldReturnDraw_whenScissorsVsScissors(){
		Result result = referee.judge(SCISSORS, SCISSORS);
		assertEquals("Result is not as expected", DRAW, result);
	}
	
	
}
