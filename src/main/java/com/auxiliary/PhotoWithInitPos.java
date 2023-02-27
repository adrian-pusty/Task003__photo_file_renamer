package com.auxiliary;

import com.Photo;

import java.time.ZonedDateTime;


public class PhotoWithInitPos
{
    private final Photo photo;
    private final int initialPosition;

    public PhotoWithInitPos(Photo photo, int initialPosition)
    {
        this.photo = photo;
        this.initialPosition = initialPosition;
    }

    public String getCity()
    {
        return photo.getCity();
    }

    public int getInitialPosition()
    {
        return initialPosition;
    }

    public ZonedDateTime getZonedDateTime()
    {
        return photo.getTime();
    }

    public String getExtension()
    {
        return photo.getExtension();
    }
}