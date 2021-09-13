/**
 * Unlicensed code created by A Softer Space, 2021
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.assTrainer.facts;

import com.asofterspace.toolbox.io.HTML;
import com.asofterspace.toolbox.utils.Record;
import com.asofterspace.toolbox.utils.StrUtils;

import java.util.UUID;


public class Question {

	private static final String ANSWER = "answer";
	private static final String ID = "id";
	private static final String PRIORITY = "priority";
	private static final String TIME_SINCE_ANSWER = "timeSinceAnswer";
	private static final String TEXT = "question";
	private static final String TAG = "tag";

	private final static int UUID_LENGTH = UUID.randomUUID().toString().length();

	private String text;

	private String id;

	// a higher number indicates that a card needs to be learned again sooner
	private int priority;
	private int timeSinceAnswer;

	private String tag;

	private boolean answeredNow = false;

	private Answer answer;


	public Question(Record rec) {
		this.text = rec.getString(TEXT);
		this.id = rec.getString(ID);
		if ((this.id == null) || (this.id.length() != UUID_LENGTH)) {
			this.id = ""+UUID.randomUUID();
		}
		this.priority = rec.getInteger(PRIORITY);
		// on every load, increase by one
		this.timeSinceAnswer = rec.getInteger(TIME_SINCE_ANSWER, 0) + 1;
		this.tag = rec.getString(TAG);
		this.answer = new Answer(rec.getString(ANSWER), this);
	}

	public String getText() {
		return text;
	}

	public String getId() {
		return id;
	}

	public Answer getAnswer() {
		return answer;
	}

	public int getPriority() {
		return safeadd(priority, timeSinceAnswer);
	}

	public void answer(String answeredHowWell) {
		answeredNow = true;

		switch (answeredHowWell) {
			case "easy":
				priority = safesub(priority, 300);
				break;
			case "hard":
				priority = safesub(priority, 150);
				break;
			case "close":
				break;
			case "wrong":
				priority = safeadd(priority, 150);
				break;
			case "critfail":
				priority = safeadd(priority, 300);
				break;
		}

		timeSinceAnswer = 0;
	}

	public boolean getAnsweredNow() {
		return answeredNow;
	}

	public void setAnsweredNow(boolean answeredNow) {
		this.answeredNow = answeredNow;
	}

	public Record toRecord() {
		Record result = new Record();
		result.set(TEXT, text);
		result.set(ID, id);
		result.set(PRIORITY, priority);
		result.set(TIME_SINCE_ANSWER, timeSinceAnswer);
		result.set(TAG, tag);
		result.set(ANSWER, answer.getText());
		return result;
	}

	public int getTimeSinceAnswer() {
		return timeSinceAnswer;
	}

	public String toHtml() {
		String result = HTML.escapeHTMLstr(text);
		return StrUtils.replaceAll(result, "&#10;", "<br>");
	}

	// prevent overflows
	private int safeadd(int a, int b) {
		int result = a + b;
		if (result < a) {
			return result;
		}
		return result;
	}
	private int safesub(int a, int b) {
		int result = a - b;
		if (result > a) {
			return result;
		}
		return result;
	}

}
