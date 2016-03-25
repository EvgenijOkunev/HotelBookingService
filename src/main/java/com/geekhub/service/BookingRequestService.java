package com.geekhub.service;

import com.geekhub.model.BookingRequest;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class BookingRequestService {

    @Autowired
    private SessionFactory sessionFactory;

    public List<BookingRequest> getBookingRequests(Date arrivalDate, Date departureDate) {
        return (List<BookingRequest>) sessionFactory.getCurrentSession()
                .createCriteria(BookingRequest.class)
                .add(Restrictions.ge("departureDate", arrivalDate))
                .add(Restrictions.le("arrivalDate", departureDate))
                .list();
    }
}
