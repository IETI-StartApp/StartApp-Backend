package edu.escuelaing.ieti.startapp.business.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Document(collection = "finances")
@NoArgsConstructor
public class Finance implements Serializable {

    private static final long serialVersionUID = 2L;
    @Getter @Setter
    private long value;
    @Getter @Setter
    @Min(value = 1,message = "EL proyecto debe tener mínimo un inversionista.")
    private int investorNumber;
    @Getter @Setter
    private long valuation;
    @Getter @Setter
    @Min(value = 100000,message = "La inversión mínima debe ser mayor a 100000")
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
