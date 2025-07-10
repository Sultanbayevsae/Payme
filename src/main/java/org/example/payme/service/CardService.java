package org.example.payme.service;

import jakarta.transaction.Transactional;
import org.example.payme.dao.CardDao;
import org.example.payme.dao.UserDao;
import org.example.payme.dto.AddCardDTO;
import org.example.payme.dto.CardVerifyDTO;
import org.example.payme.entity.Card;
import org.example.payme.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardDao cardDao;

    @Autowired
    private UserDao userDao;

    public String createCard(AddCardDTO dto) {
        User user = userDao.findById(dto.getUserId());
        if(user == null) {
            return "User not found";
        }
        Card card = Card.builder()
                .cardHolder(dto.getCardHolder())
                .cardNumber(generatePan())
                .password(dto.getPassword())
                .expiryDate(generateExpireDate())
                .balance(0.0)
                .user(user)
                .build();

        cardDao.save(card);
        return "Card added";
    }

    public List<Card> getUserCards(Long userId) {
        return cardDao.findByUserId(userId);
    }

    private String generatePan() {
        String bin = "8600";
        long randomPart = (long)(Math.random() * 1_0000_0000_0000L);
        return bin + String.format("%012d", randomPart).substring(0, 12);
    }
    private String generateExpireDate() {
        LocalDate now = LocalDate.now().plusYears(3);
        return String.format("%02d/%02d", now.getMonthValue(), now.getYear() % 100);
    }

    public void deleteCard(Long cardId) {
        Card card = cardDao.findById(cardId);
        if (card == null) {
            throw new RuntimeException("Karta topilmadi");
        }
        cardDao.delete(card);
    }


    @Transactional
    public void decreaseBalance(Long cardId, double amount) {
        Card card = cardDao.findById(cardId);
        if (card == null) {
            throw new RuntimeException("Karta topilmadi");
        }
        if (card.getBalance() < amount) {
            throw new RuntimeException("Yetarli mablag‘ mavjud emas");
        }
        card.setBalance(card.getBalance() - amount);
        cardDao.update(card);
    }

    @Transactional
    public void increaseBalance(Long cardId, double amount) {
        Card card = cardDao.findById(cardId);
        if (card == null) {
            throw new RuntimeException("Karta topilmadi");
        }
        card.setBalance(card.getBalance() + amount);
        cardDao.update(card);
    }


    public boolean existsByCardNumber(String cardNumber) {
        return cardDao.existsByCardNumber(cardNumber);
    }


    public Card findByCardNumber(String cardNumber) {
        return cardDao.findByNumber(cardNumber);
    }





}
