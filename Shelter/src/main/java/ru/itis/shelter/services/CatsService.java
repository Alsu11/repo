package ru.itis.shelter.services;

import ru.itis.shelter.dto.AddCatToShelter;
import ru.itis.shelter.dto.CatDto;
import ru.itis.shelter.dto.CatsPage;
import ru.itis.shelter.dto.UpdateCatDto;

public interface CatsService {
    CatDto addToShelter(AddCatToShelter addCatToShelter);

    CatsPage getAll(int page);

    void goOn(Long catId);

    CatDto updateCat(UpdateCatDto updateCatDto);

}
