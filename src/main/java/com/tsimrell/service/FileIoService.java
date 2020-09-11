package com.tsimrell.service;

import com.tsimrell.domain.mixtape.base.MixtapeBase;
import com.tsimrell.domain.mixtape.change.MixtapeChange;

import java.io.IOException;

public interface FileIoService {
    MixtapeBase readInMixtapeBase(String filePath) throws IOException;
    MixtapeChange readInMixtapeChange(String filePath) throws IOException;
    void writeOutputToDisk(MixtapeBase mixtapeBase, String outputName) throws IOException;
}
