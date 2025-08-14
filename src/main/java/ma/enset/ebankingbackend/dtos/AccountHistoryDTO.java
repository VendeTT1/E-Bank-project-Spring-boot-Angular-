package ma.enset.ebankingbackend.dtos;

import lombok.Data;
import ma.enset.ebankingbackend.enums.OperationType;

import java.util.Date;
import java.util.List;

@Data
public class AccountHistoryDTO {

    private String accountId;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int sizePage;
    private List<AccountOperationDTO> accountOperationDTOList;

}
