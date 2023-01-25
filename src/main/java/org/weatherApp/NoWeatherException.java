package org.weatherApp;

public class NoWeatherException extends RuntimeException{
    public NoWeatherException(String message) {
        super(message);
    }
}
