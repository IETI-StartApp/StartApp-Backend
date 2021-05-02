package edu.escuelaing.ieti.startapp.business.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

public class Chat {
	@Id
    @Getter @Setter
	private String id;
	@Getter
	@Setter
	private User investor;
	@Getter
	@Setter
	private User entrepreneur;
	@Getter
	@Setter
	private List<Message> messages;

	public Chat(User investor, User entrepreneur, List<Message> messages) {
		this.investor = investor;
		this.entrepreneur = entrepreneur;
		this.messages = messages;
	}
}
