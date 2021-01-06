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
	public static List<String> getWarmupExercises() {

		List<String> exercises = new ArrayList<>();
		exercises.add("Hampelmensch");
		exercises.add("Fußkreisen");
		exercises.add("Zehen heranziehen");
		exercises.add("Zombie Walk");
		exercises.add("Duck Walk");
		exercises.add("Arme vor und zurück schwingen");
		exercises.add("Kniekreisen");
		return exercises;
	}

	/**
	 * Return the main exercises
	 */
	public static List<String> getMainExercises() {

		List<String> exercises = new ArrayList<>();
		exercises.add("Jogging around the block");
		exercises.add("Lunges");
		exercises.add("Lunges mit erhöhtem hinteren Fuß");
		exercises.add("Walking Lunges");
		exercises.add("Liegestütze / Pushups");
		exercises.add("Inverted Pushups (down however you want, but up nicely)");
		exercises.add("Pike Pushups (so pushups in pike position)");
		exercises.add("Pike to Pushup (from pushup to pike position, back and forth)");
		exercises.add("Middle to Pushup to Middle to Sunseeker to Middle");
		exercises.add("Reverse Crunch");
		exercises.add("Sunseeker");
		exercises.add("Single-leg Hip Thrust");
		exercises.add("Burpees");
		exercises.add("Plank Hold");
		exercises.add("Plank Walk (move Elbows while keeping feet constant)");
		exercises.add("High-knee Run");
		exercises.add("Squat");
		exercises.add("Glute Bridge");
		exercises.add("Wall Sit");
		exercises.add("Good Morning");
		exercises.add("Schiffchen");
		exercises.add("Tabletop Crunch");
		exercises.add("Mountain Climbers");
		exercises.add("Sit down, reach for an object and stand up, sit down again and so on");
		return exercises;
	}

	/**
	 * Return main exercises that require gym equipment
	 */
	public static List<String> getGymExercises() {

		List<String> exercises = new ArrayList<>();
		exercises.add("Flat Dumbbell Press");
		exercises.add("Kettlebell Power Snatch");
		exercises.add("Dumbbell Triceps Overhead Extensions");
		exercises.add("Dips");
		exercises.add("Horizontal Pullups: Unter Tisch liegen und hochziehen");
		exercises.add("Scapula Pullups (Dead Hang but performing a pullup motion in the shoulders, up and down, ...)");
		exercises.add("Knee Pullups (Dead Hang but pulling the knees up to the chest and down, up and down, ...)");
		return exercises;
	}

}
