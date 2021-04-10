package edu.escuelaing.ieti.startapp.business.services.projectservices;

import java.util.List;

import edu.escuelaing.ieti.startapp.business.exception.CommentServiceException;
import edu.escuelaing.ieti.startapp.business.model.Comment;
import edu.escuelaing.ieti.startapp.business.model.Project;

public interface CommentServices {
	Comment createComment(Comment comment);
	List<Comment> getCommentsByProjectId(Project idProject) throws CommentServiceException;
	Comment addComment(Comment comment, Project project);
	
}
