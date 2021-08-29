/**
 * Unlicensed code created by A Softer Space, 2021
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.assTrainer.facts;

import com.asofterspace.toolbox.io.HTML;
import com.asofterspace.toolbox.utils.StrUtils;


public class Answer {

	private static final String TEXT = "text";

	private String text;

	private Question question;


	public Answer(String text, Question question) {
		this.text = text;
		this.question = question;
	}

	public String getText() {
		return text;
	}

	public String toHtml() {
		String result = HTML.escapeHTMLstr(text);
		return StrUtils.replaceAll(result, "&#10;", "<br>");
	}

}
