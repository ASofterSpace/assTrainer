/**
 * Unlicensed code created by A Softer Space, 2021
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.assTrainer.sports;


public class Exercise {

	private String name;

	private int repeats;


	public Exercise(String name, int repeats) {
		this.name = name;
		this.repeats = repeats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRepeats() {
		return repeats;
	}

	public void setRepeats(int repeats) {
		this.repeats = repeats;
	}

	public String getClassName() {
		return "line done0of" + repeats;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object other) {

		// If the other one does not even exist, we are not the same - because we exist!
		if (other == null) {
			return false;
		}

		if (other instanceof Exercise) {
			Exercise otherExercise = (Exercise) other;

			// If our values for name are different...
			if (this.name == null) {
				if (otherExercise.name != null) {
					// ... then we are not the same!
					return false;
				}
			} else if (!this.name.equals(otherExercise.name)) {
				// ... then we are not the same!
				return false;
			}

			// We have no reason to assume that we are not the same
			return true;
		}

		// If the other one cannot even be cast to us, then we are not the same!
		return false;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

}
