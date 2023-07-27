package kz.tele2.crmoda.service.impl.rent;

import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.model.Rent;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.model.onec.Condition;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.repository.ConditionRepository;
import kz.tele2.crmoda.repository.CounterpartyRepository;
import kz.tele2.crmoda.repository.RentRepository;
import kz.tele2.crmoda.repository.UserRepository;
import kz.tele2.crmoda.service.rent.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final UserRepository userRepository;
    private final CounterpartyRepository counterpartyRepository;
    private final ConditionRepository conditionRepository;
    private final RentRepository rentRepository;

    @Override
    public List<ClientRentsResponse> getClientsRents(String username) {
        //Get user first
        User user = userRepository.findByUsername(username);

        //Find counterparty by username
        Counterparty counterparty = counterpartyRepository.findByName(user.getName());

        //Find condition for that counterparty
        List<Condition> conditions = conditionRepository.findAllByCounterpartyIdNeedsToSign(counterparty, LocalDateTime.now().toLocalDate());

        //Accumulate last 6 months
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last6Months = now.minusMonths(6).withDayOfMonth(1);

        //Container for response
        List<ClientRentsResponse> responses = new ArrayList<>();

        //Populate response
        for(Condition c : conditions){
            if (c.getContract_code() == null || c.getContract_code().isEmpty()) { continue; }
            LocalDate dateRangeStart = c.getStart_date().isAfter(last6Months.toLocalDate()) ? c.getStart_date() : last6Months.toLocalDate();
            List<LocalDate> listOf6Months = new ArrayList<>();
            while (dateRangeStart.isBefore(now.toLocalDate().withDayOfMonth(1))) {
                dateRangeStart = dateRangeStart.withDayOfMonth(1);
                listOf6Months.add(dateRangeStart);
                dateRangeStart = dateRangeStart.plusMonths(1);
            }

            List<Rent> signedRents = rentRepository.findAllByCounterparty(counterparty);

            List<Rent> last6MonthRents = signedRents.stream()
                    .filter(x -> x.getContractCode() == c.getContract_code())
                    .filter(x -> x.getStartDate().isAfter(last6Months.toLocalDate()))
                    .collect(Collectors.toList());

            Map<LocalDate, Rent> startDateSignedRentsMap = last6MonthRents.stream().collect(Collectors.toMap(r -> r.getStartDate(), r -> r));

            for (LocalDate l : listOf6Months) {
                if (startDateSignedRentsMap.get(l) == null) {
                    ClientRentsResponse rentsResponse = ClientRentsResponse.builder()
                            .startDate(l)
                            .contractCode(c.getContract_code())
                            .siteId(c.getSite().getId())
                            .totalSum(c.getSum_1())
                            .signed(false)
                            .build();
                    responses.add(rentsResponse);
                }
            }
            for (Rent r : signedRents) {
                ClientRentsResponse rentsResponse = ClientRentsResponse.builder()
                        .id(r.getId())
                        .startDate(r.getStartDate())
                        .contractCode(r.getContractCode())
                        .siteId(r.getSite().getId())
                        .totalSum(r.getTotalSum())
                        .signed(true)
                        .build();
                responses.add(rentsResponse);
            }
        }
        return responses;
    }
}
