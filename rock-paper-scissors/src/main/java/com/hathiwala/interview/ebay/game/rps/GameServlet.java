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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.hathiwala.interview.ebay.game.rps.model.Gesture;
import com.hathiwala.interview.ebay.game.rps.model.Result;

public class GameServlet extends HttpServlet {

	private static final long serialVersionUID = 1742644600040520836L;

	private Game game = new Game();

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession(true);
		String servletPath = request.getServletPath();
		if(session.isNew() || servletPath.equals(PATH_RESET)){
			addPlayersToSession(session);
		}
		
		if(servletPath.equals(PATH_ROBOT_VS_ROBOT)){
			json = playRobotVsRobot(session);
		}else if(servletPath.equals(PATH_HUMAN_VS_ROBOT)){
			json = playHumanVsRobot(request, session);
		}
        
		response.getWriter().print(json);
    }

	private void addPlayersToSession(HttpSession session) {
    	Player humanPlayer = new Player();
    	RobotPlayer robot1 = new RobotPlayer();
    	RobotPlayer robot2 = new RobotPlayer();
    	RobotPlayer robot3 = new RobotPlayer();
    	session.setAttribute(KEY_HUMAN_PLAYER, humanPlayer);
    	session.setAttribute(KEY_ROBOT1, robot1);
    	session.setAttribute(KEY_ROBOT2, robot2);
    	session.setAttribute(KEY_ROBOT3, robot3);
	}

	private JSONObject playRobotVsRobot(HttpSession session) {
    	RobotPlayer robot1 = (RobotPlayer) session.getAttribute(KEY_ROBOT1);
		RobotPlayer robot2 = (RobotPlayer) session.getAttribute(KEY_ROBOT2);
		Result result = game.play(robot1, robot2);
		return buildJsonResponse(result, robot1, robot2);
	}

    private JSONObject playHumanVsRobot(HttpServletRequest request, HttpSession session) {
    	RobotPlayer robot = (RobotPlayer) session.getAttribute(KEY_ROBOT3);
    	Player humanPlayer = (Player) session.getAttribute(KEY_HUMAN_PLAYER);
    	String humanGesture = request.getParameter(KEY_HUMAN_GESTURE);
    	humanPlayer.setGesture(Gesture.valueOf(humanGesture));
		Result result = game.play(humanPlayer, robot);
		return buildJsonResponse(result, humanPlayer, robot);
    }
    
	private JSONObject buildJsonResponse(Result result, Player player1, Player player2){
    	JSONObject json = new JSONObject();  
        try {
        	json.put(KEY_RESULT, result);  
        	json.put(KEY_PLAYER1_GESTURE, player1.getGesture());
        	json.put(KEY_PLAYER1_SCORE, player1.getScore());
			json.put(KEY_PLAYER2_GESTURE, player2.getGesture());  
			json.put(KEY_PLAYER2_SCORE, player2.getScore());
		} catch (JSONException e) {
			e.printStackTrace();
		}  
    	return json;
    }
}
