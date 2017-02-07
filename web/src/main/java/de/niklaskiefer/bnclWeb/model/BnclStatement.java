package de.niklaskiefer.bnclWeb.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

public class BnclStatement {
    @Id
    public String id;

    public String statement;

    @CreatedDate
    public Date createdDate;
    @LastModifiedDate
    public Date lastModifiedDate;

    public BnclStatement() {}

    public BnclStatement(String statement) {
        this.statement = statement;
        this.createdDate = new Date();
        this.lastModifiedDate = new Date();
    }

    @Override
    public String toString() {
        return String.format(
                "BnclStatement[id=%s, statement='%s']",
                id, statement);
    }
}
