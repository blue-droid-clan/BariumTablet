package com.codeswimmer.common.contract;

public class Contract {

    public static final void precondition(Boolean isValid) {
        if (isValid == false)
            throw new PreconditionError();
    }
    
    public static final void preconditionNotNull(Object object) {
        precondition(object != null);
    }
}
