package org.usfirst.frc.team4990.robot.lib;

public class MotionProfile {
	private final double distanceToTravel;
	private final double goalVelocity;
	private final double acceleration;
	
	/*
	 * units: milliseconds
	 */
	private final double timeToMaxVelocity;
	private final double timeOfDeccelerationStart;
	
	public MotionProfile(double distanceToTravel, double goalVelocity, double acceleration) {
		this.distanceToTravel = distanceToTravel;
		this.goalVelocity = goalVelocity;
		this.acceleration = acceleration;
		
		this.timeToMaxVelocity = goalVelocity / acceleration;
		this.timeOfDeccelerationStart = distanceToTravel / goalVelocity;
	}
	
	public double getDistanceToTravel() {
		return this.distanceToTravel;
	}
	
	/*
	 * A class for storing a set of motion profile values at a given time
	 */
	public class ProfileValues {
		public final double velocity;
		public final double acceleration;
		
		public ProfileValues(double velocity, double acceleration) {
			this.velocity = velocity;
			this.acceleration = acceleration;
		}
	}
	
	/*
	 * time is in milliseconds
	 */
	public ProfileValues getProfileValuesAt(double time) {
		if (time < this.timeToMaxVelocity) {
			return new ProfileValues(this.acceleration * time, this.acceleration);
		} else if (time >= this.timeToMaxVelocity && time < this.timeOfDeccelerationStart) {
			return new ProfileValues(this.goalVelocity, 0);
		} else {
			double timeSinceDeccelerationStart = time - this.timeOfDeccelerationStart;
			return new ProfileValues(this.goalVelocity - (this.acceleration * timeSinceDeccelerationStart), -this.acceleration);
		}
	}
}
