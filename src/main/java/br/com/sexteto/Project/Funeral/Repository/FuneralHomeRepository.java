package br.com.sexteto.Project.Funeral.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sexteto.Project.Funeral.model.FuneralHomeModel;

/**
 * Repositório para operações com a entidade FuneralHomeModel.
 */
@Repository
public interface FuneralHomeRepository extends JpaRepository<FuneralHomeModel, UUID> {
    Optional<FuneralHomeModel> findByName(String name);
    Optional<FuneralHomeModel> findByCnpj(String cnpj);
    Optional<FuneralHomeModel> findByPhone(String phone);
}
