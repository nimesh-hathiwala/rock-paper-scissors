package com.hathiwala.interview.ebay.game.rps.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumIT {

	private static Selenium selenium;
	
	@BeforeClass
	public static void setup() {
		selenium = new DefaultSelenium("localhost", 4444, "*googlechrome", "http://localhost:8080");
		selenium.start();
	}
	
	@AfterClass
	public static void tearDown() {
		selenium.stop();
	}
	
	@Test
	public void test_canPlayRobotVsRobot() throws ServletException, IOException {
		// Setup
		selenium.open("/rock-paper-scissors/");
		
		// Test
	    selenium.click("//input[@value='Play!']");
		
	    // Verify
	    waitForAjaxResponse();
	    String robot1Score = selenium.getText("//span[@id='robot1Score']");
	    String robot2Score = selenium.getText("//span[@id='robot2Score']");
	    
	    if(selenium.isVisible("//span[@id='robot1Wins']")){
	    	assertEquals("Score is not as expected", "1", robot1Score);
	    	assertEquals("Score is not as expected", "0", robot2Score);
	    }else if(selenium.isVisible("//span[@id='robot2Wins']")){
	    	assertEquals("Score is not as expected", "0", robot1Score);
	    	assertEquals("Score is not as expected", "1", robot2Score);
	    }else if(selenium.isVisible("//span[@id='game1Draw']")){
	    	assertEquals("Score is not as expected", "0", robot1Score);
	    	assertEquals("Score is not as expected", "0", robot2Score);
	    }else{
	    	fail();
	    }
	}

	@Test
	public void test_canPlayHumanVsRobot() throws ServletException, IOException {
		// Test
		selenium.click("//img[@id='userPicRock']");

		// Verify
		waitForAjaxResponse();
		String humanScore = selenium.getText("//span[@id='playerScore']");
		String robot3Score = selenium.getText("//span[@id='robot3Score']");
		
		if(selenium.isVisible("//span[@id='userWins']")){
			assertEquals("Score is not as expected", "1", humanScore);
			assertEquals("Score is not as expected", "0", robot3Score);
		}else if(selenium.isVisible("//span[@id='robot3Wins']")){
			assertEquals("Score is not as expected", "0", humanScore);
			assertEquals("Score is not as expected", "1", robot3Score);
		}else if(selenium.isVisible("//span[@id='game2Draw']")){
			assertEquals("Score is not as expected", "0", humanScore);
			assertEquals("Score is not as expected", "0", robot3Score);
	    }else{
	    	fail();
	    }
	}
	
	@Test
	public void test_canPlayDifferentGame() throws ServletException, IOException {
		// Setup
		assertFalse(selenium.isVisible("//span[@id='game1Ready']"));
		assertFalse(selenium.isVisible("//span[@id='game2Ready']"));
		
		// Test
		selenium.click("//input[@value='Reset scores']");
		
		// Verify
		waitForAjaxResponse();
		assertTrue(selenium.isVisible("//span[@id='game1Ready']"));
		assertTrue(selenium.isVisible("//span[@id='game2Ready']"));
	}
	
	private void waitForAjaxResponse() {
		selenium.waitForCondition("selenium.browserbot.getCurrentWindow().$.active==0", "1000");
	}
	
}
