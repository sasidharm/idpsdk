package com.equifax.eid.idpsdk;

import com.equifax.eid.idpsdk.identity.EidRequest;
import com.equifax.eid.idpsdk.identity.Identity;
import com.equifax.eid.idpsdk.identity.IdentityProofing;

import junit.framework.TestCase;

/**
 * Created by Sasidhar on 11/15/14.
 */
public class EidClientTest extends TestCase {

    private EidClient eidClient = new EidClient();

    public void testDoInBackground()
    {
        EidRequest request = new EidRequest();
        Identity identity = new Identity();
        request.setIdentity(identity);
        IdentityProofing proofing = eidClient.doInBackground(request);
        assertNotNull(proofing);
        assertNull(proofing.getAssessment());
        assertNotNull(proofing.getQuestionnaire());
    }
}
