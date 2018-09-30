package org.openstack4j.openstack.heat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.openstack4j.model.heat.StackOutput;
import org.openstack4j.openstack.common.ListResult;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("outputs")
public class HeatStackOutput implements StackOutput {

    /**
     * @Fields: serialVersionUID
     * @Todo: TODO
     */
    private static final long serialVersionUID = 1L;
    @JsonProperty("output_key")
    private String outputKey;
    @JsonProperty("description")
    private String description;

    @Override
    public String getOutputKey() {
        return outputKey;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static class StackOutputs extends ListResult<HeatStackOutput> {
        private static final long serialVersionUID = 600661296207420793L;

        @JsonProperty("outputs")
        private List<HeatStackOutput> list;

        protected List<HeatStackOutput> value() {
            return list;
        }
    }
}
