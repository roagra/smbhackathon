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
        DateTime now = new DateTime(System.currentTimeMillis());
        List<EventDetails> listEventDetails = new ArrayList<>();
        Events events = mService.events().list("primary")
                .setMaxResults(100)
                .setTimeMin(now)
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
        Event event = new Event()
                .setSummary("Today's todo list")
                .setDescription(eventStrings.get(0));

        DateTime startDateTime = new DateTime("2017-10-08T09:00:00+05:30");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime).setTimeZone("Asia/Kolkata");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2017-10-08T10:00:00+05:30");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime).setTimeZone("Asia/Kolkata");
        event.setEnd(end);

        String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };

        String calendarId = "primary";
        event = mService.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }
}
