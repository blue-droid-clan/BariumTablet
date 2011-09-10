package com.codeswimmer.common.contract;

public class PreconditionError extends Error {
    private static final long serialVersionUID = -2771149092054155783L;

    public PreconditionError() {
    }

    public PreconditionError(String detailMessage) {
        super(detailMessage);
    }

    public PreconditionError(Throwable throwable) {
        super(throwable);
    }

    public PreconditionError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
