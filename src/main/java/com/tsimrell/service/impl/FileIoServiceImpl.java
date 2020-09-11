package com.tsimrell.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsimrell.domain.mixtape.base.MixtapeBase;
import com.tsimrell.domain.mixtape.change.MixtapeChange;
import com.tsimrell.service.FileIoService;

import java.io.*;

public class FileIoServiceImpl implements FileIoService {

    private ObjectMapper mapper;

    public FileIoServiceImpl() {
        mapper = new ObjectMapper();
    }

    public MixtapeBase readInMixtapeBase(String filePath) throws IOException {
        MixtapeBase mixtapeBase = null;
        if(filePath != null && !filePath.isEmpty()) {
            try {
                File file = new File(filePath);
                if(file != null) {
                    mixtapeBase = mapper.readValue(file, MixtapeBase.class);
                } else {
                    throw new FileNotFoundException("The Mixtape Base File does not exist or this program does not have " +
                            "the correct privileges to access the file");
                }
            } catch (IOException e) { // Catch and re-throw with a nicer message
                throw new IOException("There was an error trying to parse the Mixtape Base JSON file. " +
                        "It may be malformed.");
            }
        }

        return mixtapeBase;
    }

    public MixtapeChange readInMixtapeChange(String filePath) throws IOException {
        MixtapeChange mixtapeChange = null;
        if(filePath != null && !filePath.isEmpty()) {
            try {
                File file = new File(filePath);
                if(file != null) {
                    mixtapeChange = mapper.readValue(file, MixtapeChange.class);
                } else {
                    throw new FileNotFoundException("The Mixtape Change File does not exist or this program does not " +
                            "have the correct privileges to access the file");
                }
            } catch (IOException e) { // Catch and re-throw
                throw new IOException("There was an error trying to parse the Mixtape Change JSON file. " +
                        "It may be malformed.");
            }
        }

        return mixtapeChange;
    }

    public void writeOutputToDisk(MixtapeBase mixtapeBase, String outputName) throws IOException {
        if(mixtapeBase != null && outputName != null) {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
                writer.write(mapper.writeValueAsString(mixtapeBase));
            } catch (IOException e) {
                throw new IOException("There was an error trying to write the updated Mixtape base json to file.");
            }
        }
    }
}
