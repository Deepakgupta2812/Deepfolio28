package com.portfolio.deepak.repository;

import com.portfolio.deepak.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findTop5ByOrderByCreatedAtDesc();

}