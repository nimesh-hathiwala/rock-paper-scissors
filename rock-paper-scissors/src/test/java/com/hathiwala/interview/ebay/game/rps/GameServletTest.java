package com.hathiwala.interview.ebay.game.rps;

import static com.hathiwala.interview.ebay.game.rps.Constants.KEY_HUMAN_GESTURE;
import static com.hathiwala.interview.ebay.game.rps.Constants.KEY_HUMAN_PLAYER;
import static com.hathiwala.interview.ebay.game.rps.Constants.KEY_PLAYER1_GESTURE;
import static com.hathiwala.interview.ebay.game.rps.Constants.KEY_PLAYER1_SCORE;
import static com.hathiwala.interview.ebay.game.rps.Constants.KEY_PLAYER2_GESTURE;
import static com.hathiwala.interview.ebay.game.rps.Constants.KEY_PLAYER2_SCORE;
import static com.hathiwala.interview.ebay.game.rps.Constants.KEY_RESULT;
import static com.hathiwala.interview.ebay.game.rps.Constants.KEY_ROBOT1;
import static com.hathiwala.interview.ebay.game.rps.Constants.KEY_ROBOT2;
import static com.hathiwala.interview.ebay.game.rps.Constants.KEY_ROBOT3;
import static com.hathiwala.interview.ebay.game.rps.Constants.PATH_HUMAN_VS_ROBOT;
import static com.hathiwala.interview.ebay.game.rps.Constants.PATH_RESET;
import static com.hathiwala.interview.ebay.game.rps.Constants.PATH_ROBOT_VS_ROBOT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hathiwala.interview.ebay.game.rps.model.Gesture;
import com.hathiwala.interview.ebay.game.rps.model.Result;

public class GameServletTest {

	@InjectMocks
	private GameServlet gameServlet;

	@Mock
	private Game game;
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	private HttpSession session;
	@Mock
	private PrintWriter writer;
	@Mock
	private Player humanPlayer;
	@Mock
	private RobotPlayer robot1;
	@Mock
	private RobotPlayer robot2;
	@Mock
	private RobotPlayer robot3;
	@Captor
	private ArgumentCaptor<JSONObject> captorJson;
	
	@Before
	public void setup() throws IOException{
		gameServlet = new GameServlet();
		MockitoAnnotations.initMocks(this);
		when(request.getSession(true)).thenReturn(session);
		when(request.getServletPath()).thenReturn("/unknown");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter(KEY_HUMAN_GESTURE)).thenReturn(Gesture.PAPER.toString());
	}
	
	@Test
	public void doPost_shouldAddPlayersToSession_whenSessionIsNew() throws ServletException, IOException{
		// Setup
		when(session.isNew()).thenReturn(true);
		// Test
		gameServlet.doPost(request, response);
		// Verify
		verify(session).setAttribute(eq(KEY_HUMAN_PLAYER), any(Player.class));
		verify(session).setAttribute(eq(KEY_ROBOT1), any(RobotPlayer.class));
		verify(session).setAttribute(eq(KEY_ROBOT2), any(RobotPlayer.class));
		verify(session).setAttribute(eq(KEY_ROBOT3), any(RobotPlayer.class));
	}
	
	@Test
	public void doPost_shouldAddPlayersToSession_whenServletPathIsReset() throws ServletException, IOException{
		// Setup
		when(request.getServletPath()).thenReturn(PATH_RESET);
		// Test
		gameServlet.doPost(request, response);
		// Verify
		verify(session).setAttribute(eq(KEY_HUMAN_PLAYER), any(Player.class));
		verify(session).setAttribute(eq(KEY_ROBOT1), any(RobotPlayer.class));
		verify(session).setAttribute(eq(KEY_ROBOT2), any(RobotPlayer.class));
		verify(session).setAttribute(eq(KEY_ROBOT3), any(RobotPlayer.class));
	}
	
	@Test
	public void doPost_shouldNotAddPlayersToSession_whenSessionIsNotNewAndServletPathIsNotReset() throws ServletException, IOException{
		// Test
		gameServlet.doPost(request, response);
		// Verify
		verify(session, never()).setAttribute(eq(KEY_HUMAN_PLAYER), any(Player.class));
		verify(session, never()).setAttribute(eq(KEY_ROBOT1), any(RobotPlayer.class));
		verify(session, never()).setAttribute(eq(KEY_ROBOT2), any(RobotPlayer.class));
		verify(session, never()).setAttribute(eq(KEY_ROBOT3), any(RobotPlayer.class));
	}
	
	@Test
	public void doPost_shouldInvokePlayWithCorrectParameters_whenServletPathIsPlayRobotVsRobot() throws ServletException, IOException{
		// Setup
		when(request.getServletPath()).thenReturn(PATH_ROBOT_VS_ROBOT);
		when(session.getAttribute(KEY_ROBOT1)).thenReturn(robot1);
		when(session.getAttribute(KEY_ROBOT2)).thenReturn(robot2);
		// Test
		gameServlet.doPost(request, response);
		// Verify
		verify(game).play(robot1, robot2);
	}
	
	@Test
	public void doPost_shouldInvokePlayWithCorrectParameters_whenServletPathIsPlayHumanVsRobot() throws ServletException, IOException{
		// Setup
		when(request.getServletPath()).thenReturn(PATH_HUMAN_VS_ROBOT);
		when(session.getAttribute(KEY_HUMAN_PLAYER)).thenReturn(humanPlayer);
		when(session.getAttribute(KEY_ROBOT3)).thenReturn(robot3);
		// Test
		gameServlet.doPost(request, response);
		// Verify
		verify(game).play(humanPlayer, robot3);
	}
	
	@Test
	public void doPost_shouldPopulateResponseWithCorrectData_whenServletPathIsPlayRobotVsRobot() throws ServletException, IOException, JSONException{
		// Setup
		Gesture expectedRobot1Gesture = Gesture.PAPER;
		Gesture expectedRobot2Gesture = Gesture.ROCK;
		int expectedRobot1Score = 5;
		int expectedRobot2Score = 10;
		Result expectedResult = Result.PLAYER1;
		when(request.getServletPath()).thenReturn(PATH_ROBOT_VS_ROBOT);
		when(session.getAttribute(KEY_ROBOT1)).thenReturn(robot1);
		when(session.getAttribute(KEY_ROBOT2)).thenReturn(robot2);
		when(robot1.getGesture()).thenReturn(expectedRobot1Gesture);
		when(robot2.getGesture()).thenReturn(expectedRobot2Gesture);
		when(robot1.getScore()).thenReturn(expectedRobot1Score);
		when(robot2.getScore()).thenReturn(expectedRobot2Score);
		when(game.play(robot1, robot2)).thenReturn(expectedResult);
		
		// Test
		gameServlet.doPost(request, response);
		
		// Verify
		verify(writer).print(captorJson.capture());
		assertEquals("Response is not as expected", expectedRobot1Gesture, captorJson.getValue().get(KEY_PLAYER1_GESTURE));
		assertEquals("Response is not as expected", expectedRobot2Gesture, captorJson.getValue().get(KEY_PLAYER2_GESTURE));
		assertEquals("Response is not as expected", expectedRobot1Score, captorJson.getValue().get(KEY_PLAYER1_SCORE));
		assertEquals("Response is not as expected", expectedRobot2Score, captorJson.getValue().get(KEY_PLAYER2_SCORE));
		assertEquals("Response is not as expected", expectedResult, captorJson.getValue().get(KEY_RESULT));
	}
	
	@Test
	public void doPost_shouldPopulateResponseWithCorrectData_whenServletPathIsPlayHumanVsRobot() throws ServletException, IOException, JSONException{
		// Setup
		Gesture expectedHumanGesture = Gesture.PAPER;
		Gesture expectedRobot3Gesture = Gesture.ROCK;
		int expectedHumanScore = 5;
		int expectedRobot3Score = 10;
		Result expectedResult = Result.PLAYER1;
		when(request.getServletPath()).thenReturn(PATH_HUMAN_VS_ROBOT);
		when(session.getAttribute(KEY_HUMAN_PLAYER)).thenReturn(humanPlayer);
		when(session.getAttribute(KEY_ROBOT3)).thenReturn(robot3);
		when(humanPlayer.getGesture()).thenReturn(expectedHumanGesture);
		when(robot3.getGesture()).thenReturn(expectedRobot3Gesture);
		when(humanPlayer.getScore()).thenReturn(expectedHumanScore);
		when(robot3.getScore()).thenReturn(expectedRobot3Score);
		when(game.play(humanPlayer, robot3)).thenReturn(expectedResult);
		
		// Test
		gameServlet.doPost(request, response);
		
		// Verify
		verify(writer).print(captorJson.capture());
		assertEquals("Response is not as expected", expectedHumanGesture, captorJson.getValue().get(KEY_PLAYER1_GESTURE));
		assertEquals("Response is not as expected", expectedRobot3Gesture, captorJson.getValue().get(KEY_PLAYER2_GESTURE));
		assertEquals("Response is not as expected", expectedHumanScore, captorJson.getValue().get(KEY_PLAYER1_SCORE));
		assertEquals("Response is not as expected", expectedRobot3Score, captorJson.getValue().get(KEY_PLAYER2_SCORE));
		assertEquals("Response is not as expected", expectedResult, captorJson.getValue().get(KEY_RESULT));
	}
	
}
