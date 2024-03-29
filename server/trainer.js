window.trainer = {

	// amount of seconds until timer starts
	curTimerPreTime: null,

	// amount of seconds left on timer
	curTimerTime: null,

	// the date time at which the training started
	trainingStartTime: null,


	onResize: function() {

		var retry = false;

		var body = document.getElementById("body");
		if (body) {
			body.style.height = window.innerHeight + "px";
		} else {
			retry = true;
		}

		var mainContent = document.getElementById("mainContent");
		if (mainContent) {
			mainContent.style.height = (window.innerHeight - 31) + "px";
		} else {
			retry = true;
		}

		var tabList = document.getElementById("tabList");
		var avatar = document.getElementById("zaraAvatar");
		if (tabList && avatar) {
			var topPx = avatar.clientHeight + 25;
			tabList.style.top = topPx + "px";
			tabList.style.height = (window.innerHeight - (topPx + 25)) + "px";
			if (avatar.clientHeight < 1) {
				retry = true;
			}
		} else {
			retry = true;
		}

		if (retry) {
			// if we could not fully resize now, then let's do it later...
			window.setTimeout(function() {
				window.trainer.onResize();
			}, 100);
		}
	},

	leaveOut: function(whatToLeaveOut) {
		window.location.href = "?leaveout=" + whatToLeaveOut;
	},

	startTimer: function() {
		this.curTimerPreTime = 5;
		// accept o and O as 0 in the time field
		var timerTimeVal = document.getElementById("timerTimeField").value;
		timerTimeVal = timerTimeVal.split("o").join("0");
		timerTimeVal = timerTimeVal.split("O").join("0");
		this.curTimerTime = parseInt(timerTimeVal) + 1;
		document.getElementById("timerTime").innerHTML = "Timer starts in " + this.curTimerPreTime + " seconds";
		document.getElementById("timerBG").style.display = "block";
		document.getElementById("timerTime").style.display = "block";
		this.ongoingTimer = window.setInterval(function() {
			if (window.trainer.curTimerPreTime > 1) {
				window.trainer.curTimerPreTime -= 1;
				document.getElementById("timerTime").innerHTML =
					"Timer starts in " + window.trainer.curTimerPreTime + " second" +
					(window.trainer.curTimerPreTime == 1 ? "" : "s")
				return;
			}
			window.trainer.curTimerTime -= 1;
			// show the done message for two seconds
			if (window.trainer.curTimerTime < -1) {
				window.trainer.stopTimer();
			} else if (window.trainer.curTimerTime < 1) {
				document.getElementById("timerTime").innerHTML = "Done!";
			} else if (window.trainer.curTimerTime < 2) {
				document.getElementById("timerTime").innerHTML = "1 second";
			} else {
				document.getElementById("timerTime").innerHTML = window.trainer.curTimerTime + " seconds";
			}
		}, 1000);
	},

	stopTimer: function() {
		document.getElementById("timerBG").style.display = "none";
		document.getElementById("timerTime").style.display = "none";
		window.clearInterval(this.ongoingTimer);
	},

	startTraining: function() {
		this.trainingStartTime = toolbox.utils.DateUtils.now();
	},

	lineClick: function(lineId) {

		if (this.trainingStartTime == null) {
			this.startTraining();
		}

		var line = document.getElementById("line-" + lineId);
		var repeatNum = document.getElementById("line-" + lineId + "-repeat-num");
		if (line) {
			switch (line.className) {
				case "line done0of3":
					line.className = "line done1of3";
					repeatNum.innerHTML = "(1/3)";
					break;
				case "line done1of3":
					line.className = "line done2of3";
					repeatNum.innerHTML = "(2/3)";
					break;
				case "line done2of3":
					line.className = "line done3of3";
					repeatNum.innerHTML = "(3/3)";
					break;
				case "line done3of3":
					line.className = "line done0of3";
					repeatNum.innerHTML = "(0/3)";
					break;
				case "line done0of2":
					line.className = "line done1of2";
					repeatNum.innerHTML = "(1/2)";
					break;
				case "line done1of2":
					line.className = "line done2of2";
					repeatNum.innerHTML = "(2/2)";
					break;
				case "line done2of2":
					line.className = "line done0of2";
					repeatNum.innerHTML = "(0/2)";
					break;
				case "line done0of1":
					line.className = "line done1of1";
					repeatNum.innerHTML = "(1/1)";
					break;
				case "line done1of1":
					line.className = "line done0of1";
					repeatNum.innerHTML = "(0/1)";
					break;
				default:
					line.className = "line";
					repeatNum.innerHTML = "(?/?)";
					break;
			}
		}
	},

	lineRightClick: function(lineId) {
		var line = document.getElementById("line-" + lineId);
		var repeatNum = document.getElementById("line-" + lineId + "-repeat-num");
		if (line) {
			switch (line.className) {
				case "line done0of3":
					line.className = "line done3of3";
					repeatNum.innerHTML = "(3/3)";
					break;
				case "line done1of3":
					line.className = "line done0of3";
					repeatNum.innerHTML = "(0/3)";
					break;
				case "line done2of3":
					line.className = "line done1of3";
					repeatNum.innerHTML = "(1/3)";
					break;
				case "line done3of3":
					line.className = "line done2of3";
					repeatNum.innerHTML = "(2/3)";
					break;
				case "line done0of2":
					line.className = "line done2of2";
					repeatNum.innerHTML = "(2/2)";
					break;
				case "line done1of2":
					line.className = "line done0of2";
					repeatNum.innerHTML = "(0/2)";
					break;
				case "line done2of2":
					line.className = "line done1of2";
					repeatNum.innerHTML = "(1/2)";
					break;
				case "line done0of1":
					line.className = "line done1of1";
					repeatNum.innerHTML = "(1/1)";
					break;
				case "line done1of1":
					line.className = "line done0of1";
					repeatNum.innerHTML = "(0/1)";
					break;
				default:
					line.className = "line";
					repeatNum.innerHTML = "(?/?)";
					break;
			}
		}
		return false;
	},

	loadQuestion: function(data) {
		var questionDiv = document.getElementById("question_div");
		var answerDiv = document.getElementById("answer_div");
		answerDiv.style.display = "none";
		questionDiv.innerHTML = data.questionHtml;
		answerDiv.innerHTML = data.answerHtml;
		window.curQuestion = data.questionId;
		document.getElementById('timeSinceAnswer_span').innerHTML = ""+data.timeSinceAnswer;
	},

	showAnswer: function(questionId) {

		// if no questionId is given, just use the one main answer div
		var answerDiv = document.getElementById("answer_div");

		// if a questionId is given, show that particular answer
		if (questionId) {
			answerDiv = document.getElementById("answer_div_" + questionId);
		}

		// show or hide
		if (answerDiv.style.display == "block") {
			answerDiv.style.display = "none";
		} else {
			answerDiv.style.display = "block";
		}
	},

	answered: function(answeredHowWell) {

		var request = new XMLHttpRequest();
		request.open("POST", "answered", true);
		request.setRequestHeader("Content-Type", "application/json");

		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				var result = JSON.parse(request.response);
				if (result.success) {
					window.trainer.loadQuestion(result);
				}
			}
		}

		var data = {
			id: window.curQuestion,
			answeredHowWell: answeredHowWell,
		};

		request.send(JSON.stringify(data));
	},

	randomFact: function(answeredHowWell) {

		var request = new XMLHttpRequest();
		request.open("POST", "randomFact", true);
		request.setRequestHeader("Content-Type", "application/json");

		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				var result = JSON.parse(request.response);
				if (result.success) {
					window.trainer.loadQuestion(result);
				}
			}
		}

		var data = {
		};

		request.send(JSON.stringify(data));
	},

	restartSession: function() {

		var request = new XMLHttpRequest();
		request.open("POST", "restartSession", true);
		request.setRequestHeader("Content-Type", "application/json");

		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				var result = JSON.parse(request.response);
				if (result.success) {
					window.trainer.loadQuestion(result);
				}
			}
		}

		var data = {
		};

		request.send(JSON.stringify(data));
	},

	reloadDatabase: function() {

		var request = new XMLHttpRequest();
		request.open("POST", "reloadDatabase", true);
		request.setRequestHeader("Content-Type", "application/json");

		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				var result = JSON.parse(request.response);
				if (result.success) {
					window.trainer.loadQuestion(result);
				}
			}
		}

		var data = {
		};

		request.send(JSON.stringify(data));
	},

}



window.addEventListener("resize", window.trainer.onResize);


// every half second, update the time since start of the training
window.setInterval(function() {
	var timeSinceTrainingStartLabel = document.getElementById("timeSinceTrainingStartLabel");
	if (toolbox && timeSinceTrainingStartLabel && window.trainer.trainingStartTime) {
		var DateUtils = toolbox.utils.DateUtils;
		if (DateUtils) {
			var now = DateUtils.now();
			var diff = now.getTime() - window.trainer.trainingStartTime.getTime();
			var diffDate = new Date(diff);
			var hrs = diffDate.getUTCHours();
			var min = diffDate.getUTCMinutes();
			var sec = diffDate.getUTCSeconds();

			var html = "Training started ";
			if (hrs > 0) {
				html += hrs + " hour" + (hrs == 1 ? "" : "s") + ", ";
			}
			if (min > 0) {
				html += min + " minute" + (min == 1 ? "" : "s") + ", ";
			}
			html += sec + " second" + (sec == 1 ? "" : "s") + " ago...";

			timeSinceTrainingStartLabel.innerHTML = html;
		}
	}
}, 500);


window.trainer.onResize();
