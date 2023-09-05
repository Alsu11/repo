package ru.itis.services.impl;

import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.jpa.CardsRepository;
import ru.itis.dao.jpa.OrdersRepository;
import ru.itis.dao.jpa.UsersRepository;
import ru.itis.dto.*;
import ru.itis.dto.enums.State;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.OrdersException;
import ru.itis.exceptions.SignUpException;
import ru.itis.exceptions.UsersException;
import ru.itis.models.CardEntity;
import ru.itis.models.OrderEntity;
import ru.itis.models.UserEntity;
import ru.itis.services.UsersService;
import ru.itis.utils.confirm.EmailUtil;
import ru.itis.utils.mappers.CardsMapper;
import ru.itis.utils.mappers.OrdersMapper;
import ru.itis.utils.mappers.UsersMapper;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final EmailUtil emailUtil;
    private final OrdersMapper ordersMapper;
    private final CardsMapper cardsMapper;
    private final CardsRepository cardsRepository;
    private final OrdersRepository ordersRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto signUp(SignUpForm signUpForm) {

        if (usersRepository.findUserEntityByEmail(signUpForm.getEmail()).isPresent()) {
            throw new SignUpException(Errors.EMAIL_ALREADY_TAKEN);
        }

        UserEntity user = usersMapper.toRequest(signUpForm);
        user.setState(State.NOT_CONFIRMED);
        user.setConfirmCode(UUID.randomUUID().toString());
        user.setHashPassword(passwordEncoder.encode(signUpForm.getPassword()));

        usersRepository.save(user);

        try {
            emailUtil.sendMail(user.getEmail(), "confirm", "confirm_mail",
                    Collections.singletonMap("userEntity", user));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return usersMapper.toResponse(user);
    }

    @Override
    public Set<OrderDto> getOrders(UUID id) {
        Set<OrderEntity> ordersEntity = ordersRepository.findAllByUser(id)
                .orElseThrow(() -> new OrdersException(Errors.ORDERS_DOES_NOT_EXIST));
        Set<OrderDto> ordersDto = new HashSet<>();

        for (OrderEntity orderEntity : ordersEntity) {
            ordersDto.add(ordersMapper.toResponse(orderEntity));
        }
        return ordersDto;
    }

    @Override
    public CardDto addCard(UUID userId, AddCardDto addCardDto) {
        CardEntity cardEntity = cardsMapper.toRequest(addCardDto);
        cardEntity.setUser(getUserFromDb(userId));

        return cardsMapper.toResponse(
                cardsRepository.save(cardEntity));
    }

    private UserEntity getUserFromDb(UUID userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));
    }

}
