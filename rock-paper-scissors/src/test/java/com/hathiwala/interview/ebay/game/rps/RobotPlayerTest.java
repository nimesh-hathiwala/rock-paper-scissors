package com.hathiwala.interview.ebay.game.rps;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hathiwala.interview.ebay.game.rps.model.Gesture;

public class RobotPlayerTest {

	@InjectMocks
	private RobotPlayer robot;
	
	@Mock
	private Random random;
	
	@Before
	public void setup(){
		robot = new RobotPlayer();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void play_shouldReturnGesture_whenRandomNumberIs0(){
		// Setup
		Gesture expectedGesture = Gesture.ROCK;
		when(random.nextInt(anyInt())).thenReturn(0);
		// Test
		Gesture gesture = robot.play();
		// Verify
		assertEquals("Gesture is not as expected", expectedGesture, gesture);
	}

	@Test
	public void play_shouldReturnGesture_whenRandomNumberIs1(){
		// Setup
		Gesture expectedGesture = Gesture.PAPER;
		when(random.nextInt(anyInt())).thenReturn(1);
		// Test
		Gesture gesture = robot.play();
		// Verify
		assertEquals("Gesture is not as expected", expectedGesture, gesture);
	}
	
	@Test
	public void play_shouldReturnGesture_whenRandomNumberIs2(){
		// Setup
		Gesture expectedGesture = Gesture.SCISSORS;
		when(random.nextInt(anyInt())).thenReturn(2);
		// Test
		Gesture gesture = robot.play();
		// Verify
		assertEquals("Gesture is not as expected", expectedGesture, gesture);
	}
	
	@Test
	public void play_shouldSetGestureInObject(){
		// Setup
		Gesture expectedGesture = Gesture.SCISSORS;
		when(random.nextInt(anyInt())).thenReturn(2);
		// Test
		robot.play();
		// Verify
		assertEquals("Gesture is not as expected", expectedGesture, robot.getGesture());
	}
	
}
