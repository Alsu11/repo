package ru.itis.shelter.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.shelter.api.ShelterController;
import ru.itis.shelter.dto.ShelterDto;
import ru.itis.shelter.services.ShelterService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShelterControllerImpl implements ShelterController {

    private final ShelterService shelterService;

    @Override
    public ResponseEntity<List<ShelterDto>> getAll() {
        return ResponseEntity.ok(shelterService.getAll());
    }
}
