package com.tpo.fitness.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Tiberiu on 21/10/15.
 */
@Getter
@Setter
public class Athlete {

    private int id;
    private String firstName;
    private String lastName;
    private String profileMediumPicture;
    private String profile;
    private String city;
    private String state;
    private String sex;
    private List<Gear> bikes;
    private List<Gear> shoes;
}