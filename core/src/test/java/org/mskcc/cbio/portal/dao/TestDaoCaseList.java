/** Copyright (c) 2012 Memorial Sloan-Kettering Cancer Center.
**
** This library is free software; you can redistribute it and/or modify it
** under the terms of the GNU Lesser General Public License as published
** by the Free Software Foundation; either version 2.1 of the License, or
** any later version.
**
** This library is distributed in the hope that it will be useful, but
** WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
** MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
** documentation provided hereunder is on an "as is" basis, and
** Memorial Sloan-Kettering Cancer Center 
** has no obligations to provide maintenance, support,
** updates, enhancements or modifications.  In no event shall
** Memorial Sloan-Kettering Cancer Center
** be liable to any party for direct, indirect, special,
** incidental or consequential damages, including lost profits, arising
** out of the use of this software and its documentation, even if
** Memorial Sloan-Kettering Cancer Center 
** has been advised of the possibility of such damage.  See
** the GNU Lesser General Public License for more details.
**
** You should have received a copy of the GNU Lesser General Public License
** along with this library; if not, write to the Free Software Foundation,
** Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
**/

package org.mskcc.cbio.portal.dao;

import java.util.ArrayList;
import junit.framework.TestCase;
import org.mskcc.cbio.portal.model.*;
import org.mskcc.cbio.portal.dao.*;
import org.mskcc.cbio.portal.scripts.ResetDatabase;

/**
 * JUnit test for DaoCase List.
 */
public class TestDaoCaseList extends TestCase {

    public void testDaoCaseList() throws DaoException {
        ResetDatabase.resetDatabase();
        createSamples();
        DaoCaseList daoCaseList = new DaoCaseList();
        CaseList caseList = new CaseList();
        caseList.setName("Name0");
        caseList.setDescription("Description0");
        caseList.setStableId("stable_0");
        caseList.setCancerStudyId(2);
        caseList.setCaseListCategory(CaseListCategory.ALL_CASES_WITH_CNA_DATA);
        ArrayList<String> cases = new ArrayList<String>();
        cases.add("TCGA-1");
        cases.add("TCGA-2");
        caseList.setCaseList(cases);
        daoCaseList.addCaseList(caseList);
        
        CaseList caseListFromDb = daoCaseList.getCaseListByStableId("stable_0");
        assertEquals("Name0", caseListFromDb.getName());
        assertEquals("Description0", caseListFromDb.getDescription());
        assertEquals(CaseListCategory.ALL_CASES_WITH_CNA_DATA, caseListFromDb.getCaseListCategory());
        assertEquals("stable_0", caseListFromDb.getStableId());
        assertEquals(2, caseListFromDb.getCaseList().size());
    }

    private void createSamples() throws DaoException {
        CancerStudy study = new CancerStudy("study", "description", "id", "brca", true);
        Patient p = new Patient(study, "TCGA-1");
        int pId = DaoPatient.addPatient(p);
        Sample s = new Sample("TCGA-1", pId, "type");
        DaoSample.addSample(s);
        p = new Patient(study, "TCGA-2");
        pId = DaoPatient.addPatient(p);
        s = new Sample("TCGA-2", pId, "type");
        DaoSample.addSample(s);
    }
}
