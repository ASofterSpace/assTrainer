/**
 * Unlicensed code created by A Softer Space, 2020
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.assTrainer.web;

import com.asofterspace.assTrainer.Database;
import com.asofterspace.assTrainer.sports.Exercise;
import com.asofterspace.assTrainer.sports.SportsCtrl;
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
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ServerRequestHandler extends WebServerRequestHandler {

	private Database database;

	private Directory serverDir;

	private Random rand;

	private int lineId = 0;


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

		if (location.equals("/exit")) {
			System.exit(0);
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

			String leaveout = arguments.get("leaveout");

			exHtml.append("<div>");
			exHtml.append("<div>For the warmup, how about these:</div>");

			List<Exercise> exercises = SportsCtrl.getWarmupExercises(leaveout);
			addExercisesToHtml(exHtml, exercises, 4);
			exHtml.append("</div>");

			exHtml.append("<div>");
			exHtml.append("<div>And now the fun begins! Carry on with sets of:</div>");

			exercises = SportsCtrl.getMainExercises(leaveout);
			addExercisesToHtml(exHtml, exercises, 8);
			exHtml.append("</div>");

			exHtml.append("<div>");
			exHtml.append("<div>If you have the necessary equipment, you can also replace exercises " +
				"with some of these:</div>");

			exercises = SportsCtrl.getGymExercises(leaveout);
			addExercisesToHtml(exHtml, exercises, 4);
			exHtml.append("</div>");

			indexContent = StrUtils.replaceAll(indexContent, "[[SPORTS_EXERCISE]]", exHtml.toString());

			return new WebServerAnswerInHtml(indexContent);
		}

		return null;
	}

	private void addExercisesToHtml(StringBuilder exHtml, List<Exercise> exercises, int howMany) {

		for (int i = 0; i < howMany; i++) {

			int cur = rand.nextInt(exercises.size());
			Exercise ex = exercises.get(cur);
			lineId++;
			exHtml.append("<div id=\"line-" + lineId + "\" class=\"" + ex.getClassName() + "\" " +
				"onclick=\"trainer.lineClick(" + lineId + ");\" " +
				"oncontextmenu=\"return trainer.lineRightClick(" + lineId + ");\">" +
				"* " + ex.toString() +
				" <span id=\"line-" + lineId + "-repeat-num\">(0/" + ex.getRepeats() + ")</span>" +
				"</div>");
			exercises.remove(cur);

			// if there are no more exercises to pick from... well, then let's pick fewer ones
			// instead of exploding
			if (exercises.size() < 1) {
				return;
			}
		}
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
