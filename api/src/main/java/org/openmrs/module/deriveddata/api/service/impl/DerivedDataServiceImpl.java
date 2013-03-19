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

import org.openmrs.OpenmrsObject;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.deriveddata.api.service.DerivedDataService;
import org.openmrs.module.deriveddata.api.db.DerivedDataDAO;

/**
 * It is a default implementation of {@link DerivedDataService}.
 */
public class DerivedDataServiceImpl extends BaseOpenmrsService implements DerivedDataService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private DerivedDataDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(final DerivedDataDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public DerivedDataDAO getDao() {
	    return dao;
    }

    @Override
    public OpenmrsObject getObjectByUuid(final String classname, final String uuid) {
        return getDao().getObjectByUuid(classname, uuid);
    }
}