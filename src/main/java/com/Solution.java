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


    public static Stream<PhotosWithSecondaryPosition> withFileNameSuffixes(List<PhotoWithInitialPosition> photos) {
        int size = photos.size();

        List<PhotoWithInitialPosition> sorted = photos.stream()
                .sorted(Comparator.comparing(PhotoWithInitialPosition::getZonedDateTime))
                .collect(Collectors.toList());

        return IntStream.range(0, size)
                .mapToObj(i -> new PhotosWithSecondaryPosition(sorted.get(i), i, size));
    }
}