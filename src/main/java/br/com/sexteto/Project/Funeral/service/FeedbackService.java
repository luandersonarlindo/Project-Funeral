package br.com.sexteto.Project.Funeral.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sexteto.Project.Funeral.Repository.FeedbackRepository;
import br.com.sexteto.Project.Funeral.Repository.FuneralHomeRepository;
import br.com.sexteto.Project.Funeral.Repository.UserRepository;
import br.com.sexteto.Project.Funeral.dto.feedback.FeedbackRequest;
import br.com.sexteto.Project.Funeral.dto.feedback.FeedbackUpdateRequest;
import br.com.sexteto.Project.Funeral.exception.NotFoundException;
import br.com.sexteto.Project.Funeral.model.FeedbackModel;
import br.com.sexteto.Project.Funeral.model.FuneralHomeModel;
import br.com.sexteto.Project.Funeral.model.UserModel;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FuneralHomeRepository funeralHomeRepository;

    public FeedbackModel createFeedback(FeedbackRequest request) {
        UserModel user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        FuneralHomeModel funeralHome = funeralHomeRepository.findById(request.funeralHomeId())
                .orElseThrow(() -> new NotFoundException("Funerária não encontrada"));

        FeedbackModel feedback = new FeedbackModel();
        feedback.setComment(request.comment());
        feedback.setRating(request.rating());
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setUser(user);
        feedback.setFuneralHome(funeralHome);

        return feedbackRepository.save(feedback);
    }

    public List<FeedbackModel> findAll() {
        return feedbackRepository.findAll();
    }

    public FeedbackModel findByIdOrThrow(UUID id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback não encontrado"));
    }

    public FeedbackModel updateFeedback(UUID id, FeedbackUpdateRequest request) {
        FeedbackModel feedback = findByIdOrThrow(id);
        feedback.setComment(request.comment());
        feedback.setRating(request.rating());

        return feedbackRepository.save(feedback);
    }

    public void delete(FeedbackModel feedbackModel) {
        feedbackRepository.delete(feedbackModel);
    }
}
