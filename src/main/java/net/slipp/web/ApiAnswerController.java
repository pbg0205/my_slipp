package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.Result;
import net.slipp.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    
    @Autowired
    private QuestionRepository questionRepository; 
    
    @Autowired
    private AnswerRepository answerRepository; 
    
    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }
        
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(loginUser, question ,contents);
        question.addAnswer();
        
        return answerRepository.save(answer);
    }
    
    @DeleteMapping("/{id}")
	public Result delete(@PathVariable Long questionId,@PathVariable Long id,HttpSession session) {
					
			if(!HttpSessionUtils.isLoginUser(session)) {
						
						return Result.fail("로그인 해야합니다");
					}
			User loginUser = HttpSessionUtils.getUserFromSession(session);
			Answer answer = answerRepository.findById(id).get();
			
			if(!answer.isSameWriter(loginUser)) {
				return Result.fail("본인 글만  삭제할수있습니다");
			}

			answerRepository.delete(answer);
			Question question = questionRepository.findById(questionId).get();
			question.deleteAnswer();
			
			questionRepository.save(question);
			
			return Result.ok();
			
			
	}  
}
