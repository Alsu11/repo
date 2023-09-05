package ru.itis.shelter.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.shelter.api.CatsController;
import ru.itis.shelter.dto.AddCatToShelter;
import ru.itis.shelter.dto.CatDto;
import ru.itis.shelter.dto.CatsPage;
import ru.itis.shelter.dto.UpdateCatDto;
import ru.itis.shelter.services.CatsService;

@RequiredArgsConstructor
@RestController
public class CatsControllerImpl implements CatsController {

    private final CatsService catsService;

    @Override
    public ResponseEntity<CatDto> addToShelter(AddCatToShelter addCatToShelter) {
        return ResponseEntity.ok(catsService.addToShelter(addCatToShelter));
    }


    @Override
    public ResponseEntity<CatsPage> getAll(int page) {
        return ResponseEntity.ok(catsService.getAll(page));
    }

    @Override
    public ResponseEntity goOn(Long catId) {
        catsService.goOn(catId);
        return ResponseEntity.ok().body("The cat have go on");
    }

    @Override
    public ResponseEntity<CatDto> updatePost(UpdateCatDto updateCatDto) {
        return ResponseEntity.ok(catsService.updateCat(updateCatDto));
    }
}
