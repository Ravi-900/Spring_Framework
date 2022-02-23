package com.demo.spring;

public class TrackCoach implements Coach {

	// define a private field for dependency
	public FortuneService fortuneService;
	
	public TrackCoach() {
		
	}
		
	// define a constructor for dependency injection
	public TrackCoach(FortuneService theFortuneServcie){
		fortuneService = theFortuneServcie;
	}
		
	@Override
	public String getDailyWorkout() {
		return "Run a hard 5K";
	}

	@Override
	public String getDailyFortune() {
		return "just Do It: "+fortuneService.getFortune();
	}
	
	// add init method
	public void doMyStartupStuff() {
		System.out.println("TrackCoach: inside method doMyStartupStuff");
	}
	
	// add destroy method
	public void doMyCleanupStuff() {
		System.out.println("TrackCoach: inside method doMyCleanupStuff");
	}
}
