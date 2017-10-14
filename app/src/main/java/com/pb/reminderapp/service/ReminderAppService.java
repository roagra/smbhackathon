package com.pb.reminderapp.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import com.pb.reminderapp.model.EventDetails;
import com.pb.reminderapp.model.EventInfo;
import com.pb.reminderapp.model.RateResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


    public EventInfo processResponse(RateResponse rateResponse, EventDetails eventDetails ) {
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEventTitle(eventDetails.getEventTitle());
        eventInfo.setDaysToShip(rateResponse.getRates().get(0).getDeliveryCommitment().getMaxEstimatedNumberOfDays());
        eventInfo.setShipmentAmount(rateResponse.getRates().get(0).getBaseCharge().toString());
        return eventInfo;
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
