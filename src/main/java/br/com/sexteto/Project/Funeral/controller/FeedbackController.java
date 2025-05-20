package br.com.sexteto.Project.Funeral.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sexteto.Project.Funeral.dto.feedback.FeedbackRequest;
import br.com.sexteto.Project.Funeral.dto.feedback.FeedbackResponse;
import br.com.sexteto.Project.Funeral.dto.feedback.FeedbackUpdateRequest;
import br.com.sexteto.Project.Funeral.model.FeedbackModel;
import br.com.sexteto.Project.Funeral.service.FeedbackService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid FeedbackRequest feedbackRequest) {
        // Cria um novo feedback a partir do DTO de entrada
        feedbackService.createFeedback(feedbackRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> getAll() {
        // Retorna todos os feedbacks convertidos em DTO de resposta
        var feedbacks = feedbackService.findAll();
        var responseList = feedbacks.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList); // 200 OK com lista de feedbacks
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> getOne(@PathVariable UUID id) {
        // Busca um feedback pelo ID, ou lança exceção se não encontrado
        var feedback = feedbackService.findByIdOrThrow(id);
        return ResponseEntity.ok(toResponse(feedback)); // 200 OK com o feedback
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable UUID id,
            @RequestBody @Valid FeedbackUpdateRequest feedbackRequest) {
        // Busca o feedback existente e atualiza com os novos dados
        var feedback = feedbackService.findByIdOrThrow(id);
        BeanUtils.copyProperties(feedbackRequest, feedback);

        feedbackService.updateFeedback(id, feedbackRequest);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // Remove o feedback se ele existir
        var feedback = feedbackService.findByIdOrThrow(id);
        feedbackService.delete(feedback);

        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Método auxiliar para converter modelo em DTO de resposta
    private FeedbackResponse toResponse(FeedbackModel model) {
        return new FeedbackResponse(
                model.getId(),
                model.getComment(),
                model.getRating(),
                model.getCreatedAt(),
                model.getUser().getId(),
                model.getFuneralHome().getId());
    }
}
