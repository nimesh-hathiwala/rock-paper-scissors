package com.hathiwala.interview.ebay.game.rps;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hathiwala.interview.ebay.game.rps.model.Gesture;
import com.hathiwala.interview.ebay.game.rps.model.Result;

public class GameTest {

	@InjectMocks
	private Game game;
	
	@Mock
	private Referee referee;
	@Mock
	private RobotPlayer robotPlayer1;
	@Mock
	private RobotPlayer robotPlayer2;
	@Mock
	private Player player;
	@Captor
	private ArgumentCaptor<Gesture> captorGesture1;
	@Captor
	private ArgumentCaptor<Gesture> captorGesture2;

	@Before
	public void setup(){
		game = new Game();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void play_shouldInvokePlayOnBothPlayers_whenRobotVsRobot(){
		// Setup
		when(referee.judge(any(Gesture.class), any(Gesture.class))).thenReturn(Result.DRAW);
		// Test
		game.play(robotPlayer1, robotPlayer2);
		// Verify
		verify(robotPlayer1).play();
		verify(robotPlayer2).play();
	}
	
	@Test
	public void play_shouldInvokePlayOnRobotPlayer_whenHumanVsRobot(){
		// Setup
		when(referee.judge(any(Gesture.class), any(Gesture.class))).thenReturn(Result.DRAW);
		// Test
		game.play(player, robotPlayer2);
		// Verify
		verify(robotPlayer2).play();
	}

	@Test
	public void play_shouldInvokeJudgeWithCorrectParameters_whenRobotVsRobot(){
		// Setup
		when(referee.judge(any(Gesture.class), any(Gesture.class))).thenReturn(Result.DRAW);
		Gesture expectedGesture1 = Gesture.PAPER;
		Gesture expectedGesture2 = Gesture.ROCK;
		when(robotPlayer1.getGesture()).thenReturn(expectedGesture1);
		when(robotPlayer2.getGesture()).thenReturn(expectedGesture2);
		// Test
		game.play(robotPlayer1, robotPlayer2);
		// Verify
		verify(referee).judge(captorGesture1.capture(), captorGesture2.capture());
		assertEquals("Gesture is not as expected", expectedGesture1, captorGesture1.getValue());
		assertEquals("Gesture is not as expected", expectedGesture2, captorGesture2.getValue());
	}

	
	@Test
	public void play_shouldInvokeJudgeWithCorrectParameters_whenHumanVsRobot(){
		// Setup
		when(referee.judge(any(Gesture.class), any(Gesture.class))).thenReturn(Result.DRAW);
		Gesture expectedGesture1 = Gesture.PAPER;
		Gesture expectedGesture2 = Gesture.ROCK;
		when(player.getGesture()).thenReturn(expectedGesture1);
		when(robotPlayer2.getGesture()).thenReturn(expectedGesture2);
		// Test
		game.play(player, robotPlayer2);
		// Verify
		verify(referee).judge(captorGesture1.capture(), captorGesture2.capture());
		assertEquals("Gesture is not as expected", expectedGesture1, captorGesture1.getValue());
		assertEquals("Gesture is not as expected", expectedGesture2, captorGesture2.getValue());
	}
	
	@Test
	public void play_shouldOnlyUpdatePlayer1Score_whenPlayer1Wins(){
		// Setup
		int originalScore = 5;
		int expectedScore = 6;
		when(referee.judge(any(Gesture.class), any(Gesture.class))).thenReturn(Result.PLAYER1);
		when(robotPlayer1.getScore()).thenReturn(originalScore);
		// Test
		game.play(robotPlayer1, robotPlayer2);
		// Verify
		verify(robotPlayer1).setScore(expectedScore);
		verify(robotPlayer2, never()).setScore(anyInt());
	}

	@Test
	public void play_shouldOnlyUpdatePlayer2Score_whenPlayer2Wins(){
		// Setup
		int originalScore = 5;
		int expectedScore = 6;
		when(referee.judge(any(Gesture.class), any(Gesture.class))).thenReturn(Result.PLAYER2);
		when(robotPlayer2.getScore()).thenReturn(originalScore);
		// Test
		game.play(robotPlayer1, robotPlayer2);
		// Verify
		verify(robotPlayer2).setScore(expectedScore);
		verify(robotPlayer1, never()).setScore(anyInt());
	}
	
	@Test
	public void play_shouldNotUpdateAnyScore_whenResultIsDraw(){
		// Setup
		when(referee.judge(any(Gesture.class), any(Gesture.class))).thenReturn(Result.DRAW);
		// Test
		game.play(robotPlayer1, robotPlayer2);
		// Verify
		verify(robotPlayer1, never()).setScore(anyInt());
		verify(robotPlayer2, never()).setScore(anyInt());
	}
	

}
