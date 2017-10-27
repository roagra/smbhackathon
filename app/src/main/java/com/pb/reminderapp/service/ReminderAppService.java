package com.pb.reminderapp.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import com.pb.reminderapp.model.EventDetails;
import com.pb.reminderapp.model.EventInfo;
import com.pb.reminderapp.model.PostCodeResponse;
import com.pb.reminderapp.model.RateRequest;
import com.pb.reminderapp.model.RateResponse;
import com.pb.reminderapp.utility.GetAPIData;
import com.pb.reminderapp.utility.PreferencesUtils;

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
import java.util.List;
import java.util.Map;
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
        Date date9Days = new Date();
        Calendar calTomorrow = Calendar.getInstance();
        calTomorrow.setTime(dateTomorrow);
        calTomorrow.add(Calendar.DATE, 1);
        dateTomorrow = calTomorrow.getTime();
        Calendar cal9Days = Calendar.getInstance();
        cal9Days.setTime(date9Days);
        cal9Days.add(Calendar.DATE, 15);
        date9Days = cal9Days.getTime();

        List<EventDetails> listEventDetails = new ArrayList<>();
        Events events = mService.events().list("primary")
                .setMaxResults(100)
                .setTimeMin( new DateTime(dateTomorrow))
                .setTimeMax(new DateTime(date9Days))
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        for (Event event : items) {
            if (event.getStatus().equalsIgnoreCase("confirmed")) {
                EventDetails eventDetails = new EventDetails();
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                eventDetails.setEventId(event.getId());
                eventDetails.setEventStartDate(start.toString());
                eventDetails.setEventDescription(event.getDescription());
                eventDetails.setEventTitle(event.getSummary());
                eventDetails.setToAddress(event.getLocation());
                eventDetails.setEventStatus(event.getStatus());
                listEventDetails.add(eventDetails);
            }
        }
        return listEventDetails;
    }


    public void markEventAsDone(String eventId, com.google.api.services.calendar.Calendar mService) throws IOException {
        Event event = mService.events().get("primary", eventId).execute();
        event.setStatus("tentative");
        //String eventColor = event.getColorId();
        mService.events().update("primary", event.getId(), event).execute();
    }


    public EventInfo prepareSuggestion(RateResponse rateResponse, EventDetails eventDetails) throws ParseException {
        Map<String,DeliveryInfo> dayAndRateMap = new HashMap<>();
        EventInfo eventInfo = new EventInfo();
        String requiredDeliveryDate = eventDetails.getEventStartDate();
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = originalFormat.parse(requiredDeliveryDate);
        String formattedRequiredDeliveryDate = targetFormat.format(date);
        eventInfo.setEventTitle(eventDetails.getEventTitle());
        eventInfo.setUserDeliveryDateTime(formattedRequiredDeliveryDate);
        eventInfo.setEventId(eventDetails.getEventId());
        eventInfo.setToAddress(eventDetails.getToAddress());
        List<EventInfo.ShippingOption> shippingOptions = new ArrayList<>();
        for (RateResponse.Rate rate : rateResponse.getRates()){
            if ((rate.getRateTypeId().equals("RETAIL") || rate.getRateTypeId().equals("CONTRACT_RATES") || rate.getRateTypeId().equals("COMMERCIAL_BASE"))){
                if (rate.getServiceId().equals("FCM") || rate.getServiceId().equals("PM") || rate.getServiceId().equals("STDPOST")) {
                    DeliveryInfo deliveryInfo = new DeliveryInfo();
                    deliveryInfo.setDays(Integer.valueOf(rate.getDeliveryCommitment().getMaxEstimatedNumberOfDays()));
                    deliveryInfo.setEstimatedDeliveryDate(rate.getDeliveryCommitment().getEstimatedDeliveryDateTime());
                    deliveryInfo.setTotalCarrierCharge(rate.getTotalCarrierCharge());
                    dayAndRateMap.put(rate.getServiceId(), deliveryInfo);
                }
            }
        }

        if (dateCheckMailClass(eventInfo, "STDPOST", formattedRequiredDeliveryDate, dayAndRateMap.get("STDPOST").getEstimatedDeliveryDate())){
            EventInfo.ShippingOption shippingOption = new EventInfo.ShippingOption();
            shippingOption.setMailClass("STDPOST");
            shippingOption.setNote("Standard Post,   Delivery Date : " + dayAndRateMap.get("STDPOST").getEstimatedDeliveryDate()  + ",  Amount : $" + dayAndRateMap.get("STDPOST").getTotalCarrierCharge());
            eventInfo.setStandardShippingOption(shippingOption);
        }

        if ((dateCheckMailClass(eventInfo, "FCM", formattedRequiredDeliveryDate, dayAndRateMap.get("FCM").getEstimatedDeliveryDate())) || null !=  eventInfo.getStandardShippingOption()){
            EventInfo.ShippingOption shippingOption = new EventInfo.ShippingOption();
            shippingOption.setMailClass("FCM");
            shippingOption.setNote("First Class,  Delivery Date : " + dayAndRateMap.get("FCM").getEstimatedDeliveryDate()  + ",  Amount : $" + dayAndRateMap.get("FCM").getTotalCarrierCharge());
            eventInfo.setFmShippingOption(shippingOption);
        }

        if ((dateCheckMailClass(eventInfo, "PM", formattedRequiredDeliveryDate, dayAndRateMap.get("PM").getEstimatedDeliveryDate())) || null !=  eventInfo.getStandardShippingOption()){
            EventInfo.ShippingOption shippingOption = new EventInfo.ShippingOption();
            shippingOption.setMailClass("PM");
            shippingOption.setNote("Priority Mail,  Delivery Date : " + dayAndRateMap.get("PM").getEstimatedDeliveryDate()  + ",  Amount : $" + dayAndRateMap.get("PM").getTotalCarrierCharge());
            eventInfo.setPmShippingOption(shippingOption);
        }
        if (eventInfo.isStandardPostTooLate() && eventInfo.isPriorityMailTooLate() && eventInfo.isFirstClassMailTooLate()){
            eventInfo.setSevere(true);
        }

        if (eventInfo.isStandardPostTooEarly() && eventInfo.isPriorityMailTooEarly() && eventInfo.isFirstClassMailTooEarly()){
            eventInfo.setEarly(true);
        }
        return eventInfo;
    }

    private boolean dateCheckMailClass(EventInfo eventInfo, String mailClass, String requiredDeliveryDate, String estimatedDeliveryDateTime) {
        Date requiredDeliveryDateD = null;
        Date estimatedDeliveryDateTimeD = null;
        try {
            requiredDeliveryDateD = new SimpleDateFormat("yyyy-MM-dd").parse(requiredDeliveryDate);
            estimatedDeliveryDateTimeD = new SimpleDateFormat("yyyy-MM-dd").parse(estimatedDeliveryDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (mailClass.equals("STDPOST")){
            Calendar c = Calendar.getInstance();
            c.setTime(estimatedDeliveryDateTimeD);
            c.add(Calendar.DATE, 1);
            Date estimatedDeliveryDateTimePlusOne = c.getTime();
            if(!requiredDeliveryDateD.before(estimatedDeliveryDateTimeD) && !requiredDeliveryDateD.after(estimatedDeliveryDateTimePlusOne)){
                return true;
            }
            if(requiredDeliveryDateD.after(estimatedDeliveryDateTimePlusOne)){
                eventInfo.setStandardPostTooEarly(true);
            }
            if(requiredDeliveryDateD.before(estimatedDeliveryDateTimeD)){
                eventInfo.setStandardPostTooLate(true);
            }

        }

        if (mailClass.equals("FCM")){
            Calendar c = Calendar.getInstance();
            c.setTime(estimatedDeliveryDateTimeD);
            c.add(Calendar.DATE, 1);
            Date estimatedDeliveryDateTimePlusOne = c.getTime();
            if(!requiredDeliveryDateD.before(estimatedDeliveryDateTimeD) && !requiredDeliveryDateD.after(estimatedDeliveryDateTimePlusOne)){
                    return true;
            }
            if(requiredDeliveryDateD.after(estimatedDeliveryDateTimePlusOne)){
                eventInfo.setFirstClassMailTooEarly(true);
            }
            if(requiredDeliveryDateD.before(estimatedDeliveryDateTimeD)){
                eventInfo.setFirstClassMailTooLate(true);
            }
        }

        if (mailClass.equals("PM")){
            Calendar c = Calendar.getInstance();
            c.setTime(estimatedDeliveryDateTimeD);
            c.add(Calendar.DATE, 1);
            Date estimatedDeliveryDateTimePlusOne = c.getTime();
            if(!requiredDeliveryDateD.before(estimatedDeliveryDateTimeD) && !requiredDeliveryDateD.after(estimatedDeliveryDateTimePlusOne)){
                    return true;
            }
            if(requiredDeliveryDateD.after(estimatedDeliveryDateTimePlusOne)){
                eventInfo.setPriorityMailTooEarly(true);
            }
            if(requiredDeliveryDateD.before(estimatedDeliveryDateTimeD)){
                eventInfo.setPriorityMailTooLate(true);
            }
        }
        return false;
    }



    public EventInfo processResponse(RateResponse rateResponse, EventDetails eventDetails, String requiredDeliveryDate) {
        EventInfo eventInfo = new EventInfo();
        Map<Integer,Double> dayAndRateMap = new HashMap<>();
        eventInfo.setEventTitle(eventDetails.getEventTitle());
        List<EventInfo.ShipmentDetail> shipmentDetails = new ArrayList<>();
        for (RateResponse.Rate rate : rateResponse.getRates()){
            if ((rate.getRateTypeId().equals("RETAIL") || rate.getRateTypeId().equals("CONTRACT_RATES") || rate.getRateTypeId().equals("COMMERCIAL_BASE")) && dateCheck(requiredDeliveryDate, rate.getDeliveryCommitment().getEstimatedDeliveryDateTime())) {
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
            requiredDeliveryDateD = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(requiredDeliveryDate);
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

    public RateRequest prepareRateAndShipmentRequest(String toAddressFromEvent , String serviceId) {
        RateRequest rateRequest = new RateRequest();
        PostCodeResponse toAddress;
        PostCodeResponse fromAddress;
        toAddress = PreferencesUtils.getPostCodeResponse(toAddressFromEvent);
        fromAddress = PreferencesUtils.getPostCodeResponse(PreferencesUtils.getAddress());

        if(null == toAddress) {
            toAddress = GetAPIData.getPostCode(toAddressFromEvent);
            PreferencesUtils.savePostCodeResponse(toAddressFromEvent,toAddress);
        }

        if(null == fromAddress){
            fromAddress = GetAPIData.getPostCode(PreferencesUtils.getAddress());
            PreferencesUtils.savePostCodeResponse(PreferencesUtils.getAddress(),fromAddress);
        }

        // Setting From Address
        RateRequest.Address fromAddressObj = new RateRequest.Address();
        List<String> fromAddressLines = new ArrayList<>();
        fromAddressLines.add(fromAddress.getMainAddressLine());
        //fromAddressLines.add(fromAddress.getAddressLastLine());
        fromAddressObj.setCompany("Pitney Bowes Inc.");
        fromAddressObj.setName("sender_fname");
        fromAddressObj.setPhone("2032032033");
        fromAddressObj.setEmail("sender@email.com");
        fromAddressObj.setResidential(true);
        fromAddressObj.setCityTown(fromAddress.getAreaName3());
        fromAddressObj.setStateProvince(fromAddress.getAreaName1());
        fromAddressObj.setPostalCode(fromAddress.getPostCode1());
        fromAddressObj.setCountryCode("US");
        fromAddressObj.setAddressLines(fromAddressLines);

        // Setting To Address
        RateRequest.Address toAddressObj = new RateRequest.Address();
        List<String> toAddressLines = new ArrayList<>();
        toAddressLines.add(toAddress.getMainAddressLine());
        //toAddressLines.add(toAddress.getAddressLastLine());
        toAddressObj.setCompany("XYZ Inc.");
        toAddressObj.setName("receiver_fname");
        toAddressObj.setPhone("7777777777");
        toAddressObj.setEmail("receiver@email.com");
        toAddressObj.setResidential(true);
        toAddressObj.setCityTown(toAddress.getAreaName3());
        toAddressObj.setStateProvince(toAddress.getAreaName1());
        toAddressObj.setPostalCode(toAddress.getPostCode1());
        toAddressObj.setCountryCode("US");
        toAddressObj.setAddressLines(toAddressLines);

        // Set parcel Details
        RateRequest.Parcel parcel = new RateRequest.Parcel();
        RateRequest.Parcel.Weight weight = new RateRequest.Parcel.Weight();
        weight.setUnitOfMeasurement("OZ");
        weight.setWeight(1);
        RateRequest.Parcel.Dimension dimension = new RateRequest.Parcel.Dimension();
        dimension.setUnitOfMeasurement("IN");
        dimension.setLength(6.0);
        dimension.setWidth(0.25);
        dimension.setHeight(4.0);
        dimension.setIrregularParcelGirth("0.002");
        parcel.setWeight(weight);
        parcel.setDimension(dimension);

        // Set rate details
        List<RateRequest.Rate> rateList = new ArrayList<>();
        RateRequest.Rate rate = new RateRequest.Rate();
        rate.setCarrier("usps");
        rate.setParcelType("PKG");
        rate.setInductionPostalCode(fromAddress.getPostCode1());
        rateList.add(rate);

        RateRequest.Document document = new RateRequest.Document();
        RateRequest.ShipmentOption shipmentOption = new RateRequest.ShipmentOption();
        RateRequest.Rate.SpecialServices specialServices = new RateRequest.Rate.SpecialServices();
        RateRequest.Rate.SpecialServices.InputParameters inputParameters = new RateRequest.Rate.SpecialServices.InputParameters();
        List<RateRequest.Rate.SpecialServices.InputParameters> inputParametersList = new ArrayList<>();
        List<RateRequest.Rate.SpecialServices> specialServicesList = new ArrayList();
        List<RateRequest.Document> documents = new ArrayList<>();
        List<RateRequest.ShipmentOption> shipmentOptions = new ArrayList<>();

        if(!serviceId.isEmpty()){

            document.setType("SHIPPING_LABEL");
            document.setContentType("URL");
            document.setSize("DOC_8X11");
            document.setFileFormat("PDF");
            document.setPrintDialogOption("NO_PRINT_DIALOG");
            documents.add(document);

            shipmentOption.setName("SHIPPER_ID");
            shipmentOption.setValue("9021678263");
            shipmentOptions.add(shipmentOption);

            inputParameters.setName("INPUT_VALUE");
            inputParameters.setValue("0");
            inputParametersList.add(inputParameters);

            specialServices.setInputParameters(inputParametersList);
            specialServices.setSpecialServiceId("DelCon");
            specialServicesList.add(specialServices);

            rate.setServiceId(serviceId);
            rate.setSpecialServices(specialServicesList);
        }

        rateRequest.setFromAddress(fromAddressObj);
        rateRequest.setToAddress(toAddressObj);
        rateRequest.setParcel(parcel);
        rateRequest.setRates(rateList);
        rateRequest.setDocuments(documents);
        rateRequest.setShipmentOptions(shipmentOptions);

        return rateRequest;
    }


    public static class DeliveryInfo {
        private Integer days;
        private String estimatedDeliveryDate;
        private Double totalCarrierCharge;

        public Integer getDays() {
            return days;
        }

        public void setDays(Integer days) {
            this.days = days;
        }

        public String getEstimatedDeliveryDate() {
            return estimatedDeliveryDate;
        }

        public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
            this.estimatedDeliveryDate = estimatedDeliveryDate;
        }

        public Double getTotalCarrierCharge() {
            return totalCarrierCharge;
        }

        public void setTotalCarrierCharge(Double totalCarrierCharge) {
            this.totalCarrierCharge = totalCarrierCharge;
        }
    }

    public static class TakeDecision {
        private boolean isTooEarly;

        private boolean isTooLate;

        public boolean isTooEarly() {
            return isTooEarly;
        }

        public void setTooEarly(boolean tooEarly) {
            isTooEarly = tooEarly;
        }

        public boolean isTooLate() {
            return isTooLate;
        }

        public void setTooLate(boolean tooLate) {
            isTooLate = tooLate;
        }
    }


}
