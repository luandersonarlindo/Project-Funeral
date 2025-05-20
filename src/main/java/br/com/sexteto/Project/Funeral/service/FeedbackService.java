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

    /**
     * Cria um novo feedback com base no request, associando usuário e funerária.
     *
     * @param request dados do feedback
     * @return feedback criado
     */
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

    /**
     * Retorna todos os feedbacks cadastrados.
     *
     * @return lista de feedbacks
     */
    public List<FeedbackModel> findAll() {
        return feedbackRepository.findAll();
    }

    /**
     * Busca um feedback pelo ID ou lança exceção se não encontrado.
     *
     * @param id identificador do feedback
     * @return feedback encontrado
     */
    public FeedbackModel findByIdOrThrow(UUID id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback não encontrado"));
    }

    /**
     * Atualiza o comentário e a nota de um feedback existente.
     *
     * @param id identificador do feedback
     * @param request dados atualizados
     * @return feedback atualizado
     */
    public FeedbackModel updateFeedback(UUID id, FeedbackUpdateRequest request) {
        FeedbackModel feedback = findByIdOrThrow(id);
        feedback.setComment(request.comment());
        feedback.setRating(request.rating());

        return feedbackRepository.save(feedback);
    }

    /**
     * Exclui um feedback do banco de dados.
     *
     * @param feedbackModel entidade a ser deletada
     */
    public void delete(FeedbackModel feedbackModel) {
        feedbackRepository.delete(feedbackModel);
    }
}
