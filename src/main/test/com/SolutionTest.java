package com;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest
{
    private static final String EXPECTED = "Warsaw02.jpg\n" +
            "London1.png\n" +
            "Warsaw01.png\n" +
            "Paris2.jpg\n" +
            "Paris1.jpg\n" +
            "London2.jpg\n" +
            "Paris3.png\n" +
            "Warsaw03.jpg\n" +
            "Warsaw09.png\n" +
            "Warsaw07.jpg\n" +
            "Warsaw06.jpg\n" +
            "Warsaw08.jpg\n" +
            "Warsaw04.png\n" +
            "Warsaw05.png\n" +
            "Warsaw10.jpg";

    private static final String INPUT = "photo.jpg, Warsaw, 2013-09-05 14:08:15\n" +
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

    @Test
    public void solutionTest()
    {
        String result = new Solution().solution(INPUT);

        assertEquals(EXPECTED, result);
    }
}