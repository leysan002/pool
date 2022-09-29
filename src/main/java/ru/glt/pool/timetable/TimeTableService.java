package ru.glt.pool.timetable;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.glt.pool.PoolApp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static ru.glt.pool.timetable.Constants.*;

@Repository
public class TimeTableService {

    public static final String DATETIME = "datetime";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TimeTableRepository timeTableRepository;

    public List<ReservedTimeInfo> getAll(Date date) {
        date = DateUtils.truncate(date, Calendar.HOUR);
        Date toDate = DateUtils.addDays(date, 1);
        return fetchReservedTime(date, toDate);
    }

    public List<ReservedTimeInfo> getAvailable(Date date) {
        date = DateUtils.truncate(date, Calendar.HOUR);

        Date toDate = DateUtils.addDays(date, 1);

        List<ReservedTimeInfo> reservedTimeInfoList = fetchReservedTime(date, toDate);
        List<ReservedTimeInfo> result = new ArrayList<>();

        Date startDate = DateUtils.setHours(date, START_TIME);
        Date endDate = DateUtils.setHours(date, END_TIME);
        while (startDate.compareTo(endDate) <= 0) {
            Date currentTime = startDate;
            Optional<ReservedTimeInfo> timeInfo = reservedTimeInfoList.stream().filter(item -> {
                return currentTime.equals(item.getTime());
            }).findFirst();
            result.add(new ReservedTimeInfo(currentTime, timeInfo.isPresent() ? MAX_TIME_ORDERS - timeInfo.get().getCount() : MAX_TIME_ORDERS));
            startDate = DateUtils.addHours(startDate, 1);
        }
        return result;
    }

    public TimeTable reserve(TimeTable timeTable) throws Exception {
        timeTable.setOrderId(null);
        timeTable.setDatetime(DateUtils.truncate(timeTable.getDatetime(), Calendar.MINUTE));

        Date currentTime = Date.from(LocalDateTime.now(PoolApp.getSystemTimeZone().toZoneId()).toInstant(ZoneOffset.UTC));
        if (timeTable.getDatetime().compareTo(currentTime) < 0) {
            throw new Exception("Only future time only is available for reservation");
        }

        if ( DateUtils.getFragmentInHours(timeTable.getDatetime(), Calendar.DATE) < START_TIME ||
                DateUtils.getFragmentInHours(timeTable.getDatetime(), Calendar.DATE) > END_TIME) {
            throw new Exception("Ordered time should be within 09:00 - 20:00");
        }

        Date toDate = DateUtils.addHours(timeTable.getDatetime(), 1);
        Optional<ReservedTimeInfo> reservedTimeInfoList = fetchReservedTime(timeTable.getDatetime(), toDate).stream().findFirst();
        if (reservedTimeInfoList.isPresent() && reservedTimeInfoList.get().getCount() >= MAX_TIME_ORDERS) {
            throw new Exception("This time is not available to order");
        }

       /* Date fromDate = DateUtils.truncate(timeTable.getDatetime(), Calendar.HOUR);
        toDate = DateUtils.addDays(fromDate, 1);
        reservedTimeInfoList = fetchReservedTime(fromDate, toDate, timeTable.getClientId()).stream().findFirst();
        if (reservedTimeInfoList.isPresent()) {
            throw new Exception("The client has already reserved time at this day");
        }*/

        return timeTableRepository.save(timeTable);
    }


    public void cancel(TimeTable timeTable) throws Exception {
        /*Optional<TimeTable> item = timeTableRepository.findById(timeTable.getOrderId());
        if (item.isPresent()) {
            if (!item.get().getClientId().equals(timeTable.getClientId())) {
                throw new Exception("Wrong client id");
            }

            timeTableRepository.delete(item.get());
        }*/
    }

    private List<ReservedTimeInfo> fetchReservedTime(Date from, Date to) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReservedTimeInfo> criteriaQuery = criteriaBuilder.createQuery(ReservedTimeInfo.class);
        Root<TimeTable> root = criteriaQuery.from(TimeTable.class);
        criteriaQuery.multiselect(root.get(DATETIME), criteriaBuilder.count(root));
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get(DATETIME), from),
                criteriaBuilder.lessThan(root.get(DATETIME),  to)));
        criteriaQuery.groupBy(root.get(DATETIME));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private List<ReservedTimeInfo> fetchReservedTime(Date from, Date to, int clientId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReservedTimeInfo> criteriaQuery = criteriaBuilder.createQuery(ReservedTimeInfo.class);
        Root<TimeTable> root = criteriaQuery.from(TimeTable.class);
        criteriaQuery.multiselect(root.get(DATETIME), criteriaBuilder.count(root));
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get(DATETIME), from),
                criteriaBuilder.lessThan(root.get(DATETIME),  to),
                criteriaBuilder.equal(root.get("clientId"), clientId)));
        criteriaQuery.groupBy(root.get(DATETIME));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}