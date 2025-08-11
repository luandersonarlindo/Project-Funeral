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

    public FuneralHomeModel createOrUpdate(FuneralHomeModel funeralHomeModel) {
        validateUniqueCnpjAndPhone(funeralHomeModel);
        return funeralHomeRepository.save(funeralHomeModel);
    }

    public List<FuneralHomeModel> findAll() {
        return funeralHomeRepository.findAll();
    }

    public Optional<FuneralHomeModel> findById(UUID id) {
        return funeralHomeRepository.findById(id);
    }

    public FuneralHomeModel findByIdOrThrow(UUID id) {
        return funeralHomeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Funerária não encontrada!"));
    }

    public void delete(FuneralHomeModel funeralHomeModel) {
        funeralHomeRepository.delete(funeralHomeModel);
    }

    private void validateUniqueCnpjAndPhone(FuneralHomeModel funeralHomeModel) {
       
        boolean cnpjExists = funeralHomeRepository.findByCnpj(funeralHomeModel.getCnpj())
                .filter(existing -> !existing.getId().equals(funeralHomeModel.getId()))
                .isPresent();

        if (cnpjExists) {
            throw new ConflictException("CNPJ já em uso.");
        }

        boolean phoneExists = funeralHomeRepository.findByPhone(funeralHomeModel.getPhone())
                .filter(existing -> !existing.getId().equals(funeralHomeModel.getId()))
                .isPresent();

        if (phoneExists) {
            throw new ConflictException("Telefone já em uso.");
        }
    }
}
