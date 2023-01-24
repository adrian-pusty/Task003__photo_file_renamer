package com;

import com.auxiliary.PhotoWithInitialPosition;
import com.auxiliary.PhotosWithSecondaryPosition;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

class Solution
{
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

        System.out.println(new Solution().solution(S));
    }

    public static Stream<PhotosWithSecondaryPosition> withFileNameSuffixes(List<PhotoWithInitialPosition> photos) {
        int size = photos.size();

        List<PhotoWithInitialPosition> sorted = photos.stream()
                .sorted(Comparator.comparing(PhotoWithInitialPosition::getZonedDateTime))
                .collect(Collectors.toList());

        return IntStream.range(0, size)
                .mapToObj(i -> new PhotosWithSecondaryPosition(sorted.get(i), i, size));
    }

    public String solution(String S) {
        List<Photo> photos = Arrays.stream(S.split("\\r?\\n"))
                .map(s -> s
                        .replace(",", "")
                        .split(" "))
                .map(Photo::fromArray)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<PhotoWithInitialPosition> photosWithInitialPositions = IntStream.range(0, photos.size())
                .mapToObj(i -> new PhotoWithInitialPosition(photos.get(i), i))
                .collect(Collectors.toList());

        Map<String, List<PhotoWithInitialPosition>> groupedByCity = photosWithInitialPositions.stream()
                .collect(groupingBy(PhotoWithInitialPosition::getCity));

        return groupedByCity.values().stream()
                .flatMap(Solution::withFileNameSuffixes)
                .sorted(Comparator.comparing(PhotosWithSecondaryPosition::getInitialPosition))
                .map(PhotosWithSecondaryPosition::getFinalName)
                .collect(Collectors.joining("\n"));
    }
}