package com.melogtm.wikinhistory.controller;

import com.melogtm.wikinhistory.model.Event;
import com.melogtm.wikinhistory.services.EventService;
import com.melogtm.wikinhistory.services.ImageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;


@Controller
public class EventController {

    EventService eventService;
    ImageService imageService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    private static Integer dayOfToday = LocalDate.now().getDayOfMonth();
    private static Integer monthOfToday = LocalDate.now().getMonthValue();
    private static String defaultEventType = "events";
    private static String defaultUrlEvents = EventService.buildEventUrl(dayOfToday,
            monthOfToday, defaultEventType);

    public static boolean isDateValid(String date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            dateFormat.setLenient(false);

            dateFormat.parse(date);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @GetMapping("/")
    public String getRootRoute(Model model) {

        JSONObject chosenEvent = eventService.getRandomEvent(eventService.getEvents
                (defaultUrlEvents, defaultEventType));


        String eventDate = eventService.getChooseDateApiCall(defaultUrlEvents);
        String eventYear = eventService.getYearOfEvent(chosenEvent);
        String eventImgURL = eventService.buildImageUrl(eventService.getEventTitle(chosenEvent));
        String eventDescription = eventService.getEventDescription(chosenEvent);

        Event myEvent = new Event(eventDate, eventYear, eventImgURL, eventDescription,
                defaultEventType.substring(0, 1).toUpperCase() +
                        defaultEventType.substring(1));

        myEvent.setEventUrlImage(imageService.getImage(myEvent.getEventUrlImage()));

        model.addAttribute("event", myEvent);

        return "index";
    }

    @PostMapping("/")
    public String userDate(@RequestParam("dayUser") Integer day, @RequestParam("monthUser") Integer month,
                           @RequestParam("eventType") String eventType ) {

        // Leap year so if user enters 29/02 it'll look for leap years in the api
        boolean isAValidDate = isDateValid("2012-" + month + "-" + day);

        if (!isAValidDate) {
            return "redirect:/";
        }

        dayOfToday = day;
        monthOfToday = month;
        defaultEventType = eventType.toLowerCase();
        defaultUrlEvents = EventService.buildEventUrl(dayOfToday, monthOfToday, defaultEventType);

        return "redirect:/";
    }

    @GetMapping("/about")
    public String getMyAboutPage() {
        return "about";
    }

}
