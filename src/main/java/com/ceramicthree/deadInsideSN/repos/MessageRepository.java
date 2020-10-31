package com.ceramicthree.deadInsideSN.repos;

import com.ceramicthree.deadInsideSN.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}

