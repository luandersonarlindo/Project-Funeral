package br.com.sexteto.Project.Funeral.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sexteto.Project.Funeral.Repository.FuneralHomeRepository;
import br.com.sexteto.Project.Funeral.exception.ConflictException;
import br.com.sexteto.Project.Funeral.exception.NotFoundException;
import br.com.sexteto.Project.Funeral.model.FuneralHomeModel;

@Service
public class FuneralHomeService {

    @Autowired
    private FuneralHomeRepository funeralHomeRepository;

    /**
     * Cria ou atualiza uma funerária, garantindo unicidade de CNPJ e telefone.
     *
     * @param funeralHomeModel Dados da funerária
     * @return Funerária salva no banco
     */
    public FuneralHomeModel createOrUpdate(FuneralHomeModel funeralHomeModel) {
        validateUniqueCnpjAndPhone(funeralHomeModel);
        return funeralHomeRepository.save(funeralHomeModel);
    }

    /**
     * Retorna todas as funerárias cadastradas.
     *
     * @return Lista de funerárias
     */
    public List<FuneralHomeModel> findAll() {
        return funeralHomeRepository.findAll();
    }

    /**
     * Busca uma funerária por ID, retornando Optional vazio se não existir.
     *
     * @param id Identificador da funerária
     * @return Optional<FuneralHomeModel>
     */
    public Optional<FuneralHomeModel> findById(UUID id) {
        return funeralHomeRepository.findById(id);
    }

    /**
     * Busca uma funerária por ID ou lança exceção se não encontrada.
     *
     * @param id Identificador da funerária
     * @return FuneralHomeModel encontrada
     */
    public FuneralHomeModel findByIdOrThrow(UUID id) {
        return funeralHomeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Funerária não encontrada!"));
    }

    /**
     * Remove uma funerária do banco de dados.
     *
     * @param funeralHomeModel Instância a ser removida
     */
    public void delete(FuneralHomeModel funeralHomeModel) {
        funeralHomeRepository.delete(funeralHomeModel);
    }

    /**
     * Valida se o CNPJ e telefone são únicos, considerando atualizações.
     *
     * @param funeralHomeModel Funerária a ser validada
     */
    private void validateUniqueCnpjAndPhone(FuneralHomeModel funeralHomeModel) {
        // Validação de CNPJ
        boolean cnpjExists = funeralHomeRepository.findByCnpj(funeralHomeModel.getCnpj())
                .filter(existing -> !existing.getId().equals(funeralHomeModel.getId()))
                .isPresent();

        if (cnpjExists) {
            throw new ConflictException("CNPJ já em uso.");
        }

        // Validação de telefone
        boolean phoneExists = funeralHomeRepository.findByPhone(funeralHomeModel.getPhone())
                .filter(existing -> !existing.getId().equals(funeralHomeModel.getId()))
                .isPresent();

        if (phoneExists) {
            throw new ConflictException("Telefone já em uso.");
        }
    }
}
