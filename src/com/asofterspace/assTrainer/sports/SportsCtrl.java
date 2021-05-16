/**
 * Unlicensed code created by A Softer Space, 2020
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.assTrainer.sports;

import java.util.ArrayList;
import java.util.List;


public class SportsCtrl {

	/**
	 * Return exercises that are useful for warming up
	 */
	public static List<String> getWarmupExercises(String leaveout) {

		List<String> exercises = new ArrayList<>();

		if (!"legs".equals(leaveout)) {
			exercises.add("Duck Walk");
		}

		exercises.add("Hampelmensch");
		exercises.add("Fußkreisen");
		exercises.add("Zehen heranziehen");
		exercises.add("Zombie Walk");
		exercises.add("Arme vor und zurück schwingen");
		exercises.add("Kniekreisen");
		exercises.add("Ausfallschritt, dann die Füße so lassen und den Oberkörper von einer Seite zur anderen drehen, mit den Armen fröhlich mitschwingen, und zurück, und hin und her und so weiter :D");

		return exercises;
	}

	/**
	 * Return the main exercises
	 */
	public static List<String> getMainExercises(String leaveout) {

		List<String> exercises = new ArrayList<>();

		if (!"arms".equals(leaveout)) {
			exercises.add("Liegestütze / Pushups");
			exercises.add("stufenweise Liegestütze / Pushups");
			exercises.add("Inverted Pushups (down however you want, but up nicely)");
			exercises.add("Pike Pushups (so pushups in pike position)");
			exercises.add("Pike to Pushup (from pushup to pike position, back and forth)");
			exercises.add("Middle to Pushup to Middle to Sunseeker to Middle");
			exercises.add("Plank Hold");
			exercises.add("Plank Walk (move Elbows while keeping feet constant)");
			exercises.add("Good Morning");
			exercises.add("Arme ausstrecken und 3 Minuten ausgestreckt lassen (2 Sets, nicht 3)");
		}

		if (!"legs".equals(leaveout)) {
			exercises.add("Jogging around the block");
			exercises.add("Lunges");
			exercises.add("Lunges mit erhöhtem hinteren Fuß");
			exercises.add("Walking Lunges");
			exercises.add("High-knee Run");
			exercises.add("Squat");
			exercises.add("Pistol Squat (squat on one leg only)");
			exercises.add("Strecksprünge");
			exercises.add("Sit down, reach for an object and stand up, sit down again and so on");
		}

		if ((!"arms".equals(leaveout)) && (!"legs".equals(leaveout))) {
			exercises.add("Burpees");
		}

		exercises.add("Reverse Crunch (lie on your back, legs straight, and bring them up, and down, and up, and down...)");
		exercises.add("Sunseeker");
		exercises.add("Single-leg Hip Thrust");
		exercises.add("Glute Bridge");
		exercises.add("Wall Sit");
		exercises.add("Schiffchen (versuchen, in möglichst vielen Winkeln stabil zu sein, und von einem Winkel zum nächsten flüssig hoch und runter zu kommen)");
		exercises.add("Tabletop Crunch (lie on your back, legs as tabletop, and crunch towards the feet)");
		exercises.add("Mountain Climbers");
		exercises.add("Standwaage");
		exercises.add("auf dem Rücken liegen und Fahrrad fahren");

		return exercises;
	}

	/**
	 * Return main exercises that require gym equipment
	 */
	public static List<String> getGymExercises(String leaveout) {

		List<String> exercises = new ArrayList<>();

		if (!"arms".equals(leaveout)) {
			exercises.add("Flat Dumbbell Press");
			exercises.add("Kettlebell Power Snatch");
			exercises.add("Dumbbell Triceps Overhead Extensions");
			exercises.add("Dips");
			exercises.add("Horizontal Pullups: Unter Tisch liegen und hochziehen");
			exercises.add("Scapula Pullups (Dead Hang but performing a pullup motion in the shoulders, up and down, ...)");
			exercises.add("Knee Pullups (Dead Hang but pulling the knees up to the chest and down, up and down, ...)");
			exercises.add("Jemensch an den Fußgelenken anheben und Schubkarre-mäßig rumwackeln");
		}

		if (!"legs".equals(leaveout)) {
			exercises.add("Run Up and Down some Stairs");
			exercises.add("Jemensch Huckepack nehmen und rumrennen");
		}

		return exercises;
	}

}
