/**
 * Unlicensed code created by A Softer Space, 2021
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.assTrainer.facts;

import com.asofterspace.toolbox.io.HTML;
import com.asofterspace.toolbox.utils.MathUtils;
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
	private static final String WHEN = "when";
	private static final String HUGO = "hugo";

	private final static int UUID_LENGTH = UUID.randomUUID().toString().length();

	private String text;

	private String id;

	// a higher number indicates that a card needs to be learned again sooner
	private int priority;
	private int timeSinceAnswer;

	private String tag;

	private Integer when;

	private Boolean hugo;

	private boolean answeredNow = false;

	private Answer answer;

	private boolean inverted = false;


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
		this.when = rec.getInteger(WHEN);
		this.hugo = rec.getBoolean(HUGO);
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
		return safeadd(priority, 15 * timeSinceAnswer);
	}

	public void answer(String answeredHowWell) {
		answeredNow = true;

		switch (answeredHowWell) {
			case "easy":
				priority = safesub(priority, 300);
				break;
			case "hard":
				priority = safesub(priority, 120);
				break;
			case "close":
				break;
			case "wrong":
				priority = safeadd(priority, 120);
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

	public boolean getHugo() {
		if (hugo == null) {
			hugo = false;
		}
		return hugo;
	}

	public Record toRecord() {
		Record result = new Record();
		result.set(TEXT, text);
		result.set(ID, id);
		result.set(PRIORITY, priority);
		result.set(TIME_SINCE_ANSWER, timeSinceAnswer);
		result.set(TAG, tag);
		result.set(WHEN, when);
		result.set(HUGO, getHugo());
		result.set(ANSWER, answer.getText());
		return result;
	}

	public int getTimeSinceAnswer() {
		return timeSinceAnswer;
	}

	public Integer getWhen() {
		return when;
	}

	public String toHtml(boolean allowInverted) {
		String result = HTML.escapeHTMLstr(getMaybeInvertedText(allowInverted));
		return StrUtils.replaceAll(result, "&#10;", "<br>");
	}

	private String getMaybeInvertedText(boolean allowInverted) {

		this.inverted = false;
		getAnswer().setMaybeInvertedText(null);

		String questext = getText();

		if (questext == null) {
			return "";
		}

		questext = questext.trim();

		if (allowInverted &&
			(questext.startsWith("Who is ") ||
			 questext.startsWith("Who was ") ||
			 questext.startsWith("Wer ist ") ||
			 questext.startsWith("Wer war ")) &&
			questext.endsWith("?")) {

			inverted = MathUtils.randomBoolean();

			if (inverted) {

				// e.g. "Who is Mileva Maric?" to "Mileva Maric"
				questext = questext.substring(questext.indexOf(" ") + 1);
				questext = questext.substring(questext.indexOf(" ") + 1);
				questext = questext.substring(0, questext.length() - 1);

				// e.g. "Mileva Maric"
				String name = questext;

				// e.g. "Mileva Maric" to ["Mileva", "Maric"]
				String[] nameParts = name.split(" ");

				questext = getAnswer().getText();

				for (String namePart : nameParts) {
					if (namePart.equals("de")) {
						continue;
					}

					questext = StrUtils.replaceAll(questext, namePart, "???");

					int pos = questext.indexOf("(née ");
					if (pos >= 0) {
						String before = questext.substring(0, pos + 5);
						String after = questext.substring(pos + 5).trim();
						int posSpace = after.indexOf(" ");
						int posComma = after.indexOf(",");
						if (posSpace >= 0) {
							if (posComma >= 0) {
								if (posSpace < posComma) {
									after = after.substring(posSpace);
								} else {
									after = after.substring(posComma);
								}
							} else {
								after = after.substring(posSpace);
							}
						} else {
							if (posComma >= 0) {
								after = after.substring(posComma);
							}
						}
						questext = before + "???" + after;
					}
				}

				questext = StrUtils.replaceAllRepeatedly(questext, "??? de ???", "???");

				questext = StrUtils.replaceAllRepeatedly(questext, "??? ???", "???");

				questext += "\n\nWho is or was this person?";

				getAnswer().setMaybeInvertedText("This refers to:\n" + name);
			}
		}

		return questext;
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
