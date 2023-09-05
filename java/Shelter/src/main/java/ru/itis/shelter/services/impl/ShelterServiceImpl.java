package ru.itis.shelter.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.shelter.dao.ShelterRepository;
import ru.itis.shelter.dto.ShelterDto;
import ru.itis.shelter.services.ShelterService;

import java.util.List;

import static ru.itis.shelter.dto.ShelterDto.from;

@RequiredArgsConstructor
@Service
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository shelterRepository;

    @Override
    public List<ShelterDto> getAll() {
        return from(shelterRepository.findAll());
    }
}
