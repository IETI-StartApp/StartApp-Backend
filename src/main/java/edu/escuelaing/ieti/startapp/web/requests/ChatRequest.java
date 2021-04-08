package edu.escuelaing.ieti.startapp.web.requests;

import java.io.Serializable;
import java.util.List;

import edu.escuelaing.ieti.startapp.business.model.Chat;
import edu.escuelaing.ieti.startapp.business.model.Message;
import edu.escuelaing.ieti.startapp.business.model.User;

public class ChatRequest implements Serializable {

	private User investor;
	private User entrepreneur;
	private List<Message> messages;

	public ChatRequest() {

	}

	public ChatRequest(Chat chat) {
		this.investor = chat.getInvestor();
		this.entrepreneur = chat.getEntrepreneur();
		this.messages = chat.getMessages();
	}
	public Chat toChat (){
        return new Chat(investor, entrepreneur, messages);
    }

	public User getInvestor() {
		return investor;
	}

	public void setInvestor(User investor) {
		this.investor = investor;
	}

	public User getEntrepreneur() {
		return entrepreneur;
	}

	public void setEntrepreneur(User entrepreneur) {
		this.entrepreneur = entrepreneur;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	private static final long serialVersionUID = 1L;

}
