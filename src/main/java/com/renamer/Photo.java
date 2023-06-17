package com.renamer;

import lombok.Getter;

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
    @Getter
    private final int initialPosition;
    private final String extension;
    private final String city;
    private final String secondaryPosition;

    private Photo(Builder builder)
    {
        extension = builder.extension;
        city = builder.city;
        initialPosition = builder.initialPosition;
        secondaryPosition = builder.secondaryPosition;
    }


    public static Optional<Photo.Builder> fromArray(String[] arr)
    {
        if (arr.length == 4) {
            Builder builder = new Builder().nameAndExtension(arr[0]).city(arr[1]).time(arr);
            return Optional.of(builder);
        }
        return Optional.empty();
    }

    private static Map<String, ZoneId> initZones()
    {
        Map<String, ZoneId> zones = new HashMap<>();
        zones.put("Warsaw", ZoneId.of("Europe/Warsaw"));
        zones.put("Paris", ZoneId.of("Europe/Paris"));
        zones.put("London", ZoneId.of("Europe/London"));
        return zones;
    }


    public String getFinalName()
    {
        return city + secondaryPosition + "." + extension;
    }

    public static class Builder
    {
        @Getter
        private ZonedDateTime time;
        private String extension;
        @Getter
        private String city;
        private int initialPosition;
        private String secondaryPosition;

        private Builder time(String[] arr)
        {
            String city = arr[1];
            LocalDate date = LocalDate.parse(arr[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime time = LocalTime.parse(arr[3], DateTimeFormatter.ofPattern("H:mm:ss"));
            this.time = ZonedDateTime.of(date, time, ZONES.get(city));
            return this;
        }

        private Builder nameAndExtension(String fileName)
        {
            String[] split = fileName.split("\\.");
            this.extension = split[1];
            return this;
        }

        private Builder city(String city)
        {
            this.city = city;
            return this;
        }

        public Builder initialPosition(int initialPosition)
        {
            this.initialPosition = initialPosition;
            return this;
        }

        public Builder secondaryPosition(int secondaryPosition, int nrOfDigits)
        {
            this.secondaryPosition = numberOfLeadingZeros(secondaryPosition, nrOfDigits);
            return this;
        }

        private static String numberOfLeadingZeros(int secondaryPosition, int nrOfDigits)
        {
            int i = (int) (Math.log10(nrOfDigits) + 1);
            String format = "%0" + i + "d";
            return String.format(format, secondaryPosition + 1);
        }

        public Photo build()
        {
            return new Photo(this);
        }
    }
}