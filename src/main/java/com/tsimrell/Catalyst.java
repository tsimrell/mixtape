package com.tsimrell;

import com.tsimrell.domain.mixtape.base.MixtapeBase;
import com.tsimrell.domain.mixtape.change.MixtapeChange;
import com.tsimrell.service.FileIoService;
import com.tsimrell.service.MixtapeChangeService;
import com.tsimrell.service.impl.FileIoServiceImpl;
import com.tsimrell.service.impl.MixtapeChangeServiceImpl;

/**
 * Catalyst class for CMD Java program.
 *
 * Requires three parameters:
 * 1) MixtapeBase JSON file
 * 2) MixtapeChange file
 * 3) Name of output file
 */
public class Catalyst {
    public static final int MIXTAPE_BASE_FILE_ARG = 0;
    public static final int CHANGE_FILE_ARG = 1;
    public static final int OUTPUT_FILE_ARG = 2;

    public static void main(String args[]) {
        if(args != null && args.length > 1) {
            FileIoService fileIoService = new FileIoServiceImpl();
            MixtapeChangeService mixtapeChangeService = new MixtapeChangeServiceImpl();
            MixtapeBase mixtapeBase = null;
            MixtapeChange mixtapeChange = null;
            try {
                 mixtapeBase = fileIoService.readInMixtapeBase(args[MIXTAPE_BASE_FILE_ARG]);
                 mixtapeChange = fileIoService.readInMixtapeChange(args[CHANGE_FILE_ARG]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            if(mixtapeBase != null && mixtapeChange != null) {
                MixtapeBase newMixTapeBase = null;
                try {
                     newMixTapeBase = mixtapeChangeService.updateMixtapeBase(mixtapeBase, mixtapeChange);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                String outputName = "output.json";
                if(args.length == 3) {
                    outputName = args[OUTPUT_FILE_ARG];
                }

                try {
                    fileIoService.writeOutputToDisk(newMixTapeBase, outputName);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return;
                }

                System.out.println("Mixtape Update has successfully completed!");
            } else {
                System.out.println("There was an error trying to read in the Mixtape files.");
            }
        } else {
            System.out.println("There must be at least two arguments-> 0: name of the Mixtape Base, 1: name of the " +
                    "Mixtape Change file. There is an optional third arg that allows you to specify the output file.");
        }
    }
}
