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
	public static List<Exercise> getWarmupExercises(String leaveout) {

		List<Exercise> exercises = new ArrayList<>();

		if (!"legs".equals(leaveout)) {
			exercises.add(new Exercise("Duck Walk", 1));
		}

		exercises.add(new Exercise("Hampelmensch", 1));
		exercises.add(new Exercise("Rücken-Raupe an der Wand", 1));
		exercises.add(new Exercise("Fußkreisen", 1));
		exercises.add(new Exercise("Zehen heranziehen", 1));
		exercises.add(new Exercise("Zombie Walk", 1));
		exercises.add(new Exercise("Arme vor und zurück schwingen", 1));
		exercises.add(new Exercise("Kniekreisen", 1));
		exercises.add(new Exercise("Ausfallschritt, dann die Füße so lassen und den Oberkörper von einer Seite zur anderen drehen, mit den Armen fröhlich mitschwingen, und zurück, und hin und her und so weiter :D", 1));

		return exercises;
	}

	/**
	 * Return the main exercises
	 */
	public static List<Exercise> getMainExercises(String leaveout) {

		List<Exercise> exercises = new ArrayList<>();

		if (!"arms".equals(leaveout)) {
			exercises.add(new Exercise("Liegestütze / Pushups", 3));
			exercises.add(new Exercise("stufenweise Liegestütze / Pushups", 3));
			exercises.add(new Exercise("Inverted Pushups (down however you want, but up nicely)", 3));
			exercises.add(new Exercise("Pike Pushups (so pushups in pike position)", 3));
			exercises.add(new Exercise("Pike to Pushup (from pushup to pike position, back and forth)", 3));
			exercises.add(new Exercise("Middle to Pushup to Middle to Sunseeker to Middle", 3));
			exercises.add(new Exercise("Plank Hold", 3));
			exercises.add(new Exercise("Plank Walk (move Elbows while keeping feet constant)", 3));
			exercises.add(new Exercise("Good Morning", 3));
			exercises.add(new Exercise("Arme ausstrecken und 3 Minuten ausgestreckt lassen (einmal mit linker Handfläche oben, einmal mit rechter)", 2));
		}

		if (!"legs".equals(leaveout)) {
			exercises.add(new Exercise("Jogging around the block", 1));
			exercises.add(new Exercise("Lunges", 3));
			exercises.add(new Exercise("Lunges mit erhöhtem hinteren Fuß", 3));
			exercises.add(new Exercise("Walking Lunges", 3));
			exercises.add(new Exercise("High-knee Run", 3));
			exercises.add(new Exercise("Squats", 3));
			exercises.add(new Exercise("Pistol Squats (squats on one leg only)", 3));
			exercises.add(new Exercise("Strecksprünge", 3));
			exercises.add(new Exercise("Sit down, reach for an object and stand up, sit down again and so on", 3));
		}

		if ((!"arms".equals(leaveout)) && (!"legs".equals(leaveout))) {
			exercises.add(new Exercise("Burpees", 3));
			exercises.add(new Exercise("Burpees (aber einen Fuß antippen, dann den anderen, anstatt einen Liegestütz zu machen)", 3));
		}

		exercises.add(new Exercise("Reverse Crunch (lie on your back, legs straight, and bring them up, and down, and up, and down...)", 3));
		exercises.add(new Exercise("Sunseeker", 3));
		exercises.add(new Exercise("Single-leg Hip Thrust", 3));
		exercises.add(new Exercise("Glute Bridge", 3));
		exercises.add(new Exercise("Wall Sit", 3));
		exercises.add(new Exercise("Schiffchen (versuchen, in möglichst vielen Winkeln stabil zu sein, und von einem Winkel zum nächsten flüssig hoch und runter zu kommen)", 2));
		exercises.add(new Exercise("Tabletop Crunch (lie on your back, legs as tabletop, and crunch towards the feet)", 3));
		exercises.add(new Exercise("Mountain Climbers", 3));
		exercises.add(new Exercise("Standwaage", 3));
		exercises.add(new Exercise("auf dem Rücken liegen und Fahrrad fahren", 3));

		return exercises;
	}

	/**
	 * Return main exercises that require special (e.g. gym) equipment or people
	 */
	public static List<Exercise> getGymExercises(String leaveout) {

		List<Exercise> exercises = new ArrayList<>();

		if (!"arms".equals(leaveout)) {
			exercises.add(new Exercise("Flat Dumbbell Press", 3));
			exercises.add(new Exercise("Kettlebell Power Snatch", 1));
			exercises.add(new Exercise("Dumbbell Triceps Overhead Extensions", 3));
			exercises.add(new Exercise("Dips", 3));
			exercises.add(new Exercise("Horizontal Pullups: Unter Tisch liegen und hochziehen", 3));
			exercises.add(new Exercise("Scapula Pullups (Dead Hang but performing a pullup motion in the shoulders, up and down, ...)", 3));
			exercises.add(new Exercise("Knee Pullups (Dead Hang but pulling the knees up to the chest and down, up and down, ...)", 3));
			exercises.add(new Exercise("Jemensch an den Fußgelenken anheben und Schubkarre-mäßig rumwackeln", 1));
			exercises.add(new Exercise("Handstand an der Wand", 3));
		}

		if (!"legs".equals(leaveout)) {
			exercises.add(new Exercise("Run Up and Down some Stairs", 3));
			exercises.add(new Exercise("Jemensch Huckepack nehmen und rumrennen", 1));
		}

		return exercises;
	}

}
