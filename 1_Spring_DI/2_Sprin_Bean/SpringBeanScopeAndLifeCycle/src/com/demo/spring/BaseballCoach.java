package com.demo.spring;

public class BaseballCoach implements Coach{
	
	// define a private field for dependency
	public FortuneService fortuneService;
	
	// define a constructor for dependency injection
	public BaseballCoach(FortuneService theFortuneServcie){
	    fortuneService = theFortuneServcie;
	  }
	
	@Override
	public String getDailyWorkout() {
		return "Spend 30 minutes on batting practice." ;
	}

	@Override
	public String getDailyFortune() {
	// use my fortuneService to get a fortune
		return fortuneService.getFortune();
	}
}
   