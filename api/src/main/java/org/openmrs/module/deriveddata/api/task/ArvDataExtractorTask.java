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
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.ObsService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.deriveddata.api.model.ArvData;
import org.openmrs.module.deriveddata.api.service.ArvDataService;
import org.openmrs.module.deriveddata.api.util.ArvDataUtils;
import org.openmrs.scheduler.tasks.AbstractTask;

import java.util.List;

/**
 */
public class ArvDataExtractorTask extends AbstractTask {

    private final Log log = LogFactory.getLog(ArvDataExtractorTask.class);

    @Override
    public void execute() {
        Context.openSession();
        try {
            ObsService obsService = Context.getObsService();
            PatientService patientService = Context.getPatientService();

            Integer counter = 0;
            List<Patient> patients = patientService.getAllPatients();
            for (Patient patient : patients) {
                for (Concept question : ArvDataUtils.getQuestions()) {
                    List<Obs> observations = obsService.getObservationsByPersonAndConcept(patient, question);
                    for (Obs observation : observations) {
                        ArvData arvData = getArvData(observation);
                        Context.getService(ArvDataService.class).saveArvData(arvData);
                        cleanSession(counter++);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Unable to execute task!", e);
        } finally {
            Context.closeSession();
        }
    }

    private ArvData getArvData(final Obs observation) {
        Concept question = observation.getConcept();
        Concept valueCoded = observation.getValueCoded();

        Encounter encounter = observation.getEncounter();
        ArvData arvData = Context.getService(ArvDataService.class).getArvData(encounter, question);
        if (arvData == null) {
            Obs parentObs = observation.getObsGroup();
            Concept parentQuestion = null;
            if (parentObs != null)
                parentQuestion = parentObs.getConcept();

            arvData = new ArvData();
            arvData.setQuestion(question);
            arvData.setEncounter(encounter);
            arvData.setParentQuestion(parentQuestion);
            arvData.setPatient(encounter.getPatient());
            arvData.setLocation(encounter.getLocation());
            arvData.setEncounterDatetime(encounter.getEncounterDatetime());
        }
        arvData.setMedications(valueCoded, Boolean.TRUE);
        return arvData;
    }

    private void cleanSession(final Integer counter) {
        if (counter > 20) {
            log.info("Clearing up the session!");
            Context.clearSession();
        }
    }
}
