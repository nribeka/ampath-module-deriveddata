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
package org.openmrs.module.deriveddata.api.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.deriveddata.api.db.ArvDataDAO;
import org.openmrs.module.deriveddata.api.model.ArvData;
import org.openmrs.module.deriveddata.api.service.ArvDataService;

import java.util.List;

/**
 * It is a default implementation of {@link org.openmrs.module.deriveddata.api.service.DerivedDataService}.
 */
public class ArvDataServiceImpl extends BaseOpenmrsService implements ArvDataService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private ArvDataDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(final ArvDataDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public ArvDataDAO getDao() {
	    return dao;
    }

    @Override
    public ArvData saveArvData(final ArvData data) {
        return getDao().saveArvData(data);
    }

    @Override
    public ArvData getArvData(final Encounter encounter, final Concept concept) {
        return getDao().getArvData(encounter, concept);
    }

    @Override
    public List<ArvData> findArvDataByEncounter(final Encounter encounter) {
        return getDao().findArvDataByEncounter(encounter);
    }

    @Override
    public List<ArvData> findArvDataByPatient(final Patient patient) {
        return getDao().findArvDataByPatient(patient);
    }

    @Override
    public void deleteArvData(final ArvData data) {
        getDao().deleteArvData(data);
    }
}