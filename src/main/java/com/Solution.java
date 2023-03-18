package com;

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
    public String solution(String S)
    {
        List<Photo.Builder> photos = Arrays.stream(S.split("\\r?\\n"))
                .map(s -> s
                        .replace(",", "")
                        .split(" "))
                .map(Photo::fromArray)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<Photo.Builder> withInitialPositions = IntStream.range(0, photos.size())
                .mapToObj(i -> photos.get(i).initialPosition(i))
                .collect(Collectors.toList());

        Map<String, List<Photo.Builder>> groupedByCity = withInitialPositions.stream()
                .collect(groupingBy(Photo.Builder::getCity));

        return groupedByCity.values().stream()
                .flatMap(Solution::withFileNameSuffixes)
                .sorted(Comparator.comparing(Photo::getInitialPosition))
                .map(Photo::getFinalName)
                .collect(Collectors.joining("\n"));
    }

    public static Stream<Photo> withFileNameSuffixes(List<Photo.Builder> photos)
    {
        int size = photos.size();

        List<Photo.Builder> sorted = photos.stream()
                .sorted(Comparator.comparing(Photo.Builder::getTime))
                .collect(Collectors.toList());

        return IntStream.range(0, size)
                .mapToObj(i -> sorted.get(i).secondaryPosition(i, size).build());
    }
}