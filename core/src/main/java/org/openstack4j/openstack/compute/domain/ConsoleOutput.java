package org.openstack4j.openstack.compute.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.openstack4j.model.ModelEntity;

/**
 * A wrapper model class to return the console output from a server
 *
 * @author Jeremy Unruh
 */
public class ConsoleOutput implements ModelEntity {

    private static final long serialVersionUID = 1L;
    private static final String JSON_FORMAT = "{ \"os-getConsoleOutput\": {\"length\":%d } }";

    @JsonProperty
    private String output;

    public static String getJSONAction(int numLines) {
        return String.format(JSON_FORMAT, numLines);
    }

    /**
     * @return the console output from a VM/Server or null
     */
    public String getOutput() {
        return output;
    }

}
