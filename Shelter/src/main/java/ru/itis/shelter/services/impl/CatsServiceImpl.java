package ru.itis.shelter.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.shelter.dao.CatsRepository;
import ru.itis.shelter.dao.ShelterRepository;
import ru.itis.shelter.dto.AddCatToShelter;
import ru.itis.shelter.dto.CatDto;
import ru.itis.shelter.dto.CatsPage;
import ru.itis.shelter.dto.UpdateCatDto;
import ru.itis.shelter.exceptions.CatsException;
import ru.itis.shelter.exceptions.Errors;
import ru.itis.shelter.exceptions.ShelterException;
import ru.itis.shelter.models.CatEntity;
import ru.itis.shelter.models.ShelterEntity;
import ru.itis.shelter.services.CatsService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.itis.shelter.dto.CatDto.from;

@RequiredArgsConstructor
@Service
public class CatsServiceImpl implements CatsService {

    private final CatsRepository catsRepository;
    private final ShelterRepository shelterRepository;

    @Value("${shelter.default-page-size}")
    private int defaultPageSize;

    @Override
    public CatDto addToShelter(AddCatToShelter addCatToShelter) {
        ShelterEntity shelter = shelterRepository.findById(addCatToShelter.getShelterId())
                .orElseThrow(() -> new ShelterException(Errors.NOT_FOUND));

        System.out.println(shelter);

        CatEntity catEntity = CatEntity.builder()
                .name(addCatToShelter.getName())
                .age(addCatToShelter.getAge())
                .health(CatEntity.Health.UNCLEAR)
                .state(CatEntity.State.ON_SHELTER)
                .shelter(shelter)
                .build();

        return from(
                catsRepository
                        .save(catEntity));
    }

    @Override
    public CatsPage getAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<CatEntity> catsPage = catsRepository.findAll(pageRequest);

        List<CatEntity> cats = catsPage.stream()
                .sorted((cat1, cat2) -> (int) (cat1.getAge() - cat2.getAge()))
                .collect(Collectors.toList()
                );

        return CatsPage.builder()
                .cats(from(cats))
                .totalPages(catsPage.getTotalPages())
                .build();
    }

    @Override
    public void goOn(Long catId) {
        catsRepository.deleteById(catId);
    }

    @Override
    public CatDto updateCat(UpdateCatDto updateCatDto) {
        CatEntity catEntity = catsRepository.findById(updateCatDto.getCatId())
                .orElseThrow(() -> new CatsException(Errors.NOT_FOUND));

        catEntity.setHealth(updateCatDto.getHealth());
        catEntity.setState(updateCatDto.getState());

        return from(
                catsRepository
                        .save(catEntity));
    }
}
