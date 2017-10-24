package com.pb.reminderapp.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import com.pb.reminderapp.model.EventDetails;
import com.pb.reminderapp.model.EventInfo;
import com.pb.reminderapp.model.RateRequest;
import com.pb.reminderapp.model.RateResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ro003ag on 10/8/2017.
 */

public class ReminderAppService {


    /**
     * Fetch a list of the next 100 events from the primary calendar.
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    public List<EventDetails> getDataFromApi(com.google.api.services.calendar.Calendar mService) throws IOException {
        //DateTime now = new DateTime(System.currentTimeMillis());
        Date dateTomorrow = new Date();
        Date date15Days = new Date();
        Calendar calTomorrow = Calendar.getInstance();
        calTomorrow.setTime(dateTomorrow);
        calTomorrow.add(Calendar.DATE, 1);
        dateTomorrow = calTomorrow.getTime();
        Calendar cal15Days = Calendar.getInstance();
        cal15Days.setTime(date15Days);
        cal15Days.add(Calendar.DATE, 15);
        date15Days = cal15Days.getTime();

        List<EventDetails> listEventDetails = new ArrayList<>();
        Events events = mService.events().list("primary")
                .setMaxResults(100)
                .setTimeMin( new DateTime(dateTomorrow))
                .setTimeMax(new DateTime(date15Days))
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        for (Event event : items) {
            EventDetails eventDetails = new EventDetails();
            DateTime start = event.getStart().getDateTime();
            if (start == null) {
                start = event.getStart().getDate();
            }
            eventDetails.setEventStartDate(start.toString());
            eventDetails.setEventDescription(event.getDescription());
            eventDetails.setEventTitle(event.getSummary());
            listEventDetails.add(eventDetails);
        }
        return listEventDetails;
    }


    public EventInfo processResponse(RateResponse rateResponse, EventDetails eventDetails, String requiredDeliveryDate ) {
        EventInfo eventInfo = new EventInfo();
        Map<Integer,Double> dayAndRateMap = new HashMap<>();
        eventInfo.setEventTitle(eventDetails.getEventTitle());
        List<EventInfo.ShipmentDetail> shipmentDetails = new ArrayList<>();
        for (RateResponse.Rate rate : rateResponse.getRates()){
            if ((rate.getRateTypeId().equals("RETAIL") || rate.getRateTypeId().equals("CONTRACT_RATES")) && dateCheck(requiredDeliveryDate, rate.getDeliveryCommitment().getEstimatedDeliveryDateTime())) {
                EventInfo.ShipmentDetail shipmentDetail = new EventInfo.ShipmentDetail();
                shipmentDetail.setEstimatedDeliveryDateTime(rate.getDeliveryCommitment().getEstimatedDeliveryDateTime());
                shipmentDetail.setShipmentAmount(rate.getTotalCarrierCharge().toString());
                shipmentDetail.setServiceType(rate.getServiceId());
                shipmentDetail.setPriority(1);
                shipmentDetails.add(shipmentDetail);
                dayAndRateMap.put(Integer.valueOf(rate.getDeliveryCommitment().getMaxEstimatedNumberOfDays()), rate.getTotalCarrierCharge());
            }
        }
        String suggestion = checkforShippingOptions(dayAndRateMap);
        eventInfo.setSuggestion(suggestion);
        eventInfo.setUserDeliveryDateTime(requiredDeliveryDate);
        eventInfo.setShipmentDetails(shipmentDetails);
        eventInfo.setToAddress(getMappedAddress(rateResponse.getToAddress()));
        return eventInfo;
    }

    private String checkforShippingOptions(Map<Integer,Double> dayAndRateMap) {
        StringBuilder sb = new StringBuilder();
            TreeSet<Integer> treeSet = new TreeSet<>(dayAndRateMap.keySet());
            List<Integer> list = new ArrayList(dayAndRateMap.keySet());
            Collections.sort(list, Collections.reverseOrder());
            // For Today's  Recommendation
            if (list.size() > 0) {
                Calendar shipDateToday = Calendar.getInstance();
                Calendar deliveryDate = Calendar.getInstance();
                deliveryDate.add(Calendar.DATE, list.get(0));
                if (dateCheck(deliveryDate.getTime(),shipDateToday.getTime())) {
                    sb.append("If package shipped on " + formatDate(shipDateToday.getTime()) + " it will be delivered by " + formatDate(deliveryDate.getTime()) + " in $" + dayAndRateMap.get(list.get(0)) + "\n");
                } else {
                    sb.append("If package shipped on " + formatDate(deliveryDate.getTime()) + " it will be delivered by " + formatDate(deliveryDate.getTime()) + " in $" + dayAndRateMap.get(list.get(0)) + "\n");
                }
            }
            // For Tomorrow's Recommendation
        if (list.size() > 1) {
            Calendar shipDateTomorrow = Calendar.getInstance();
            shipDateTomorrow.add(Calendar.DATE, 1);
            Calendar deliveryDateIfShipDateTomorrow = Calendar.getInstance();
            deliveryDateIfShipDateTomorrow.add(Calendar.DATE, list.get(1));
            if (dateCheck(deliveryDateIfShipDateTomorrow.getTime(),shipDateTomorrow.getTime())) {
                sb.append("If package shipped on " + formatDate(shipDateTomorrow.getTime()) + " it will be delivered by " + formatDate(deliveryDateIfShipDateTomorrow.getTime()) + " in $" + dayAndRateMap.get(list.get(1)) + "\n");
            } else {
                sb.append("If package shipped on " + formatDate(deliveryDateIfShipDateTomorrow.getTime()) + " it will be delivered by " + formatDate(deliveryDateIfShipDateTomorrow.getTime()) + " in $" + dayAndRateMap.get(list.get(1)) + "\n");

            }
        }
            // For Day after tomorrow's Recommendation
        if (list.size() > 2) {
            Calendar shipDateDayAfterTomorrow = Calendar.getInstance();
            shipDateDayAfterTomorrow.add(Calendar.DATE, 2);
            Calendar deliveryDateIfShipDateDayAfterTomorrow = Calendar.getInstance();
            deliveryDateIfShipDateDayAfterTomorrow.add(Calendar.DATE, list.get(2));
            if (dateCheck(deliveryDateIfShipDateDayAfterTomorrow.getTime(),shipDateDayAfterTomorrow.getTime())) {
                sb.append("If package shipped on " + formatDate(shipDateDayAfterTomorrow.getTime()) + " it will be delivered by " + formatDate(deliveryDateIfShipDateDayAfterTomorrow.getTime()) + " in $" + dayAndRateMap.get(list.get(2)));
            } else {
                sb.append("If package shipped on " + formatDate(deliveryDateIfShipDateDayAfterTomorrow.getTime()) + " it will be delivered by " + formatDate(deliveryDateIfShipDateDayAfterTomorrow.getTime()) + " in $" + dayAndRateMap.get(list.get(2)) + "\n");

            }
        }
        return sb.toString();
    }

    private String formatDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }


    private boolean dateCheck(String requiredDeliveryDate, String estimatedDeliveryDateTime) {
        Date requiredDeliveryDateD = null;
        Date estimatedDeliveryDateTimeD = null;
        try {
            requiredDeliveryDateD = new SimpleDateFormat("MM-dd-yyyy").parse(requiredDeliveryDate);
            estimatedDeliveryDateTimeD = new SimpleDateFormat("yyyy-MM-dd").parse(estimatedDeliveryDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return requiredDeliveryDateD.after(estimatedDeliveryDateTimeD);
    }

    private boolean dateCheck(Date requiredDeliveryDate, Date estimatedDeliveryDateTime) {
        return requiredDeliveryDate.after(estimatedDeliveryDateTime);
    }

    private String getMappedAddress(RateResponse.Address address) {
        StringBuilder addressString = new StringBuilder();
        for (String addressLine : address.getAddressLines()) {
            addressString.append(addressLine);
        }
        addressString.append("," + address.getCityTown() + ", " + address.getPostalCode());

        return addressString.toString();
    }

    public void storeEventInCalendar(com.google.api.services.calendar.Calendar mService, List<String> eventStrings) throws IOException {

        String ToDoDescription = createTodoDescription(eventStrings);
        Event event = new Event()
                .setSummary("Today's TODO list")
                .setDescription(ToDoDescription);
        Date currentDate = new Date();
        currentDate.setTime(System.currentTimeMillis());
        Date currentDateAndOneHour = new Date();
        currentDateAndOneHour.setTime(System.currentTimeMillis() + 3600000);
        DateTime startDateTime = new DateTime(currentDate);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime).setTimeZone("Asia/Kolkata");
        event.setStart(start);

        //DateTime endDateTime = new DateTime("2017-10-13T11:00:00+05:30");
        DateTime endDateTime = new DateTime(currentDateAndOneHour);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime).setTimeZone("Asia/Kolkata");
        event.setEnd(end);

        String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=1"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };

        String calendarId = "primary";
        event = mService.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

    private String createTodoDescription(List<String> eventStrings) {
        StringBuffer sb = new StringBuffer();
        for(String eventString : eventStrings){
            sb.append(eventString).append("\n");
        }
        return sb.toString();
    }
}
