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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

        @Autowired
        private FeedbackService feedbackService;

        @Operation(description = "Cria um novo feedback")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Retorna o ..."),
                        @ApiResponse(responseCode = "400", description = "Não existe o valor com id informado") })
        @PostMapping
        public ResponseEntity<Void> save(@RequestBody @Valid FeedbackRequest feedbackRequest) {
                feedbackService.createFeedback(feedbackRequest);
                return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        @Operation(description = "Retorna todos os feedbacks")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o ..."),
                        @ApiResponse(responseCode = "400", description = "Não existe o valor com id informado") })
        @GetMapping
        public ResponseEntity<List<FeedbackResponse>> getAll() {
                var feedbacks = feedbackService.findAll();
                var responseList = feedbacks.stream()
                                .map(this::toResponse)
                                .collect(Collectors.toList());

                return ResponseEntity.ok(responseList);
        }

        @Operation(description = "Retorna um feedback específico")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o ..."),
                        @ApiResponse(responseCode = "400", description = "Não existe o valor com id informado") })
        @GetMapping("/{id}")
        public ResponseEntity<FeedbackResponse> getOne(@PathVariable UUID id) {
                var feedback = feedbackService.findByIdOrThrow(id);
                return ResponseEntity.ok(toResponse(feedback));
        }

        @Operation(description = "Atualiza um feedback existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o ..."),
                        @ApiResponse(responseCode = "400", description = "Não existe o valor com id informado") })
        @PutMapping("/{id}")
        public ResponseEntity<Void> update(
                        @PathVariable UUID id,
                        @RequestBody @Valid FeedbackUpdateRequest feedbackRequest) {
                var feedback = feedbackService.findByIdOrThrow(id);
                BeanUtils.copyProperties(feedbackRequest, feedback);

                feedbackService.updateFeedback(id, feedbackRequest);
                return ResponseEntity.noContent().build();
        }

        @Operation(description = "Remove um feedback existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o ..."),
                        @ApiResponse(responseCode = "400", description = "Não existe o valor com id informado") })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable UUID id) {
                var feedback = feedbackService.findByIdOrThrow(id);
                feedbackService.delete(feedback);

                return ResponseEntity.noContent().build();
        }

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
