/**
 * Unlicensed code created by A Softer Space, 2020
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.assTrainer;

import com.asofterspace.assTrainer.facts.FactsCtrl;
import com.asofterspace.assTrainer.facts.Question;
import com.asofterspace.toolbox.io.JSON;
import com.asofterspace.toolbox.io.JsonFile;
import com.asofterspace.toolbox.io.JsonParseException;
import com.asofterspace.toolbox.utils.Record;

import java.util.ArrayList;
import java.util.List;


public class Database {

	private final static String USERNAME = "username";

	private final static String PORT = "port";

	private final static String QUESTIONS = "questions";

	private JsonFile dbFile;

	private JSON root;

	private Integer port;

	private String username;


	public Database() {
		reload();
	}

	public void reload() {

		this.dbFile = new JsonFile("config/database.json");
		this.dbFile.createParentDirectory();
		try {
			this.root = dbFile.getAllContents();
		} catch (JsonParseException e) {
			System.err.println("Oh no!");
			e.printStackTrace(System.err);
			System.exit(1);
		}

		this.port = root.getInteger(PORT);

		this.username = root.getString(USERNAME);

		List<Question> questions = new ArrayList<>();
		for (Record qrec : root.getValues(QUESTIONS)) {
			questions.add(new Question(qrec));
		}
		FactsCtrl.setQuestions(questions);
	}

	public Record getRoot() {
		return root;
	}

	public void save() {

		root.makeObject();

		root.set(PORT, port);

		root.set(USERNAME, username);

		List<Record> questionRecs = new ArrayList<>();
		for (Question q : FactsCtrl.getQuestions()) {
			questionRecs.add(q.toRecord());
		}
		root.set(QUESTIONS, questionRecs);

		dbFile.setAllContents(root);
		dbFile.save();
	}

	public int getPort() {
		if (port == null) {
			return 3014;
		}
		return port;
	}

	public String getUsername() {
		return username;
	}

}
