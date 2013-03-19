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
package org.openmrs.module.deriveddata.api.db;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.module.deriveddata.api.model.ArvData;

import java.util.List;

/**
 */
public interface ArvDataDAO {

    ArvData saveArvData(final ArvData data);

    ArvData getArvData(final Encounter encounter, final Concept question);

    List<ArvData> findArvDataByEncounter(final Encounter encounter);

    List<ArvData> findArvDataByPatient(final Patient patient);

    void deleteArvData(final ArvData data);
}
