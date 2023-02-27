package com;

import com.auxiliary.PhotoWithInitPos;
import com.auxiliary.PhotosWithSecondaryPos;

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

        List<PhotoWithInitPos> photosWithInitialPositions = IntStream.range(0, photos.size())
                .mapToObj(i -> new PhotoWithInitPos(photos.get(i), i))
                .collect(Collectors.toList());

        Map<String, List<PhotoWithInitPos>> groupedByCity = photosWithInitialPositions.stream()
                .collect(groupingBy(PhotoWithInitPos::getCity));

        return groupedByCity.values().stream()
                .flatMap(Solution::withFileNameSuffixes)
                .sorted(Comparator.comparing(PhotosWithSecondaryPos::getInitialPosition))
                .map(PhotosWithSecondaryPos::getFinalName)
                .collect(Collectors.joining("\n"));
    }


    public static Stream<PhotosWithSecondaryPos> withFileNameSuffixes(List<PhotoWithInitPos> photos) {
        int size = photos.size();

        List<PhotoWithInitPos> sorted = photos.stream()
                .sorted(Comparator.comparing(PhotoWithInitPos::getZonedDateTime))
                .collect(Collectors.toList());

        return IntStream.range(0, size)
                .mapToObj(i -> new PhotosWithSecondaryPos(sorted.get(i), i, size));
    }
}