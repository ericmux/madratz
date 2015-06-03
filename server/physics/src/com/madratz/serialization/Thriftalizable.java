package com.madratz.serialization;

import org.apache.thrift.TBase;

public interface Thriftalizable {
    TBase toThrift();
}
