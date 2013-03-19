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
package org.openmrs.module.deriveddata.api.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.ObsService;
import org.openmrs.api.PatientService;
import org.openmrs.api.PatientSetService;
import org.openmrs.api.context.Context;
import org.openmrs.module.deriveddata.api.model.TbData;
import org.openmrs.module.deriveddata.api.service.TbDataService;
import org.openmrs.module.deriveddata.api.util.ArvDataUtils;
import org.openmrs.module.deriveddata.api.util.TbDataUtils;
import org.openmrs.module.deriveddata.api.util.ValidateUtils;
import org.openmrs.scheduler.tasks.AbstractTask;

import java.util.Date;
import java.util.List;

/**
 */
public class TbDataExtractorTask extends AbstractTask {

    private final Log log = LogFactory.getLog(TbDataExtractorTask.class);

    @Override
    public void execute() {
        Context.openSession();
        try {
            ObsService obsService = Context.getObsService();
            PatientSetService patientSetService = Context.getPatientSetService();

            Integer counter = 0;
            Cohort cohort = patientSetService.getAllPatients();

            for (Integer patientId : cohort.getMemberIds()) {
                for (Concept question : TbDataUtils.getQuestions()) {
                    Date currentDate = null;
                    Boolean onStarted = Boolean.FALSE;
                    Patient patient = new Patient(patientId);
                    List<Obs> observations = obsService.getObservationsByPersonAndConcept(patient, question);
                    for (Obs observation : observations) {
                        Concept valueCoded = observation.getValueCoded();
                        if (TbDataUtils.getAnswers().contains(valueCoded)
                                && ValidateUtils.isValid(observation.getObsDatetime())) {

                            if (TbDataUtils.ANSWER_START_DRUGS.equals(valueCoded)) {
                                if (!onStarted) {
                                    onStarted = Boolean.TRUE;
                                    currentDate = observation.getObsDatetime();
                                }
                            } else if (TbDataUtils.ANSWER_STOP_DRUGS.equals(valueCoded)) {
                                if (onStarted) {
                                    onStarted = Boolean.FALSE;
                                    currentDate = observation.getObsDatetime();
                                }
                            }

                            TbData tbData = new TbData();
                            tbData.setQuestion(observation.getConcept());
                            tbData.setAnswer(observation.getValueCoded());

                            Encounter encounter = observation.getEncounter();
                            tbData.setEncounter(encounter);
                            tbData.setPatient(encounter.getPatient());
                            tbData.setLocation(encounter.getLocation());
                            tbData.setOriginalDate(encounter.getEncounterDatetime());

                            tbData.setOnStarted(onStarted);
                            tbData.setStartDate(currentDate);

                            Context.getService(TbDataService.class).saveTbData(tbData);
                            cleanSession(counter++);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Unable to execute task!", e);
        } finally {
            Context.closeSession();
        }
    }

    private void cleanSession(final Integer counter) {
        if (counter % 20 == 0) {
            log.info("Clearing up the session!");
            Context.clearSession();
        }
    }
}
