package com;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

class Photo {
    public Photo(String nameWithoutExtension, String extension, String city, String date, String time) {
        this.nameWithoutExtension = nameWithoutExtension;
        this.extension = extension;
        this.city = city;
        this.setDate(date);
        this.setTime(time);
    }

    public Photo() {}

    private String nameWithoutExtension;
    private String extension;
    private String city;
    private LocalDate date;
    private LocalTime time;

    public String getNameWithoutExtension() {
        return nameWithoutExtension;
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
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
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

    public static Optional<Photo> fromArray(String[] arr)
    {
        if(arr.length == 4)
        {
            Photo photo = new Photo();
            photo.setNameAndExtension(arr[0]);
            photo.setCity(arr[1]);
            photo.setDate(arr[2]);
            photo.setTime(arr[3]);
            return Optional.of(photo);
        }
        return Optional.empty();
    }
}

class Solution {
    //Extensions: jpg, png, jpeg
    //city where photo was taken
    //time and date the photo
    // photo.jpg, Warsaw, 2013-09-05 14:08:15

    // 1. group by city
    /**
     * 2. sort by the time
     * natural numbers assignet to the photos starting from 1.
     *
     * name: city_number_,extension (prefix numbers 000001 - length of the longest number)
     *
     *
     */


    public static void main(String[] args) {
        String S = "photo.jpg, Warsaw, 2013-09-05 14:08:15\n" +
                "john.png, London, 2015-06-20 15:13:22\n" +
                "myFriends.png, Warsaw, 2013-09-05 14:07:13\n" +
                "Eiffel.jpg, Paris, 2015-07-23 08:03:02\n" +
                "pisatower.jpg, Paris, 2015-07-22 23:59:59\n" +
                "BOB.jpg, London, 2015-08-05 00:02:03\n" +
                "notredame.png, Paris, 2015-09-01 12:00:00\n" +
                "me.jpg, Warsaw, 2013-09-06 15:40:22\n" +
                "a.png, Warsaw, 2016-02-13 13:33:50\n" +
                "b.jpg, Warsaw, 2016-01-02 15:12:22\n" +
                "c.jpg, Warsaw, 2016-01-02 14:34:30\n" +
                "d.jpg, Warsaw, 2016-01-02 15:15:01\n" +
                "e.png, Warsaw, 2016-01-02 09:49:09\n" +
                "f.png, Warsaw, 2016-01-02 10:55:32\n" +
                "g.jpg, Warsaw, 2016-02-29 22:13:11";


        List<Photo> photos = Arrays.stream(S.split("\\r?\\n"))
                .map(s -> s.split(" "))
                .map(Photo::fromArray)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

//                .collect(Collectors.groupingBy(Photo::getCity));


        List<PhotoWithInitialPosition> photosWP = IntStream.range(0, photos.size())
                .mapToObj(i -> new PhotoWithInitialPosition(photos.get(i), i))
                .collect(Collectors.toList());

        Map<String, List<PhotoWithInitialPosition>> collect = photosWP.stream().collect(groupingBy(p -> p.getPhoto().getCity()));//todo handle demeter

        collect.entrySet().stream().
                map(e -> handleGroup(e.getValue()))
                .flatMap(i -> i)
                .sorted(Comparator.comparing(p -> p.getPhoto().getInitialPosition()))
                 .peek(s -> System.out.println(s.getFinalName()))
                 .map(s -> s.getFinalName())
                 .collect(Collectors.joining(","));

//        for(String s: split)
//        {
//            System.out.println(s);
//        }
    }
//    public String solution(String S) {
//        // write your code in Java SE 8
//
//        String lines[] = S.split("\\r?\\n");
//    }

    public static Stream<PhotosWithSecondaryPosition> handleGroup(List<PhotoWithInitialPosition> photos)
    {
        int size = photos.size();

        List<PhotoWithInitialPosition> sorted = photos.stream()
                .sorted(Comparator.comparing(PhotoWithInitialPosition::getZdt))
                .collect(Collectors.toList());

        return IntStream.range(0, size)
                .mapToObj(i -> new PhotosWithSecondaryPosition(sorted.get(i), i, size));
//                .map(PhotosWithSecondaryPosition::getFinalName)
//                .peek(System.out::println);
    }

}


class PhotoWithInitialPositionAndNameSuffix
{
    public PhotoWithInitialPositionAndNameSuffix(PhotoWithInitialPosition photo, String nameSuffix) {
        this.photo = photo;
        this.nameSuffix = nameSuffix;
    }

    private PhotoWithInitialPosition photo;
    private String nameSuffix;

    public PhotoWithInitialPosition getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoWithInitialPosition photo) {
        this.photo = photo;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }
}

class PhotoWithInitialPosition
{
    public static final Map<String, ZoneId> ZONES = Map.of("Warsaw", ZoneId.of("Europe/Warsaw"),
            "Paris", ZoneId.of("Europe/Paris"),
            "London", ZoneId.of("Europe/London"));

    public PhotoWithInitialPosition(Photo photo, int initialPosition) {
        this.photo = photo;
        this.initialPosition = initialPosition;
        this.zdt = ZonedDateTime.of(photo.getDate(), photo.getTime(), ZONES.get(photo.getCity()));
    }

    private Photo photo;
    private int initialPosition;
    private ZonedDateTime zdt;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public int getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }

    public ZonedDateTime getZdt() {
        return zdt;
    }
}

class PhotosWithSecondaryPosition
{
    public PhotosWithSecondaryPosition(PhotoWithInitialPosition photo, int secondaryPosition, int nrOfDigits) {
        this.photo = photo;
        this.setSecondaryPosition(secondaryPosition, nrOfDigits);
    }

    private PhotoWithInitialPosition photo;
    private String secondaryPosition;

    public PhotoWithInitialPosition getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoWithInitialPosition photo) {
        this.photo = photo;
    }

    public String getSecondaryPosition() {
        return secondaryPosition;
    }

    public void setSecondaryPosition(int secondaryPosition, int nrOfDigits) {
        this.secondaryPosition = numberOfLeadingZeros(secondaryPosition, nrOfDigits);
    }
//    public static int numberOfLeadingZeros(List<Photo> photos)
//    {
//        int size = photos.size();
//        int i = (int) (Math.log10(size) + 1);
//        String format = "%0" + i + "d";
//        String formatted = String.format("format", 5);
//        return 1;
//    }

    public static String numberOfLeadingZeros(int secondaryPosition, int nrOfDigits)
    {
        int i = (int) (Math.log10(nrOfDigits) + 1);
        String format = "%0" + i + "d";
        return String.format("format", secondaryPosition);
    }

    public String getFinalName()
    {
        return photo.getPhoto().getCity() + secondaryPosition + "." + photo.getPhoto().getExtension(); //todo handle demeter
    }

}