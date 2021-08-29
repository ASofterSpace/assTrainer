/**
 * Unlicensed code created by A Softer Space, 2021
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.assTrainer.facts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class FactsCtrl {

	private static List<Question> questions = new ArrayList<>();


	/**
	 * Return the next question based on which one has not been answered in a while
	 */
	public static Question getNextQuestion() {

		// sort the questions by priority
		Collections.sort(questions, new Comparator<Question>() {
			public int compare(Question a, Question b) {
				return b.getPriority() - a.getPriority();
			}
		});

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
