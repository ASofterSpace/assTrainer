/**
 * Unlicensed code created by A Softer Space, 2021
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.assTrainer.facts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class FactsCtrl {

	private static List<Question> questions = new ArrayList<>();

	private static Random rand = null;


	/**
	 * Return the next question based on which one has not been answered in a while
	 */
	public static Question getNextQuestion() {

		// sort the questions by priority
		sortByPriority();

		// get the one with the highest priority that has not already been answered in this session
		for (int i = 0; i < questions.size(); i++) {
			if (!questions.get(i).getAnsweredNow()) {
				return questions.get(i);
			}
		}

		// all have been answerd? restart from the top!
		restartSession();
		return getNextQuestion();
	}

	/**
	 * Return the any question
	 */
	public static Question getRandomQuestion() {

		if (rand == null) {
			rand = new Random();
		}

		List<Question> unansweredOnes = new ArrayList<>();

		for (int i = 0; i < questions.size(); i++) {
			if (!questions.get(i).getAnsweredNow()) {
				unansweredOnes.add(questions.get(i));
			}
		}

		if (unansweredOnes.size() > 0) {
			return unansweredOnes.get(rand.nextInt(unansweredOnes.size()));
		}

		// all have been answerd? restart from the top!
		restartSession();
		return getRandomQuestion();
	}

	/**
	 * sort the questions by priority
	 */
	public static void sortByPriority() {
		Collections.sort(questions, new Comparator<Question>() {
			public int compare(Question a, Question b) {
				return b.getPriority() - a.getPriority();
			}
		});
	}

	/**
	 * sort the questions by what time they are referring to
	 */
	public static void sortByWhen() {
		Collections.sort(questions, new Comparator<Question>() {
			public int compare(Question a, Question b) {
				if (a.getWhen() == null) {
					if (b.getWhen() == null) {
						return b.getPriority() - a.getPriority();
					} else {
						return 1;
					}
				} else {
					if (b.getWhen() == null) {
						return -1;
					}
				}
				return b.getWhen() - a.getWhen();
			}
		});
	}

	public static Question getQuestionById(String id) {
		for (Question q : questions) {
			if (id.equals(q.getId())) {
				return q;
			}
		}
		return null;
	}

	public static void restartSession() {
		for (Question q : questions) {
			q.setAnsweredNow(false);
		}
	}

	public static List<Question> getQuestions() {
		return questions;
	}

	public static void setQuestions(List<Question> questionsArg) {
		questions = questionsArg;
	}

}
