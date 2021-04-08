package edu.escuelaing.ieti.startapp.business.model;

import lombok.Getter;
import lombok.Setter;

public class Message {
	@Getter
	@Setter
	private String message;
	@Getter
	@Setter
	private User user;
	@Getter
	@Setter
	private String date;

	public Message(String message, User user, String date) {
		this.message = message;
		this.user = user;
		this.date = date;
	}
}
