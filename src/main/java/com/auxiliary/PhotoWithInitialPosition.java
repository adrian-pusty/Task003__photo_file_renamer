package com.auxiliary;

import com.Photo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

public class PhotoWithInitialPosition
{
    public static final Map<String, ZoneId> ZONES = Map.of("Warsaw", ZoneId.of("Europe/Warsaw"),
            "Paris", ZoneId.of("Europe/Paris"),
            "London", ZoneId.of("Europe/London")); //to be extended ..

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
}