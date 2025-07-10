package org.example.payme.service;

import jakarta.transaction.Transactional;
import org.example.payme.dao.TransactionDao;
import org.example.payme.dao.UserDao;
import org.example.payme.entity.Card;
import org.example.payme.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private CardService cardService;

    @Autowired
    private UserDao userDao;

    @Transactional
    public void makePayment(Long senderCardId, String receiverCardNumber, double amount) {
        Card receiverCard = cardService.findByCardNumber(receiverCardNumber);
        if (receiverCard == null) {
            throw new RuntimeException("Qabul qiluvchi karta topilmadi");
        }

        cardService.decreaseBalance(senderCardId, amount);

        cardService.increaseBalance(receiverCard.getId(), amount);

        Transaction transaction = new Transaction();
        transaction.setSenderId(senderCardId);
        transaction.setReceiverCard(receiverCard);
        transaction.setAmount(amount);
        transaction.setStatus("SUCCESS");
        transaction.setTimestamp(LocalDateTime.now());

        transactionDao.save(transaction);
    }

    public List<Transaction> getUserTransactions(Long userId) {
        return transactionDao.findByUserId(userId);
    }

    public List<Transaction> getByCardId(Long cardId) {
        return transactionDao.findByCardId(cardId);
    }

    public List<Transaction> getByStatus(String status) {
        return transactionDao.findByStatus(status);
    }
}
