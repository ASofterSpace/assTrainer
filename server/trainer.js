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

		if (retry) {
			// if we could not fully resize now, then let's do it later...
			window.setTimeout(function() {
				window.trainer.onResize();
			}, 100);
		}
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
			if (window.trainer.curTimerTime < 0) {
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
	}

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
