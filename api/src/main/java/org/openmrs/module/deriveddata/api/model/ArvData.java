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

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.module.deriveddata.api.util.ArvDataUtils;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.List;

/**
 */
public class ArvData extends BaseOpenmrsData {

    private final Log log = LogFactory.getLog(ArvData.class);

    private Integer id;

    private Date encounterDatetime;

    private Patient patient;

    private Encounter encounter;

    private Location location;

    private Concept parentQuestion;

    private Concept question;

    private Boolean onAbacavir;

    private Boolean onAtazanavir;

    private Boolean onDarunavir;

    private Boolean onDidanosine;

    private Boolean onEfavirenz;

    private Boolean onEmtricitabine;

    private Boolean onEtravirine;

    private Boolean onIndinavir;

    private Boolean onLamivudine;

    private Boolean onLopinavir;

    private Boolean onNelfinavir;

    private Boolean onNevirapine;

    private Boolean onRaltegravir;

    private Boolean onRitonavir;

    private Boolean onStavudine;

    private Boolean onTenofovir;

    private Boolean onZidovudine;

    private Boolean onOther;

    private Boolean onUnknown;

    public void resetMedications() {
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(this.getClass());
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            try {
                String name = propertyDescriptor.getName();
                if (StringUtils.startsWith(name, "on"))
                    PropertyUtils.setProperty(this, name, Boolean.FALSE);
            } catch (Exception e) {
                log.error("Unable to read property: " + propertyDescriptor.getName() + "!", e);
            }
        }
    }

    public void setMedications(final Concept valueCoded, final Boolean value) {
        List<String> fieldNames = ArvDataUtils.getMappings(valueCoded);
        for (String fieldName : fieldNames) {
            try {
                PropertyUtils.setProperty(this, fieldName, value);
            } catch (Exception e) {
                log.error("Unable to read property: " + fieldName + "!", e);
            }
        }
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer integer) {
        id = integer;
    }

    public Date getEncounterDatetime() {
        return encounterDatetime;
    }

    public void setEncounterDatetime(final Date encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(final Patient patient) {
        this.patient = patient;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(final Encounter encounter) {
        this.encounter = encounter;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public Concept getParentQuestion() {
        return parentQuestion;
    }

    public void setParentQuestion(final Concept parentQuestion) {
        this.parentQuestion = parentQuestion;
    }

    public Concept getQuestion() {
        return question;
    }

    public void setQuestion(final Concept question) {
        this.question = question;
    }

    public Boolean getOnAbacavir() {
        return onAbacavir;
    }

    public void setOnAbacavir(final Boolean onAbacavir) {
        this.onAbacavir = onAbacavir;
    }

    public Boolean getOnAtazanavir() {
        return onAtazanavir;
    }

    public void setOnAtazanavir(final Boolean onAtazanavir) {
        this.onAtazanavir = onAtazanavir;
    }

    public Boolean getOnDarunavir() {
        return onDarunavir;
    }

    public void setOnDarunavir(final Boolean onDarunavir) {
        this.onDarunavir = onDarunavir;
    }

    public Boolean getOnDidanosine() {
        return onDidanosine;
    }

    public void setOnDidanosine(final Boolean onDidanosine) {
        this.onDidanosine = onDidanosine;
    }

    public Boolean getOnEfavirenz() {
        return onEfavirenz;
    }

    public void setOnEfavirenz(final Boolean onEfavirenz) {
        this.onEfavirenz = onEfavirenz;
    }

    public Boolean getOnEmtricitabine() {
        return onEmtricitabine;
    }

    public void setOnEmtricitabine(final Boolean onEmtricitabine) {
        this.onEmtricitabine = onEmtricitabine;
    }

    public Boolean getOnEtravirine() {
        return onEtravirine;
    }

    public void setOnEtravirine(final Boolean onEtravirine) {
        this.onEtravirine = onEtravirine;
    }

    public Boolean getOnIndinavir() {
        return onIndinavir;
    }

    public void setOnIndinavir(final Boolean onIndinavir) {
        this.onIndinavir = onIndinavir;
    }

    public Boolean getOnLamivudine() {
        return onLamivudine;
    }

    public void setOnLamivudine(final Boolean onLamivudine) {
        this.onLamivudine = onLamivudine;
    }

    public Boolean getOnLopinavir() {
        return onLopinavir;
    }

    public void setOnLopinavir(final Boolean onLopinavir) {
        this.onLopinavir = onLopinavir;
    }

    public Boolean getOnNelfinavir() {
        return onNelfinavir;
    }

    public void setOnNelfinavir(final Boolean onNelfinavir) {
        this.onNelfinavir = onNelfinavir;
    }

    public Boolean getOnNevirapine() {
        return onNevirapine;
    }

    public void setOnNevirapine(final Boolean onNevirapine) {
        this.onNevirapine = onNevirapine;
    }

    public Boolean getOnRaltegravir() {
        return onRaltegravir;
    }

    public void setOnRaltegravir(final Boolean onRaltegravir) {
        this.onRaltegravir = onRaltegravir;
    }

    public Boolean getOnRitonavir() {
        return onRitonavir;
    }

    public void setOnRitonavir(final Boolean onRitonavir) {
        this.onRitonavir = onRitonavir;
    }

    public Boolean getOnStavudine() {
        return onStavudine;
    }

    public void setOnStavudine(final Boolean onStavudine) {
        this.onStavudine = onStavudine;
    }

    public Boolean getOnTenofovir() {
        return onTenofovir;
    }

    public void setOnTenofovir(final Boolean onTenofovir) {
        this.onTenofovir = onTenofovir;
    }

    public Boolean getOnZidovudine() {
        return onZidovudine;
    }

    public void setOnZidovudine(final Boolean onZidovudine) {
        this.onZidovudine = onZidovudine;
    }

    public Boolean getOnOther() {
        return onOther;
    }

    public void setOnOther(final Boolean onOther) {
        this.onOther = onOther;
    }

    public Boolean getOnUnknown() {
        return onUnknown;
    }

    public void setOnUnknown(final Boolean onUnknown) {
        this.onUnknown = onUnknown;
    }
}
