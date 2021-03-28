package edu.escuelaing.ieti.startapp.business.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "finances")
public class Finance implements Serializable {

    private static final long serialVersionUID = 2L;
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

    public Finance(long value, int investorNumber, long valuation, long minimumInvestment, Date startDate, Date endDate) {
        this.value = value;
        this.investorNumber = investorNumber;
        this.valuation = valuation;
        this.minimumInvestment = minimumInvestment;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
