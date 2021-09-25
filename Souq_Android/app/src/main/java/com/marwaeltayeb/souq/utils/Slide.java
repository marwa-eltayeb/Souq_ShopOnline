package com.marwaeltayeb.souq.utils;

import com.marwaeltayeb.souq.R;

import java.util.ArrayList;
import java.util.List;

public class Slide {

    private Slide(){}

    private static final List<Integer> slides = new ArrayList<>();

    static {
        slides.add(R.drawable.slide1);
        slides.add(R.drawable.slide2);
        slides.add(R.drawable.slide3);
        slides.add(R.drawable.slide4);
        slides.add(R.drawable.slide5);
        slides.add(R.drawable.slide6);
        slides.add(R.drawable.slide7);
    }

    public static List<Integer> getSlides() {
        return slides;
    }
}
