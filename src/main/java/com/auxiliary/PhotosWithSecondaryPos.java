package com.auxiliary;

public class PhotosWithSecondaryPos
{
    private final PhotoWithInitPos photo;
    private String secondaryPosition;

    public PhotosWithSecondaryPos(PhotoWithInitPos photo, int secondaryPosition, int nrOfDigits)
    {
        this.photo = photo;
        this.setSecondaryPosition(secondaryPosition, nrOfDigits);
    }

    public static String numberOfLeadingZeros(int secondaryPosition, int nrOfDigits)
    {
        int i = (int) (Math.log10(nrOfDigits) + 1);
        String format = "%0" + i + "d";
        return String.format(format, secondaryPosition + 1);
    }

    public int getInitialPosition()
    {
        return photo.getInitialPosition();
    }

    public void setSecondaryPosition(int secondaryPosition, int nrOfDigits)
    {
        this.secondaryPosition = numberOfLeadingZeros(secondaryPosition, nrOfDigits);
    }

    public String getFinalName()
    {
        return photo.getCity() + secondaryPosition + "." + photo.getExtension();
    }
}