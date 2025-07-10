package org.example.payme.service;

import jakarta.transaction.Transactional;
import org.example.payme.dao.InvoiceDao;
import org.example.payme.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private CardService cardService;

    @Transactional
    public void payInvoice(Long invoiceId, Long cardId) {
        Invoice invoice = invoiceDao.findById(invoiceId);
        if (invoice == null || invoice.getStatus().equals("PAID")) {
            throw new RuntimeException("Invoice mavjud emas yoki allaqachon to‘langan");
        }

        // Pul yechish
        cardService.decreaseBalance(cardId, invoice.getAmount());

        // Holatni o‘zgartirish
        invoice.setStatus("PAID");
        invoiceDao.save(invoice);
    }

    public List<Invoice> getInvoicesByUser(Long userId) {
        return invoiceDao.findByUserId(userId);
    }

    public List<Invoice> getByStatus(String status) {
        return invoiceDao.findByStatus(status);
    }

    public void createInvoice(Invoice invoice) {
        invoice.setStatus("PENDING");
        invoice.setCreatedAt(LocalDateTime.now());
        invoiceDao.save(invoice);
    }
}