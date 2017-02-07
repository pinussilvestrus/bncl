package de.niklaskiefer.bnclWeb.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BnclStatementRepository extends MongoRepository<BnclStatement, String> {
}
