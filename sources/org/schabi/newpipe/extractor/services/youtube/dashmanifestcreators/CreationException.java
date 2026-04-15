package org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators;

public final class CreationException extends RuntimeException {
    public CreationException(String str) {
        super(str);
    }

    public static CreationException couldNotAddElement(String str, Exception exc) {
        return new CreationException(y2.j("Could not add ", str, " element"), exc);
    }

    public CreationException(String str, Exception exc) {
        super(str, exc);
    }

    public static CreationException couldNotAddElement(String str, String str2) {
        return new CreationException("Could not add " + str + " element: " + str2);
    }
}
