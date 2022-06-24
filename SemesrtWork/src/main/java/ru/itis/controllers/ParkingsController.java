package ru.itis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.CarEntryDto;
import ru.itis.dto.EntryForm;
import ru.itis.services.ParkingsService;
import ru.itis.validation.http.ValidationErrorDto;
import ru.itis.validation.http.ValidationExceptionResponse;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Tag(name = "Парковка", description = "Контроллер, связанный с парковкой")
@RequiredArgsConstructor
@RestController
@RequestMapping("/parking")
public class ParkingsController {

    private final ParkingsService parkingsService;


    @Operation(summary = "Получение количества свободных мест на порковке по определенному аддресу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество свободных мест",
                    content = {
                        @Content(mediaType = "application/json",
                                schema =
                                @Schema(implementation = Integer.class)
                        )
                    }
            )
    })
    @GetMapping("/slots")
    public Integer getSlotsCar(@RequestParam("address") String address) {
        return parkingsService.getSlotsCount(address);
    }


    @Operation(summary = "Парковка машины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Машина припакрована",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = CarEntryDto.class)
                            )
                    }
            )
    })
    @PostMapping( "/park")
    public CarEntryDto parkCar(@Valid @RequestBody EntryForm entryForm) {
        return parkingsService.parkCar(entryForm);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ValidationExceptionResponse.class)
                            )
                    }
            )
    })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationExceptionResponse handleException(MethodArgumentNotValidException exception) {
        int i = 0;
        List<ValidationErrorDto> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {

            String errorMessage = error.getDefaultMessage();
            ValidationErrorDto errorDto = ValidationErrorDto.builder()
                    .message(errorMessage)
                    .build();

            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                errorDto.setField(fieldName);
            } else {
                String objectName = error.getObjectName();
                errorDto.setObject(objectName);
            }
            errors.add(errorDto);
        });
        return ValidationExceptionResponse.builder()
                .errors(errors)
                .build();
    }
}
