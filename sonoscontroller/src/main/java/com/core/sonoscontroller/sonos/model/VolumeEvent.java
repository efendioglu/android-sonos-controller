package com.core.sonoscontroller.sonos.model;

public class VolumeEvent {

	int master,lf,rf;

	@Override
	public String toString() {
		return "VolumeEvent [master=" + master + ", lf=" + lf + ", rf=" + rf + "]";
	}
}
