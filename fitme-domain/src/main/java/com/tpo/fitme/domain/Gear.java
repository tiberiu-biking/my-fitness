package com.tpo.fitme.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tiberiu on 21/10/15.
 */
@Getter
@Setter
public class Gear {
    private Long id;
    private String externalId;
    private Long athleteId;
    private boolean primary;
    private String name;
    private Float distance;
    private String brandName;
    private String modelName;
    private String frameType;
    private String description;
    private Integer resourceState;
}