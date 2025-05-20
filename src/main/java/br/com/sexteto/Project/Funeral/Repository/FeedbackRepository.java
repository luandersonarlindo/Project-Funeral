package br.com.sexteto.Project.Funeral.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sexteto.Project.Funeral.model.FeedbackModel;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackModel, UUID> {

    /**
     * Verifica se já existe feedback de um usuário para uma funerária específica.
     */

    boolean existsByFuneralHomeIdAndUserId(UUID funeralHomeId, UUID userId);
}
