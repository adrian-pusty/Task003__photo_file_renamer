package com;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Photo
{
    private String nameWithoutExtension;
    private String extension;
    private String city;
    private LocalDate date;
    private LocalTime time;

    private Photo() {}

    public static Optional<Photo> fromArray(String[] arr) {
        if (arr.length == 4) {
            Photo photo = new Photo();
            photo.setNameAndExtension(arr[0]);
            photo.setCity(arr[1]);
            photo.setDate(arr[2]);
            photo.setTime(arr[3]);
            return Optional.of(photo);
        }
        return Optional.empty();
    }

    public void setNameAndExtension(String fileName) {
        String[] split = fileName.split("\\.");
        this.nameWithoutExtension = split[0];
        this.extension = split[1];
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getExtension() {
        return extension;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = LocalTime.parse(time, DateTimeFormatter.ofPattern("H:mm:ss"));
    }
}
