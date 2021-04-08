package edu.escuelaing.ieti.startapp.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.escuelaing.ieti.startapp.business.exception.ChatServiceException;
import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.Chat;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.services.projectservices.ChatServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.UserServices;
import edu.escuelaing.ieti.startapp.web.requests.ChatRequest;

@RestController
@RequestMapping(value = "api/v1/chat")
public class ChatController {
	private final ChatServices chatServices;
	private final UserServices userServices;

	@Autowired
	public ChatController(ChatServices chatServices, UserServices userServices) {
		this.chatServices = chatServices;
		this.userServices = userServices;
	}

	@PostMapping()
	public ResponseEntity<Object> createChat(@RequestBody ChatRequest chatRequest, BindingResult result) {
		Chat chat = chatRequest.toChat();
		return  new ResponseEntity<>(chatServices.createChat(chat), HttpStatus.CREATED);
	}

	@PostMapping(value = "{idChat}/addMessage/{idUser}")
	public ResponseEntity<Object> addMessage(@RequestBody String message, @PathVariable String idChat,
			@PathVariable String idUser) {
		ResponseEntity<Object> response;

		User user;
		try {
			user = userServices.getUserById(idUser);
			Chat chat = chatServices.findChatById(idChat);
			response = new ResponseEntity<>(chatServices.addMessage(user, chat, message), HttpStatus.ACCEPTED);
		} catch (UserServiceException | ChatServiceException e) {
			Map<String, String> error = new HashMap<>();
			error.put("Error", e.getMessage());
			response = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping(value = "user/{idUser}")
	public ResponseEntity<Object> findAllChatByUser(@PathVariable String idUser) {
		ResponseEntity<Object> response;
		try {
			response = new ResponseEntity<>(chatServices.findAllChatsByUser(userServices.getUserById(idUser)),
					HttpStatus.OK);
		} catch (UserServiceException | ChatServiceException e) {
			Map<String, String> error = new HashMap<>();
			error.put("Error", e.getMessage());
			response = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping(value = "/{idChat}")
	public ResponseEntity<Object> findChatById(@PathVariable String idChat) {
		ResponseEntity<Object> response;
		try {
			response = new ResponseEntity<>(chatServices.findChatById(idChat), HttpStatus.OK);
		} catch (ChatServiceException e) {
			Map<String, String> error = new HashMap<>();
			error.put("Error", e.getMessage());
			response = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping(value = "/{idChat}/messages")
	public ResponseEntity<Object> findAllMessagesById(@PathVariable String idChat) {
		ResponseEntity<Object> response;
		try {
			response = new ResponseEntity<>(chatServices.findAllMessagesByChat(idChat), HttpStatus.OK);
		} catch (ChatServiceException e) {
			Map<String, String> error = new HashMap<>();
			error.put("Error", e.getMessage());
			response = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		return response;
	}

}
