package com.google.protobuf;

import java.io.IOException;

public interface x extends p6 {

    public interface a extends p6, Cloneable {
        x build();

        x buildPartial();

        a mergeFrom(f fVar, i iVar) throws IOException;

        a mergeFrom(x xVar);
    }

    int getSerializedSize();

    a newBuilderForType();

    a toBuilder();

    cp toByteString();

    void writeTo(n0 n0Var) throws IOException;
}
