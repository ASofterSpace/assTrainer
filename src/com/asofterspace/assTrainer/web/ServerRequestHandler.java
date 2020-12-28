/**
 * Unlicensed code created by A Softer Space, 2020
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.assTrainer.web;

import com.asofterspace.assTrainer.Database;
import com.asofterspace.toolbox.io.Directory;
import com.asofterspace.toolbox.io.File;
import com.asofterspace.toolbox.io.JSON;
import com.asofterspace.toolbox.io.JsonParseException;
import com.asofterspace.toolbox.io.TextFile;
import com.asofterspace.toolbox.utils.StrUtils;
import com.asofterspace.toolbox.virtualEmployees.SideBarCtrl;
import com.asofterspace.toolbox.virtualEmployees.SideBarEntry;
import com.asofterspace.toolbox.virtualEmployees.SideBarEntryForEmployee;
import com.asofterspace.toolbox.web.WebServer;
import com.asofterspace.toolbox.web.WebServerAnswer;
import com.asofterspace.toolbox.web.WebServerAnswerInHtml;
import com.asofterspace.toolbox.web.WebServerAnswerInJson;
import com.asofterspace.toolbox.web.WebServerRequestHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ServerRequestHandler extends WebServerRequestHandler {

	private Database database;

	private Directory serverDir;

	private Random rand;


	public ServerRequestHandler(WebServer server, Socket request, Directory webRoot, Directory serverDir,
		Database database) {

		super(server, request, webRoot);

		this.database = database;

		this.serverDir = serverDir;

		this.rand = new Random();
	}

	@Override
	protected void handlePost(String fileLocation) throws IOException {

		String jsonData = receiveJsonContent();

		if (jsonData == null) {
			respond(400);
			return;
		}

		WebServerAnswer sideBarAnswer = SideBarCtrl.handlePost(fileLocation, jsonData);
		if (sideBarAnswer != null) {
			respond(200, sideBarAnswer);
			return;
		}

		JSON json;
		try {
			json = new JSON(jsonData);
		} catch (JsonParseException e) {
			respond(400);
			return;
		}

		WebServerAnswer answer = new WebServerAnswerInJson("{\"success\": true}");

		try {

			switch (fileLocation) {

				case "/todo":
					answer = new WebServerAnswerInJson(new JSON("{\"success\": maybe}"));
					break;
/*
				case "/addSingleTask":

					Task addedOrEditedTask = addOrEditSingleTask(json);
					if (addedOrEditedTask == null) {
						return;
					}
					answer = new WebServerAnswerInJson(new JSON("{\"success\": true}"));
					break;
*/

				default:
					respond(404);
					return;
			}

		} catch (JsonParseException e) {
			respond(403);
			return;
		}

		respond(200, answer);
	}

	@Override
	protected WebServerAnswer answerGet(String location, Map<String, String> arguments) {

		if (!location.startsWith("/")) {
			location = "/" + location;
		}

/*
		if ("/task".equals(location)) {
			String id = arguments.get("id");
			if (id != null) {
				Task task = taskCtrl.getTaskById(id);
				if (task != null) {
					JSON response = new JSON(Record.emptyObject());
					response.set("success", true);
					response.set("title", task.getTitle());
					return new WebServerAnswerInJson(response);
				}
			}
		}
*/

		if (location.startsWith("/index.htm") || location.startsWith("/index") ||
			location.equals("/") || location.startsWith("/?")) {

			TextFile indexBaseFile = new TextFile(webRoot, "index.htm");
			String indexContent = indexBaseFile.getContent();

			indexContent = StrUtils.replaceAll(indexContent, "[[SIDEBAR]]",
				SideBarCtrl.getSidebarHtmlStr(new SideBarEntryForEmployee("Zara")));

			indexContent = StrUtils.replaceAll(indexContent, "[[USERNAME]]", database.getUsername());

			StringBuilder exHtml = new StringBuilder();

			exHtml.append("<div>");
			exHtml.append("<div>For the warmup, how about these:</div>");

			List<String> exercises = new ArrayList<>();
			exercises.add("Hampelmensch");
			exercises.add("Fußkreisen");
			exercises.add("Zehen heranziehen");
			exercises.add("Zombie Walk");
			exercises.add("Duck Walk");
			exercises.add("Arme vor und zurück schwingen");

			for (int i = 0; i < 4; i++) {
				int cur = rand.nextInt(exercises.size());
				exHtml.append("<div class=\"line\">* " + exercises.get(cur) + "</div>");
				exercises.remove(cur);
			}
			exHtml.append("</div>");

			exHtml.append("<div>");
			exHtml.append("<div>And now the fun begins! Carry on with sets of:</div>");

			exercises = new ArrayList<>();
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

			for (int i = 0; i < 8; i++) {
				int cur = rand.nextInt(exercises.size());
				exHtml.append("<div class=\"line\">* " + exercises.get(cur) + "</div>");
				exercises.remove(cur);
			}
			exHtml.append("</div>");

			exHtml.append("<div>");
			exHtml.append("<div>If you have the necessary equipment, you can also replace exercises " +
				"with some of these:</div>");

			exercises = new ArrayList<>();
			exercises.add("Flat Dumbbell Press");
			exercises.add("Kettlebell Power Snatch");
			exercises.add("Dumbbell Triceps Overhead Extensions");
			exercises.add("Dips");
			exercises.add("Horizontal Pullups: Unter Tisch liegen und hochziehen");
			exercises.add("Scapula Pullups (Dead Hang but performing a pullup motion in the shoulders, up and down, ...)");
			exercises.add("Knee Pullups (Dead Hang but pulling the knees up to the chest and down, up and down, ...)");

			for (int i = 0; i < 4; i++) {
				int cur = rand.nextInt(exercises.size());
				exHtml.append("<div class=\"line\">* " + exercises.get(cur) + "</div>");
				exercises.remove(cur);
			}
			exHtml.append("</div>");

			indexContent = StrUtils.replaceAll(indexContent, "[[SPORTS_EXERCISE]]", exHtml.toString());

			return new WebServerAnswerInHtml(indexContent);
		}

		return null;
	}

	@Override
	protected File getFileFromLocation(String location, String[] arguments) {

		File sideBarImageFile = SideBarCtrl.getSideBarImageFile(location);
		if (sideBarImageFile != null) {
			return sideBarImageFile;
		}

		String locEquiv = getWhitelistedLocationEquivalent(location);

		// if no root is specified, then we are just not serving any files at all
		// and if no location equivalent is found on the whitelist, we are not serving this request
		if ((webRoot != null) && (locEquiv != null)) {

			// serves images and text files directly from the server dir, rather than the deployed dir
			if (locEquiv.toLowerCase().endsWith(".jpg") || locEquiv.toLowerCase().endsWith(".pdf") ||
				locEquiv.toLowerCase().endsWith(".png") || locEquiv.toLowerCase().endsWith(".stp") ||
				locEquiv.toLowerCase().endsWith(".txt") || locEquiv.toLowerCase().endsWith(".stpu") ||
				locEquiv.toLowerCase().endsWith(".json")) {

				File result = new File(serverDir, locEquiv);
				if (result.exists()) {
					return result;
				}
			}

			// actually get the file
			return webRoot.getFile(locEquiv);
		}

		// if the file was not found on the whitelist, do not return it
		// - even if it exists on the server!
		return null;
	}

}
