package edu.escuelaing.ieti.startapp.business.services.projectservices.impl;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.escuelaing.ieti.startapp.business.exception.CommentServiceException;

import edu.escuelaing.ieti.startapp.business.model.Comment;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.services.projectservices.CommentServices;
import edu.escuelaing.ieti.startapp.business.repositories.CommentRepository;

@Service
public class CommentServicesImpl implements CommentServices{
	private final CommentRepository commentRepository;
	@Autowired
    public CommentServicesImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
	@Override
	public Comment createComment(Comment comment) {
		commentRepository.save(comment);
		return comment;
	}
	
	@Override
	public List<Comment> getCommentsByProjectId(Project idProject) throws CommentServiceException {
		return commentRepository.findByIdProject(idProject)
				.orElseThrow(() -> new CommentServiceException(CommentServiceException.PROJECT_ID_NOT_FOUND_EXCEPTION));
	}
	@Override
	public Comment addComment(Comment comment, Project project) {
		List<Comment> comments = project.getComments();
		comments.add(comment);
		project.setComments(comments);
		commentRepository.save(comment);
		return comment;
	}
	


	
}
