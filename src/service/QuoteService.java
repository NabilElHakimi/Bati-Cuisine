package service;

import entity.Quote;
import repository.QuoteRepository;

import java.util.ArrayList;
import java.util.List;

public class QuoteService {
    QuoteRepository quoteRepository = new QuoteRepository();

    public boolean save(Quote quote) {
        return quoteRepository.save(quote);
    }

    public List<Quote> findAll(int project_id) {
        List<Quote> quotes = quoteRepository.findByProjectId(project_id);
        return quotes;
     }

     public boolean updateStatus(Quote quote) {
            return quoteRepository.update(quote);
     }
}
