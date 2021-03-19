package edu.escuelaing.ieti.business.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "finances")
public class Finance {
    @Getter @Setter
    private long value;
    @Getter @Setter
    private int investorNumber;
    @Getter @Setter
    private long valuation;
    @Getter @Setter
    private long minimumInvestment;
    @Getter @Setter
    private Date startDate;
    @Getter @Setter
    private Date endDate;

}
