package com.madratz.decision;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Decision {

    private List<ActionRequest> mActionRequests;

    public Decision(ActionRequest... requests) {
        mActionRequests = new LinkedList<>(Arrays.asList(requests));
    }

    public void addActionRequest(ActionRequest actionRequest){
        mActionRequests.add(actionRequest);
    }

    public void apply(){
        mActionRequests.stream().forEach(ActionRequest::handle);
    }
}
