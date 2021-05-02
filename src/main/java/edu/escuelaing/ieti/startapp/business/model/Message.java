package edu.escuelaing.ieti.startapp.business.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Message implements Serializable {
	@Getter
	@Setter
	private String textmessage;
	@Getter
	@Setter
	private User user;
	@Getter
	@Setter
	private String date;

	public Message(String message, User user, String date) {
		this.textmessage = message;
		this.user = user;
		this.date = date;
	}
}
