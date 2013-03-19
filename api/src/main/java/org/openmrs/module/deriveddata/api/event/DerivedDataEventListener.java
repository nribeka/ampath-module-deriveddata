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
package org.openmrs.module.deriveddata.api.event;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.EncounterService;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.event.Event;
import org.openmrs.event.SubscribableEventListener;
import org.openmrs.module.deriveddata.api.model.ArvData;
import org.openmrs.module.deriveddata.api.model.TbData;
import org.openmrs.module.deriveddata.api.service.ArvDataService;
import org.openmrs.module.deriveddata.api.service.TbDataService;
import org.openmrs.module.deriveddata.api.util.ArvDataUtils;
import org.openmrs.module.deriveddata.api.util.TbDataUtils;
import org.openmrs.module.deriveddata.api.util.ValidateUtils;
import org.openmrs.util.PrivilegeConstants;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class DerivedDataEventListener implements SubscribableEventListener {

    private final Log log = LogFactory.getLog(DerivedDataEventListener.class);

    private static final String ENCOUNTER_CLASS_NAME = Encounter.class.getName();

    private static final String OBS_CLASS_NAME = Obs.class.getName();

    @Override
    @SuppressWarnings("all")
    public List<Class<? extends OpenmrsObject>> subscribeToObjects() {
        Object classes = Arrays.asList(Encounter.class, Obs.class);
        return (List<Class<? extends OpenmrsObject>>) classes;
    }

    @Override
    public List<String> subscribeToActions() {
        return Arrays.asList(
                Event.Action.CREATED.name(), Event.Action.UPDATED.name(),
                Event.Action.VOIDED.name(), Event.Action.UNVOIDED.name());
    }

    @Override
    public void onMessage(final Message message) {
        // TODO: this need to be changed. See ticket: TRUNK-3781
        // on newer version of OpenMRS (1.9):
        // - set the activator to become DaemonTokenAware and set the module token
        // - we can then handle this message inside Daemon.runInDaemonThread
        Context.openSession();
        Context.addProxyPrivilege(PrivilegeConstants.VIEW_OBS);
        Context.addProxyPrivilege(PrivilegeConstants.VIEW_ENCOUNTERS);
        try {
            MapMessage mapMessage = (MapMessage) message;

            String uuid = mapMessage.getString("uuid");
            String classname = mapMessage.getString("classname");
            String actionString = mapMessage.getString("action");
            Event.Action action = Event.Action.valueOf(actionString);

            if (StringUtils.equals(classname, ENCOUNTER_CLASS_NAME))
                handleEncounter(uuid, action);
            else if (StringUtils.equals(classname, OBS_CLASS_NAME))
                handleObservation(uuid, action);
            else
                log.error("This will be a bug in event module. We're only interested with two classes!");
        } catch (JMSException e) {
            log.error("Reading JMS message failed!", e);
        } finally {
            Context.removeProxyPrivilege(PrivilegeConstants.VIEW_OBS);
            Context.removeProxyPrivilege(PrivilegeConstants.VIEW_ENCOUNTERS);
            Context.clearSession();
            Context.closeSession();
        }
    }

    private void handleObservation(final String uuid, final Event.Action action) {
        // Obs event: can only update the medications of arv record!
        ObsService obsService = Context.getObsService();

        Obs obs = obsService.getObsByUuid(uuid);
        Concept question = obs.getConcept();
        if (ValidateUtils.isValid(obs.getObsDatetime())) {
            Concept valueCoded = obs.getValueCoded();
            Encounter encounter = obs.getEncounter();
            // we only process if the question is one of the arv questions!
            if (ArvDataUtils.getQuestions().contains(question)) {
                ArvDataService service = Context.getService(ArvDataService.class);
                ArvData arvData = service.getArvData(encounter, question);
                if (arvData != null) {
                    switch (action) {
                        case CREATED:
                        case UNVOIDED: {
                            arvData.setMedications(valueCoded, Boolean.TRUE);
                            service.saveArvData(arvData);
                            break;
                        }
                        case UPDATED: {
                            // We need to switch the old value to false and the new value to true.
                            // But because we don't know the old value, we need to iterate all obs here.
                            arvData.resetMedications();
                            List<Obs> observations =
                                    obsService.getObservationsByPersonAndConcept(obs.getPerson(), question);
                            for (Obs observation : observations)
                                if (encounter.equals(observation.getEncounter()))
                                    arvData.setMedications(observation.getValueCoded(), Boolean.TRUE);
                            service.saveArvData(arvData);
                            break;
                        }
                        case VOIDED: {
                            arvData.setMedications(valueCoded, Boolean.FALSE);
                            service.saveArvData(arvData);
                            break;
                        }
                    }
                }
            } else if (TbDataUtils.getQuestions().contains(question)) {
                TbDataService service = Context.getService(TbDataService.class);
                TbData tbData = service.getTbData(encounter, question);
                if (tbData != null) {
                    switch (action) {
                        case CREATED:
                        case UNVOIDED: {
                            break;
                        }
                        case UPDATED: {
                            break;
                        }
                        case VOIDED: {
                            break;
                        }
                    }
                }
            }
        }
    }

    private void handleEncounter(final String uuid, final Event.Action action) {
        // Encounter event: can create, can update encounter information and can delete arv data
        EncounterService encounterService = Context.getEncounterService();
        ArvDataService arvDataService = Context.getService(ArvDataService.class);
        TbDataService tbDataService = Context.getService(TbDataService.class);

        Encounter encounter = encounterService.getEncounterByUuid(uuid);
        switch (action) {
            case CREATED:
            case UNVOIDED: {
                Map<Concept, ArvData> arvDataMap = getConceptArvDataMap(encounter);
                for (ArvData arvData : arvDataMap.values())
                    arvDataService.saveArvData(arvData);
                break;
            }
            case UPDATED: {
                List<ArvData> arvDataList = arvDataService.findArvDataByEncounter(encounter);
                for (ArvData arvData : arvDataList) {
                    arvData.setEncounterDatetime(encounter.getEncounterDatetime());
                    arvData.setLocation(encounter.getLocation());
                    arvDataService.saveArvData(arvData);
                }
                break;
            }
            case VOIDED: {
                List<ArvData> arvDataList = arvDataService.findArvDataByEncounter(encounter);
                for (ArvData arvData : arvDataList)
                    arvDataService.deleteArvData(arvData);
                // get the episodes and then recalculate the episodes datetime
                break;
            }
        }
    }

    private Map<Concept, ArvData> getConceptArvDataMap(final Encounter encounter) {
        Map<Concept, ArvData> arvDataMap = new HashMap<Concept, ArvData>();
        for (Obs obs : encounter.getAllObs()) {
            Concept question = obs.getConcept();
            if (ArvDataUtils.getQuestions().contains(question)
                    && ValidateUtils.isValid(obs.getObsDatetime())) {
                Concept valueCoded = obs.getValueCoded();
                ArvData arvData = arvDataMap.get(question);
                if (arvData == null) {
                    Obs parentObs = obs.getObsGroup();
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
                arvDataMap.put(question, arvData);
            }
        }
        return arvDataMap;
    }
}
