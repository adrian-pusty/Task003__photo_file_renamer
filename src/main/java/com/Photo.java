package com;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Photo
{
    public static final Map<String, ZoneId> ZONES = initZones();
    private ZonedDateTime time;
    private String nameWithoutExtension;
    private String extension;
    private String city;

    private Photo() {}

    public static Optional<Photo> fromArray(String[] arr)
    {
        if (arr.length == 4)
        {
            Photo photo = new Photo();
            photo.setNameAndExtension(arr[0]);
            photo.setCity(arr[1]);
            photo.setTime(arr);
            return Optional.of(photo);
        }
        return Optional.empty();
    }

    public void setNameAndExtension(String fileName)
    {
        String[] split = fileName.split("\\.");
        this.nameWithoutExtension = split[0];
        this.extension = split[1];
    }

    public String getCity()
    {
        return city;
    }

    private void setCity(String city)
    {
        this.city = city;
    }

    public String getExtension()
    {
        return extension;
    }


    public ZonedDateTime getTime()
    {
        return time;
    }

    private void setTime(String[] arr)
    {
        String city = arr[1];
        LocalDate date = LocalDate.parse(arr[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime time = LocalTime.parse(arr[3], DateTimeFormatter.ofPattern("H:mm:ss"));
        this.time = ZonedDateTime.of(date, time, ZONES.get(city));
    }

    public static Map<String, ZoneId> initZones()
    {
        Map<String, ZoneId> zones = new HashMap<>();
        zones.put("Warsaw", ZoneId.of("Europe/Warsaw"));
        zones.put("Paris", ZoneId.of("Europe/Paris"));
        zones.put("London", ZoneId.of("Europe/London"));
        return zones;
    }
}
