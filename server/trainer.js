window.trainer = {

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
		this.curTimerTime = parseInt(document.getElementById("timerTimeField").value);
		document.getElementById("timerTime").innerHTML = this.curTimerTime + " seconds";
		document.getElementById("timerBG").style.display = "block";
		document.getElementById("timerTime").style.display = "block";
		this.ongoingTimer = window.setInterval(function() {
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

}



window.addEventListener("resize", window.trainer.onResize);


window.trainer.onResize();
