/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.deriveddata.api.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;

import java.util.Date;

/**
 */
public class TbData extends BaseOpenmrsData {

    private Integer id;

    private Patient patient;

    private Location location;

    private Encounter encounter;

    private Date originalDate;

    private Concept question;

    private Concept answer;

    private Boolean onStarted;

    private Date startDate;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(final Patient patient) {
        this.patient = patient;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(final Encounter encounter) {
        this.encounter = encounter;
    }

    public Date getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(final Date originalDate) {
        this.originalDate = originalDate;
    }

    public Concept getQuestion() {
        return question;
    }

    public void setQuestion(final Concept question) {
        this.question = question;
    }

    public Concept getAnswer() {
        return answer;
    }

    public void setAnswer(final Concept answer) {
        this.answer = answer;
    }

    public Boolean getOnStarted() {
        return onStarted;
    }

    public void setOnStarted(final Boolean onStarted) {
        this.onStarted = onStarted;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }
}
