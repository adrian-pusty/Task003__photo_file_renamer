package com.auxiliary;

import com.Photo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class PhotoWithInitialPosition
{
    public static final Map<String, ZoneId> ZONES = initZones();
    private final Photo photo;
    private final ZonedDateTime zonedDateTime;
    private final int initialPosition;

    public PhotoWithInitialPosition(Photo photo, int initialPosition) {
        this.photo = photo;
        this.initialPosition = initialPosition;
        this.zonedDateTime = ZonedDateTime.of(photo.getDate(), photo.getTime(), ZONES.get(photo.getCity()));
    }

    public String getCity() {
        return photo.getCity();
    }

    public int getInitialPosition() {
        return initialPosition;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public String getExtension() {
        return photo.getExtension();
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