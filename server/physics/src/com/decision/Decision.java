package com.decision;

import java.util.ArrayList;
import java.util.List;

public class Decision {

    private List<ActionRequest> mActionRequests;

    public Decision(){
        mActionRequests = new ArrayList<>();
    }

    public void addActionRequest(ActionRequest actionRequest){
        mActionRequests.add(actionRequest);
    }

    public void apply(){
        mActionRequests.stream().forEach(ActionRequest::handle);
    }
}
